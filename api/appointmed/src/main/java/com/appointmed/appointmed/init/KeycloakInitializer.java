package com.appointmed.appointmed.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeycloakInitializer implements CommandLineRunner {

    private final Keycloak keycloak;

    private final String REALM_NAME = "appointmed";

    private final String CLIENT_ID = "oauth2-appointmed";

    private final List<String> CLIENT_ROLES = Arrays.asList("APPOINTMED_PATIENT", "APPOINTMED_DOCTOR", "APPOINTMED_ADMIN");

    @Override
    public void run(String... args) {
        initializeKeycloak();
    }

    private void initializeKeycloak() {

        deleteRealmIfExists();

        createRealm();

        RealmResource realmResource = keycloak.realm(REALM_NAME);

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
                .filter(r -> r.getRealm().equals(REALM_NAME))
                .findAny();
        if (representationOptional.isPresent()) {
            log.info("Removing already pre-configured '{}' realm", REALM_NAME);
            keycloak.realm(REALM_NAME).remove();
        }
    }

    private void createRealm() {
        log.info("Creating realm '{}'...", REALM_NAME);
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(REALM_NAME);
        realm.setEnabled(true);
        realm.setRegistrationEmailAsUsername(true);
        realm.setRememberMe(true);
        realm.setRegistrationAllowed(true);
        realm.setResetPasswordAllowed(true);
        realm.setAccessTokenLifespan(99999999);
        realm.setVerifyEmail(true);

        // Define email configuration
        Map<String, String> realmEmailConfig = new HashMap<>();
        String emailHost = "smtp-mail.outlook.com";
        realmEmailConfig.put("host", emailHost);
        int emailPort = 587;
        realmEmailConfig.put("port", String.valueOf(emailPort));
        boolean emailStartTls = true;
        realmEmailConfig.put("starttls", String.valueOf(emailStartTls));
        realmEmailConfig.put("auth", "true");
        realmEmailConfig.put("ssl", "false");
        // Define email configuration
        String emailFrom = "appointmed@outlook.it";
        realmEmailConfig.put("from", emailFrom);
        String emailUsername = "appointmed@outlook.it";
        realmEmailConfig.put("user", emailUsername);
        String emailPassword = "";
        realmEmailConfig.put("password", emailPassword);
        realm.setSmtpServer(realmEmailConfig);
        realm.setLoginTheme("keywind");

        keycloak.realms().create(realm);
        log.info("Realm '{}' created!", REALM_NAME);
    }

    private ClientRepresentation createClient(RealmResource realmResource) {
        log.info("Creating oauth2 client '{}'...", CLIENT_ID);

        ClientRepresentation client = new ClientRepresentation();
        client.setClientId(CLIENT_ID);
        client.setDirectAccessGrantsEnabled(true);
        client.setRedirectUris(List.of("http://localhost:5173/*"));
        client.setBaseUrl("http://localhost:5173/home");
        client.setStandardFlowEnabled(true);
        client.setRootUrl("http://localhost:5173/");
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
        String googleClientId = "";
        config.put("clientId", googleClientId);
        String googleSecret = "";
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
        Map<String, List<String>> patientAttributes = new HashMap<>();
        patientAttributes.put("taxId", Collections.singletonList("123456789"));
        patientAttributes.put("phoneNumber", Collections.singletonList("123456789"));
        Map<String, List<String>> doctorAttributes = new HashMap<>();
        doctorAttributes.put("taxId", Collections.singletonList("987654321"));
        doctorAttributes.put("phoneNumber", Collections.singletonList("987654321"));
        doctorAttributes.put("imageLink", Collections.singletonList("http://example.com/image.jpg"));
        addUser(REALM_NAME, "Patient", "One", "patient@example.com", "password", patientAttributes, new String[]{"APPOINTMED_PATIENT"});
        addUser(REALM_NAME, "Doctor", "Two", "doctor@example.com", "password", doctorAttributes, new String[]{"APPOINTMED_DOCTOR"});
        addUser(REALM_NAME, "Admin", "Three", "admin@example.com", "password" ,new HashMap<>(), new String[]{"APPOINTMED_ADMIN"});
        addUser(REALM_NAME, "Fabio", "Cinicolo", "fabiocinicolo@gmail.com", "password", doctorAttributes, new String[]{"APPOINTMED_PATIENT", "APPOINTMED_DOCTOR", "APPOINTMED_ADMIN"});
        log.info("Successfully populated users!");
    }

}
