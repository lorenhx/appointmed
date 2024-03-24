package com.appointmed.appointmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DoctorAppointmentListDataDto {
    private DoctorAppointmentFiltersDto doctorAppointmentFilters;
    private List<AppointmentDto> appointments;
}
