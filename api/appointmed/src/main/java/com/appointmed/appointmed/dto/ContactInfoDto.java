package com.appointmed.appointmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactInfoDto {
    private String phoneNumber;
    private String email;
    private String faxNumber;
    private String websiteUrl;
}
