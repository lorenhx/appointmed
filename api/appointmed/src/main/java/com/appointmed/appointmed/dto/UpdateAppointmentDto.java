package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.ReservationStatus;
import lombok.Data;

@Data
public class UpdateAppointmentDto {
    private ReservationStatus status;
    private String notes;
}