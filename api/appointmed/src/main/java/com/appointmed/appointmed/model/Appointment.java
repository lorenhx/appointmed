package com.appointmed.appointmed.model;

import com.appointmed.appointmed.constant.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@Data
@Document("appointment")
public class Appointment {

    @Id
    public int id;
    public String userEmail;
    public Instant startTimestamp;
    public Instant issuedTimestamp;
    public String notes;
    public ReservationStatus status;
    public Visit visit;

    public Appointment(String userEmail, Instant startTimestamp, Instant issuedTimestamp, String notes, ReservationStatus status, Visit visit) {
        this.userEmail = userEmail;
        this.startTimestamp = startTimestamp;
        this.issuedTimestamp = issuedTimestamp;
        this.notes = notes;
        this.status = status;
        this.visit = visit;
    }
}
