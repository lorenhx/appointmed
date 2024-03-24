package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorAppointmentFiltersDto {

    private ReservationStatus[] statuses;
    private String[] visitTypes;

}
