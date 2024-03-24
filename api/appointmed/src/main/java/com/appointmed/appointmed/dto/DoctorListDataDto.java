package com.appointmed.appointmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DoctorListDataDto {
    private DoctorFiltersDto filters;
    private List<DoctorDto> doctors;
}
