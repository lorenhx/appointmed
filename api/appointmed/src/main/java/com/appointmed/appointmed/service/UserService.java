package com.appointmed.appointmed.service;

import com.appointmed.appointmed.dto.UserDto;
import com.appointmed.appointmed.exception.UserNotFound;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    UserDto getUserPersonalInfo(String email) throws UserNotFound;
    String addUser(String name, String surname, String email, Map<String, List<String>> attributes, String [] roles);
}
