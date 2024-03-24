package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.ReservationStatus;
import lombok.Data;

@Data
public class UpdateAppointmentDto {
    private ReservationStatus status;
    private String notes;
    private String doctorEmail; //This would be overridden with the actual doctorEmail extracted from the oauth2 token (Prevents IDOR)
}