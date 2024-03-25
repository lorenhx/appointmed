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
    private String doctorEmail; //This would be overridden with the actual doctorEmail extracted from the oauth2 token (Prevents IDOR)
    private String address;
}
