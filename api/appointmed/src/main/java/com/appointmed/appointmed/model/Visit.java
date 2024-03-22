package com.appointmed.appointmed.model;

import com.appointmed.appointmed.constant.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document("visit")
public class Visit {
    @Id
    public int id;
    public String type;
    public float price;
    Specialization specialization;
    public int timeSlotMinutes;

    public Visit(String type, float price, Specialization specialization, int timeSlotMinutes) {
        this.type = type;
        this.price = price;
        this.specialization = specialization;
        this.timeSlotMinutes = timeSlotMinutes;
    }
}

