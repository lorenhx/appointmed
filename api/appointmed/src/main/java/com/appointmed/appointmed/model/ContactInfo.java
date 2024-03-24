package com.appointmed.appointmed.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document("contact-info")
public class ContactInfo {

    @Id
    private String id;
    private String phoneNumber;
    private String email;
    private String faxNumber;
    private String websiteUrl;

    public ContactInfo(String phoneNumber, String email, String faxNumber, String websiteUrl) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.faxNumber = faxNumber;
        this.websiteUrl = websiteUrl;
    }
}
