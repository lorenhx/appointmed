package com.appointmed.appointmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class AppointmentAvailabilityDto {
    private int timeSlotMinutes;
    private List<Instant> timeSlotsTaken;
}


