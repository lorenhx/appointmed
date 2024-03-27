package com.appointmed.appointmed.mapper;

import com.appointmed.appointmed.dto.DoctorDto;
import com.appointmed.appointmed.dto.UserDto;
import com.appointmed.appointmed.exception.UserNotFound;
import com.appointmed.appointmed.model.Doctor;
import com.appointmed.appointmed.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DoctorMapper {

    @Autowired
    protected UserService userService;

    @Mapping(target = "reviewsNumber", expression = "java(doctor.getReviews() != null ? doctor.getReviews().size() : 0)")
    public abstract DoctorDto doctorToDoctorDto(Doctor doctor);

    public abstract Doctor doctorDtoToDoctor(DoctorDto doctor);

    @AfterMapping
    protected void setDoctorPersonalInfo(@MappingTarget DoctorDto doctorDto, Doctor doctor) {
        UserDto doctorWithPersonalInfo = null;
        try {
            doctorWithPersonalInfo = userService.getUserPersonalInfo(doctor.getEmail());
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }
        doctorDto.setName(doctorWithPersonalInfo.getName());
        doctorDto.setSurname(doctorWithPersonalInfo.getSurname());
        doctorDto.setEmail(doctor.getEmail());
        doctorDto.setAttributes(doctorWithPersonalInfo.getAttributes());
    }
}