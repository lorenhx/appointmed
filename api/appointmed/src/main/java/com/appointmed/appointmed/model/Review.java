package com.appointmed.appointmed.model;

import com.appointmed.appointmed.constant.ReviewStars;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document("review")
public class Review {
    @Id
    private String id;
    private String title;
    private String description;
    private ReviewStars stars;
    private String patientEmail;
    private String appointmentId;

    public Review(String title, String description, ReviewStars stars, String patientEmail, String appointmentId) {
        this.title = title;
        this.description = description;
        this.stars = stars;
        this.patientEmail = patientEmail;
        this.appointmentId = appointmentId;
    }
}
