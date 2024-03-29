package com.appointmed.appointmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CreateAppointmentDto {
    private Instant startTimestamp;
    private String visitId;
    private String patientEmail;
    private String doctorEmail;
    private String address;
}
