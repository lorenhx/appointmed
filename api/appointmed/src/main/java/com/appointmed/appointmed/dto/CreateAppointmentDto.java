package com.appointmed.appointmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CreateAppointmentDto {
    private Instant startTimestamp;
    private Instant issuedTimestamp = Instant.now();
    private String visitId;
    private String patientEmail;
    private String address;
}
