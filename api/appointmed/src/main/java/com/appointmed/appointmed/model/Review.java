package com.appointmed.appointmed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document("review")
public class Review {
    @Id
    public String id;
    public String title;
    public String description;
    public int stars;
    public String patientEmail;

    public Review(String title, String description, int stars, String patientEmail) {
        this.title = title;
        this.description = description;
        this.stars = stars;
        this.patientEmail = patientEmail;
    }
}
