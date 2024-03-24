package com.appointmed.appointmed.mapper;

import com.appointmed.appointmed.dto.CreateAppointmentDto;
import com.appointmed.appointmed.model.Appointment;
import com.appointmed.appointmed.model.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CreateAppointmentMapper {

    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "visit", source = "visitId", qualifiedByName = "mapVisit")
    @Mapping(target = "location.address", source = "address")
    Appointment createAppointmentDtoToAppointment(CreateAppointmentDto createAppointmentDto);

    @Named(value = "mapVisit")
    default Visit mapVisit(String visitId) {
        Visit visit = new Visit();
        visit.setId(visitId);
        return visit;
    }
}
