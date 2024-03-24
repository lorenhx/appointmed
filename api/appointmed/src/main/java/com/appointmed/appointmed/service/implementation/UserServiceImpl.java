package com.appointmed.appointmed.service.implementation;

import com.appointmed.appointmed.dto.UserDto;
import com.appointmed.appointmed.exception.UserNotFound;
import com.appointmed.appointmed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;

    @Override
    public UserDto getUserPersonalInfo(String email) throws UserNotFound {
        RealmResource realmResource = keycloak.realm("master");
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = usersResource.searchByEmail(email, true);
        System.out.println(email);
        if (users.isEmpty())
            throw new UserNotFound("User not found");
        UserRepresentation userRepresentation = users.get(0);
        return new UserDto(userRepresentation.getFirstName(), userRepresentation.getLastName(), email, userRepresentation.getAttributes());
    }

}
