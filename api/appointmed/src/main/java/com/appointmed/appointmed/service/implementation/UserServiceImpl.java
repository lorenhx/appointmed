package com.appointmed.appointmed.service.implementation;

import com.appointmed.appointmed.dto.UserDto;
import com.appointmed.appointmed.exception.UserNotFound;
import com.appointmed.appointmed.service.UserService;
import com.appointmed.appointmed.util.PasswordGenerator;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final String realm;
    private final String clientId;
    private final Keycloak keycloak;

    public UserServiceImpl(@Value("${custom-env.keycloak.appointmed-realm}") String realm,
                           @Value("${custom-env.keycloak.appointmed-clientId}") String clientId, Keycloak keycloak) {
        this.realm = realm;
        this.clientId = clientId;
        this.keycloak = keycloak;
    }

    @Override
    public UserDto getUserPersonalInfo(String email) throws UserNotFound {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = usersResource.searchByEmail(email, true);
        if (users.isEmpty())
            throw new UserNotFound("User not found " + email);
        UserRepresentation userRepresentation = users.get(0);
        return new UserDto(userRepresentation.getFirstName(), userRepresentation.getLastName(), email, userRepresentation.getAttributes());
    }

    @Override
    public String addUser(String name, String surname, String email, Map<String, List<String>> attributes, String[] roles) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        String temporaryPassword = PasswordGenerator.getRandomPassword(8);
        credential.setTemporary(true);
        credential.setValue(temporaryPassword);

        RealmResource realmResource = keycloak.realm(realm);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(email);
        user.setEmail(email);
        user.setFirstName(name);
        user.setLastName(surname);
        user.setAttributes(attributes);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credential));

        realmResource.users().create(user);

        String userId = realmResource.users().searchByEmail(email, true).get(0).getId();

        ClientRepresentation clientRep = realmResource.clients().findByClientId(clientId).get(0);
        UserResource userResource = realmResource.users().get(userId);
        List<RoleRepresentation> roleRepresentations = getRoleRepresentations(clientRep, realmResource, roles);

        for (RoleRepresentation roleRepresentation : roleRepresentations)
            userResource.roles().clientLevel(clientRep.getId()).add(Collections.singletonList(roleRepresentation));

        return temporaryPassword;
    }

    private List<RoleRepresentation> getRoleRepresentations(ClientRepresentation clientRep, RealmResource realmResource, String[] roles) {
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        for (String roleName : roles) {
            RoleRepresentation roleRepresentation = realmResource.clients().get(clientRep.getId()).roles().get(roleName).toRepresentation();
            roleRepresentations.add(roleRepresentation);
        }
        return roleRepresentations;
    }

}


