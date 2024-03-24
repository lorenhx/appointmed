package com.appointmed.appointmed.service;

import com.appointmed.appointmed.dto.UserDto;
import com.appointmed.appointmed.exception.UserNotFound;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto getUserPersonalInfo(String email) throws UserNotFound;

}
