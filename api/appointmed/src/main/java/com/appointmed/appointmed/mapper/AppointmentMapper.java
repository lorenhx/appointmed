package com.appointmed.appointmed.mapper;

import com.appointmed.appointmed.dto.AppointmentDto;
import com.appointmed.appointmed.dto.PatientDto;
import com.appointmed.appointmed.dto.UserDto;
import com.appointmed.appointmed.exception.UserNotFound;
import com.appointmed.appointmed.model.Appointment;
import com.appointmed.appointmed.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AppointmentMapper {

    @Autowired
    protected UserService userService;

    @Mapping(source = "visit.type", target = "visitType")
    @Mapping(source = "visit.timeSlotMinutes", target = "timeSlotMinutes")
    @Mapping(source = "visit.price", target = "price")
    @Mapping(source = "location.address", target = "address")
    @Mapping(source = "patientEmail", target = "patient.email")
    public abstract AppointmentDto appointmentToDto(Appointment appointment);

    @AfterMapping
    protected void setPatientPersonalInfo(@MappingTarget AppointmentDto appointmentDto, Appointment appointment) {
        UserDto patientWithPersonalInfo = null;
        try {
            patientWithPersonalInfo = userService.getUserPersonalInfo(appointment.getPatientEmail());
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }
        PatientDto patientDto = new PatientDto();
        patientDto.setName(patientWithPersonalInfo.getName());
        patientDto.setSurname(patientWithPersonalInfo.getSurname());
        patientDto.setEmail(appointment.getPatientEmail());
        patientDto.setAttributes(patientWithPersonalInfo.getAttributes());
        appointmentDto.setPatient(patientDto);
    }

}