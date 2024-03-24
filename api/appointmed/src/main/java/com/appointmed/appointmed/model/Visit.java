package com.appointmed.appointmed.model;

import com.appointmed.appointmed.constant.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("visit")
public class Visit {

    @Id
    private String id;
    private String type;
    private float price;
    private int timeSlotMinutes;
    Specialization specialization;

    public Visit(String type, float price, Specialization specialization, int timeSlotMinutes) {
        this.type = type;
        this.price = price;
        this.specialization = specialization;
        this.timeSlotMinutes = timeSlotMinutes;

    }
}

