package com.appointmed.appointmed.init;

import com.appointmed.appointmed.config.secrets.GoogleSecrets;
import com.appointmed.appointmed.config.secrets.SMTPSecrets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@EnableConfigurationProperties({GoogleSecrets.class, SMTPSecrets.class})
public class KeycloakInitializer implements CommandLineRunner {

    private final Keycloak keycloak;
    private final GoogleSecrets googleSecrets;
    private final SMTPSecrets SMTPSecrets;
    private final String SMTP_HOST;
    private final int SMTP_PORT;
    private final String SMTP_USERNAME;
    private final String REALM;
    private final String CLIENT_ID = "oauth2-appointmed";
    private final String GOOGLE_OAUTH2_CLIENTID;

    public KeycloakInitializer(Keycloak keycloak, GoogleSecrets googleSecrets, SMTPSecrets SMTPSecrets,
                               @Value("${custom-env.smtp.host}") String SMTP_HOST,
                               @Value("${custom-env.smtp.port}") int SMTP_PORT,
                               @Value("${custom-env.smtp.username}") String SMTP_USERNAME,
                               @Value("${keycloak.appointmed-realm}") String REALM,
                               @Value("${custom-env.google.oauth2.clientId}") String GOOGLE_OAUTH2_CLIENTID) {
        this.keycloak = keycloak;
        this.googleSecrets = googleSecrets;
        this.SMTPSecrets = SMTPSecrets;
        this.SMTP_HOST = SMTP_HOST;
        this.SMTP_PORT = SMTP_PORT;
        this.SMTP_USERNAME = SMTP_USERNAME;
        this.REALM = REALM;
        this.GOOGLE_OAUTH2_CLIENTID = GOOGLE_OAUTH2_CLIENTID;
    }


    private final List<String> CLIENT_ROLES = Arrays.asList("APPOINTMED_PATIENT", "APPOINTMED_DOCTOR", "APPOINTMED_ADMIN");

    @Override
    public void run(String... args) {
        initializeKeycloak();
    }

    private void initializeKeycloak() {

        deleteRealmIfExists();

        createRealm();

        RealmResource realmResource = keycloak.realm(REALM);

        ClientRepresentation client = createClient(realmResource);

        createRoles(realmResource, client);

        createGoogleIdentityProvider(realmResource);

        populateUsers();

        log.info("Keycloak is successfully initialized!");
    }

    private void deleteRealmIfExists(){
        Optional<RealmRepresentation> representationOptional = keycloak.realms()
                .findAll()
                .stream()
                .filter(r -> r.getRealm().equals(REALM))
                .findAny();
        if (representationOptional.isPresent()) {
            log.info("Removing already pre-configured '{}' realm", REALM);
            keycloak.realm(REALM).remove();
        }
    }

    private void createRealm() {
        log.info("Creating realm '{}'...", REALM);
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(REALM);
        realm.setEnabled(true);
        realm.setRegistrationEmailAsUsername(true);
        realm.setRememberMe(true);
        realm.setRegistrationAllowed(true);
        realm.setResetPasswordAllowed(true);
        realm.setAccessTokenLifespan(99999999);
        realm.setVerifyEmail(true);

        // Define email configuration
        Map<String, String> realmEmailConfig = new HashMap<>();
        realmEmailConfig.put("host", SMTP_HOST);
        realmEmailConfig.put("port", String.valueOf(SMTP_PORT));
        boolean emailStartTls = true;
        realmEmailConfig.put("starttls", String.valueOf(emailStartTls));
        realmEmailConfig.put("auth", "true");
        realmEmailConfig.put("ssl", "false");
        // Define email configuration
        realmEmailConfig.put("from", SMTP_USERNAME);
        realmEmailConfig.put("user", SMTP_USERNAME);
        String emailPassword = SMTPSecrets.getPassword();
        realmEmailConfig.put("password", emailPassword);
        realm.setSmtpServer(realmEmailConfig);
        realm.setLoginTheme("keywind");

        //At least one upper case English letter, (?=.*?[A-Z])
        //At least one lower case English letter, (?=.*?[a-z])
        //At least one digit, (?=.*?[0-9])
        //At least one special character, (?=.*?[#?!@$%^&*-])
        //Minimum eight in length .{8,}
        String passwordPolicy = "regexPattern(^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$)";
        realm.setPasswordPolicy(passwordPolicy);

        keycloak.realms().create(realm);

        log.info("Realm '{}' created!", REALM);
    }

    private ClientRepresentation createClient(RealmResource realmResource) {
        log.info("Creating oauth2 client '{}'...", CLIENT_ID);

        ClientRepresentation client = new ClientRepresentation();
        client.setClientId(CLIENT_ID);
        client.setDirectAccessGrantsEnabled(true);
        client.setRedirectUris(List.of("http://localhost/*"));
        client.setBaseUrl("http://localhost/home");
        client.setStandardFlowEnabled(true);
        client.setRootUrl("http://localhost/");
        client.setWebOrigins(List.of("*"));
        client.setPublicClient(true);

        realmResource.clients().create(client);

        client = realmResource.clients().findByClientId(CLIENT_ID).get(0);

        log.info("Oauth2 Client '{}' created!", CLIENT_ID);

        return client;
    }

    private void createRoles(RealmResource realmResource, ClientRepresentation client) {
        log.info("Creating client roles '{}'...", CLIENT_ROLES);
        RolesResource rolesResource = realmResource.clients().get(client.getId()).roles();
        for (String roleName : CLIENT_ROLES) {
            RoleRepresentation role = new RoleRepresentation();
            role.setName(roleName);
            rolesResource.create(role);
        }
        log.info("Client roles created!");
    }

