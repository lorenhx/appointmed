package com.appointmed.appointmed.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document("contact-info")
public class ContactInfo {

    @Id
    public int id;
    String phoneNumber;
    String email;
    String faxNumber;
    String websiteUrl;

    public ContactInfo(String phoneNumber, String email, String faxNumber, String websiteUrl) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.faxNumber = faxNumber;
        this.websiteUrl = websiteUrl;
    }
}
