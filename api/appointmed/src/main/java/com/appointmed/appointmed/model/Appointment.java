package com.appointmed.appointmed.model;

import com.appointmed.appointmed.constant.ReservationStatus;
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
    private String id;
    private String patientEmail;
    private String doctorEmail;
    private Instant startTimestamp;
    private Instant issuedTimestamp;
    private String notes;
    private ReservationStatus status;
    private Visit visit;
    private Location location;

    public Appointment(String patientEmail, String doctorEmail, Instant startTimestamp, Instant issuedTimestamp, String notes, ReservationStatus status, Visit visit, Location location) {
        this.patientEmail = patientEmail;
        this.doctorEmail = doctorEmail;
        this.startTimestamp = startTimestamp;
        this.issuedTimestamp = issuedTimestamp;
        this.notes = notes;
        this.status = status;
        this.visit = visit;
        this.location = location;
    }
}
