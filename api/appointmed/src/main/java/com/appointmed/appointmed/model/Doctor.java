package com.appointmed.appointmed.model;

import com.appointmed.appointmed.constant.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("doctor")
public class Doctor {

    @Id
    private String email;
    private List<Review> reviews;
    private List<Specialization> specializations;
    private List<Location> locations;
    private List<Appointment> appointments;

}
