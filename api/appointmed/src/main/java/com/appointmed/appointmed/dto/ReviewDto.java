package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.ReviewStars;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDto {

    private String title;
    private String description;
    private ReviewStars stars;
    private String patientEmail;
    private String appointmentId;
}