    private void createGoogleIdentityProvider(RealmResource realmResource) {
        log.info("Creating Google identity provider...");

        IdentityProviderRepresentation googleIdp = new IdentityProviderRepresentation();
        googleIdp.setProviderId("google");
        googleIdp.setAlias("google");
        googleIdp.setEnabled(true);

        // Configure Google Identity Provider
        Map<String, String> config = new HashMap<>();
        // Define Google identity provider details
        config.put("clientId", GOOGLE_OAUTH2_CLIENTID);
        String googleSecret = googleSecrets.getOauth2ClientSecret();
        config.put("clientSecret", googleSecret);
        googleIdp.setConfig(config);

        realmResource.identityProviders().create(googleIdp);

        log.info("Google Identity provider created!");
    }

    public void addUser(String realm, String name, String surname, String email, String password, Map<String, List<String>> attributes, String[] roles) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(password);

        RealmResource realmResource = keycloak.realm(realm);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(email);
        user.setEmail(email);
        user.setFirstName(name);
        user.setLastName(surname);
        user.setAttributes(attributes);
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credential));

        realmResource.users().create(user);

        String userId = realmResource.users().searchByEmail(email, true).get(0).getId();

        ClientRepresentation clientRep = realmResource.clients().findByClientId(CLIENT_ID).get(0);
        UserResource userResource = realmResource.users().get(userId);
        List<RoleRepresentation> roleRepresentations = getRoleRepresentations(clientRep, realmResource, roles);

        for (RoleRepresentation roleRepresentation : roleRepresentations)
            userResource.roles().clientLevel(clientRep.getId()).add(Collections.singletonList(roleRepresentation));

    }

    private List<RoleRepresentation> getRoleRepresentations(ClientRepresentation clientRep, RealmResource realmResource, String[] roles) {
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        for (String roleName : roles) {
            RoleRepresentation roleRepresentation = realmResource.clients().get(clientRep.getId()).roles().get(roleName).toRepresentation();
            roleRepresentations.add(roleRepresentation);
        }
        return roleRepresentations;
    }

    private void populateUsers(){
        log.info("Populating realm with default users...");

        //Admin
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes.put("phoneNumber", Collections.singletonList("123456789"));
        attributes.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-0.jpeg"));
        addUser(REALM, "Fabio", "Cinicolo", "fabiocinicolo@gmail.com", "P@ssw0rd", attributes, new String[]{"APPOINTMED_PATIENT", "APPOINTMED_DOCTOR", "APPOINTMED_ADMIN"});

        //Patient
        Map<String, List<String>> attributes1 = new HashMap<>();
        attributes1.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes1.put("phoneNumber", Collections.singletonList("123456789"));
        addUser(REALM, "Andrea", "Rossi", "evilpippozzo@gmail.com", "P@ssw0rd", attributes1, new String[]{"APPOINTMED_PATIENT"});

        //Doctors
        Map<String, List<String>> attributes2 = new HashMap<>();
        attributes2.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes2.put("phoneNumber", Collections.singletonList("123456789"));
        attributes2.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-profile-1.png"));
        addUser(REALM, "Aniello", "Buonocore", "anibuonocore@gmail.com", "P@ssw0rd", attributes2, new String[]{"APPOINTMED_DOCTOR"});

        Map<String, List<String>> attributes3 = new HashMap<>();
        attributes3.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes3.put("phoneNumber", Collections.singletonList("123456789"));
        attributes3.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-profile-2.png"));
        addUser(REALM, "Giulia", "Esposito", "g_esposito@gmail.com", "P@ssw0rd", attributes3, new String[]{"APPOINTMED_DOCTOR"});

        Map<String, List<String>> attributes4 = new HashMap<>();
        attributes4.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes4.put("phoneNumber", Collections.singletonList("123456789"));
        attributes4.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-profile-3.png"));
        addUser(REALM, "Lorenzo", "Gallo", "lorenzo.gallo@gmail.com", "P@ssw0rd", attributes4, new String[]{"APPOINTMED_DOCTOR"});

        Map<String, List<String>> attributes5 = new HashMap<>();
        attributes5.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes5.put("phoneNumber", Collections.singletonList("123456789"));
        attributes5.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-profile-4.png"));
        addUser(REALM, "Sofia", "De Santis", "sant.sofia@gmail.com", "P@ssw0rd", attributes5, new String[]{"APPOINTMED_DOCTOR"});

        Map<String, List<String>> attributes6 = new HashMap<>();
        attributes6.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes6.put("phoneNumber", Collections.singletonList("123456789"));
        attributes6.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-profile-5.png"));
        addUser(REALM, "Antonio", "Ferrara", "ferrara.antonio@gmail.com", "P@ssw0rd", attributes6, new String[]{"APPOINTMED_DOCTOR"});

        Map<String, List<String>> attributes7 = new HashMap<>();
        attributes7.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes7.put("phoneNumber", Collections.singletonList("123456789"));
        attributes7.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-profile-6.png"));
        addUser(REALM, "Laura", "Rizzo", "rizzo.laura@gmail.com", "P@ssw0rd", attributes7, new String[]{"APPOINTMED_DOCTOR"});

        Map<String, List<String>> attributes8 = new HashMap<>();
        attributes8.put("taxId", Collections.singletonList("AKSDJ983703"));
        attributes8.put("phoneNumber", Collections.singletonList("123456789"));
        attributes8.put("imageLink", Collections.singletonList("https://storage.googleapis.com/appointmed-doctor-profile-image/doctor-profile-7.png"));
        addUser(REALM, "Marco", "Conti", "marco.conti@gmail.com", "P@ssw0rd", attributes8, new String[]{"APPOINTMED_DOCTOR"});

        log.info("Successfully populated users!");
    }

}

