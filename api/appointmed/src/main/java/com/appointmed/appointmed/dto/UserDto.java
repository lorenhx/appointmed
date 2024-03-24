package com.appointmed.appointmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private Map<String, List<String>> attributes; //phoneNumber, taxId for Patient and phoneNumber, taxId and ImageLink for doctor
}
