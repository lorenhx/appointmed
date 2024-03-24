package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto extends UserDto {

    private Specialization[] specializations;
    private List<LocationDto> locations;
    private List<ReviewDto> reviews;

}
