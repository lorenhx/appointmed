package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AppointmentDto {

    private String visitType;
    private Instant startTimestamp;
    private Instant issuedTimestamp;
    private int timeSlotMinutes;
    private ReservationStatus status;
    private float price;
    private String address;
    private PatientDto patient;
    private String notes;

}
