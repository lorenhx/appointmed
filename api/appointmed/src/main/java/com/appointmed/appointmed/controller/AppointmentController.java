package com.appointmed.appointmed.controller;

import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.dto.*;
import com.appointmed.appointmed.exception.*;
import com.appointmed.appointmed.mapper.AppointmentMapper;
import com.appointmed.appointmed.mapper.ContactInfoMapper;
import com.appointmed.appointmed.mapper.CreateAppointmentMapper;
import com.appointmed.appointmed.model.Appointment;
import com.appointmed.appointmed.model.Location;
import com.appointmed.appointmed.service.AppointmentService;
import com.appointmed.appointmed.service.EmailService;
import com.appointmed.appointmed.util.HtmlTemplateGenerator;
import com.appointmed.appointmed.util.Oauth2TokenIntrospection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Manage appointments.")
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CreateAppointmentMapper createAppointmentMapper;
    private final AppointmentMapper appointmentMapper;
    private final ContactInfoMapper contactInfoMapper;
    private final EmailService emailService;

    @Operation(
            summary = "Creates appointment in PENDING state.",
            description = "A patient can create an appointment. The appointment will be persisted on the database and a confirmation email will be sent to the patient.",
            security = {@SecurityRequirement(name = "AuthorizationHeader")})
    @PostMapping()
    @PreAuthorize("hasRole('APPOINTMED_PATIENT')")
    public void createAppointment(@RequestBody CreateAppointmentDto createAppointmentDto) {
        Appointment appointment = createAppointmentMapper.createAppointmentDtoToAppointment(createAppointmentDto);
        try {
            appointmentService.createAppointment(appointment);
            emailService.sendHtmlEmail("appointmed@outlook.it", createAppointmentDto.getPatientEmail(), "Appointmed pending reservation", HtmlTemplateGenerator.generateReservationPendingTemplate(createAppointmentDto.getAddress()));
        } catch (DoctorNotFound | VisitNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AppointmentAlreadyExists | IDORException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

        @Operation(
            summary = "Updates appointment status.",
            description = "A doctor can confirm or reject an appointment.",
            security = {@SecurityRequirement(name = "AuthorizationHeader")})
    @PatchMapping("/{appointmentId}")
    @PreAuthorize("hasRole('APPOINTMED_DOCTOR')")
    public void updateAppointmentStatus(@PathVariable String appointmentId, @RequestBody UpdateAppointmentDto updateAppointmentDto) {
        ReservationStatus status = updateAppointmentDto.getStatus();
        try {
            appointmentService.updateAppointmentStatus(appointmentId, status, updateAppointmentDto.getNotes());
            sendNotificationEmail(appointmentId, updateAppointmentDto);
        } catch (AppointmentNotFound | DoctorNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IDORException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @Operation(
            summary = "Gets doctor appointments along with filters.",
            description = "A doctor can get a list of its appointments for management purposes.",
            security = {@SecurityRequirement(name = "AuthorizationHeader")})
    @GetMapping
    @PreAuthorize("hasRole('APPOINTMED_DOCTOR')")
    public DoctorAppointmentListDataDto getDoctorAppointmentListData() {
        List<Appointment> appointments;
        try {
            appointments = appointmentService.getAppointmentsByDoctorEmail(Oauth2TokenIntrospection.extractEmail());
        } catch (DoctorNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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

        System.out.println(visitType);
        int timeSlotMinutes = appointments.get(0).getVisit().getTimeSlotMinutes();

        List<Instant> timeSlotsTaken = appointments.stream()
                .map(Appointment::getStartTimestamp)
                .collect(Collectors.toList());

        return new AppointmentAvailabilityDto(timeSlotMinutes, timeSlotsTaken);
    }

    private void sendNotificationEmail(String appointmentId, UpdateAppointmentDto updateAppointmentDto) throws MessagingException, AppointmentNotFound {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        Location location = appointment.getLocation();
        List<ContactInfoDto> contactInfoDtos = location.getContactInfo().stream().map(contactInfoMapper::contactInfoToContactInfoDto).toList();
        if (updateAppointmentDto.getStatus().equals(ReservationStatus.CONFIRMED)) {
            sendConfirmationEmail(appointment, location, contactInfoDtos, updateAppointmentDto.getNotes());
        } else if (updateAppointmentDto.getStatus().equals(ReservationStatus.REJECTED)) {
            sendRejectionEmail(appointment, location, contactInfoDtos, updateAppointmentDto.getNotes());
        }
    }


    private void sendConfirmationEmail(Appointment appointment, Location location, List<ContactInfoDto> contactInfoDtos, String notes) throws MessagingException {
        emailService.sendHtmlEmail(
                "appointmed@outlook.it",
                appointment.getPatientEmail(),
                "Appointmed Confirmed!",
                HtmlTemplateGenerator.generateReservationConfirmedTemplate(
                        appointment.getStartTimestamp(),
                        appointment.getVisit().getTimeSlotMinutes(),
                        contactInfoDtos,
                        location.getAddress(),
                        location.getName(),
                        notes
                )
        );
    }

    private void sendRejectionEmail(Appointment appointment, Location location, List<ContactInfoDto> contactInfoDtos, String notes) throws MessagingException {
        emailService.sendHtmlEmail(
                "appointmed@outlook.it",
                appointment.getPatientEmail(),
                "Appointmed Rejected!",
                HtmlTemplateGenerator.generateReservationRejectedTemplate(
                        location.getName(),
                        contactInfoDtos,
                        notes
                )
        );
    }

}
