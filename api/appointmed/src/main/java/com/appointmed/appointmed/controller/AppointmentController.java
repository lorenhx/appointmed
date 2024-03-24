package com.appointmed.appointmed.controller;

import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.dto.*;
import com.appointmed.appointmed.exception.AppointmentNotFound;
import com.appointmed.appointmed.exception.DoctorNotFound;
import com.appointmed.appointmed.exception.VisitNotFound;
import com.appointmed.appointmed.mapper.AppointmentMapper;
import com.appointmed.appointmed.mapper.CreateAppointmentMapper;
import com.appointmed.appointmed.model.Appointment;
import com.appointmed.appointmed.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CreateAppointmentMapper createAppointmentMapper;
    private final AppointmentMapper appointmentMapper;


    @Operation(
            summary = "Creates appointment in PENDING state.",
            description = "A doctor can create an appointment. The appointment will be persisted on the database and a confirmation email will be sent to the patient.",
            security = {@SecurityRequirement(name = "AuthorizationHeader")}              )
    @PostMapping()
    public void createAppointment(@RequestBody CreateAppointmentDto createAppointmentDto) throws VisitNotFound, DoctorNotFound {
        Appointment appointment = createAppointmentMapper.createAppointmentDtoToAppointment(createAppointmentDto);
        appointmentService.createAppointment(appointment);
    }

    @Operation(
            summary = "Updates appointment status.",
            description = "A doctor can confirm or reject an appointment.",
            security = {@SecurityRequirement(name = "AuthorizationHeader")}              )
    @PatchMapping("/{appointmentId}")
    public void updateAppointmentStatus(@PathVariable String appointmentId, @RequestBody UpdateAppointmentDto updateAppointmentDto) throws AppointmentNotFound {
        appointmentService.updateAppointmentStatus(appointmentId, updateAppointmentDto.getStatus(), updateAppointmentDto.getNotes());
    }

    @Operation(
            summary = "Gets doctor appointments along with filters.",
            description = "A doctor can get a list of its appointments for management purposes.",
            security = {@SecurityRequirement(name = "AuthorizationHeader")}              )
    @GetMapping
    public DoctorAppointmentListDataDto getDoctorAppointmentListData(@RequestParam String doctorEmail) throws DoctorNotFound {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorEmail(doctorEmail);
        List<AppointmentDto> appointmentDtos = appointments.stream()
                .map(appointmentMapper::appointmentToDto)
                .toList();

        return new DoctorAppointmentListDataDto(new DoctorAppointmentFiltersDto(ReservationStatus.values(), appointments.stream()
                .map(appointment -> appointment.getVisit().getType()).distinct().toArray(String[]::new)), appointmentDtos);
    }

    @Operation(
            summary = "Gets availability info about a visit type.",
            description = "A non-authenticated user can get a list of occupied timeslots for a specific visit type."
    )
    @GetMapping("/availability")
    public AppointmentAvailabilityDto getAppointmentAvailability(@RequestParam String visitType) {
        List<Appointment> appointments = appointmentService.getAppointmentsByVisitType(visitType);

        int timeSlotMinutes = appointments.get(0).getVisit().getTimeSlotMinutes();

        List<Instant> timeSlotsTaken = appointments.stream()
                .map(Appointment::getStartTimestamp)
                .collect(Collectors.toList());

        return new AppointmentAvailabilityDto(timeSlotMinutes, timeSlotsTaken);
    }

}
