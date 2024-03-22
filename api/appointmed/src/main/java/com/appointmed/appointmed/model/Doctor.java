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
    public String email;
//    public String name;
//    public String surname;
//    public String taxId;
//    public String imageLink;
//ctl /     These attributes are going to be taken from keycloak database
    public List<Review> reviews;
    public List<Specialization> specializations;
    public List<Visit> visits;


}
