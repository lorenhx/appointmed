package com.appointmed.appointmed.controller;


import com.appointmed.appointmed.constant.Specialization;
import com.appointmed.appointmed.dto.*;
import com.appointmed.appointmed.exception.LocationToCoordinatesException;
import com.appointmed.appointmed.mapper.DoctorMapper;
import com.appointmed.appointmed.service.DoctorService;
import com.appointmed.appointmed.service.EmailService;
import com.appointmed.appointmed.service.UserService;
import com.appointmed.appointmed.util.HtmlTemplateGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@Tag(name = "Manage doctors.")
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final UserService userService;
    private final DoctorMapper doctorMapper;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Operation(
            summary = "Gets doctor list.",
            description = "A non-authenticated user can get a list of doctors for him to choose the visit from the preferred location.")
    @GetMapping()
    public DoctorListDataDto getDoctorListData(@RequestParam List<Specialization> specializations, @RequestParam String location, @RequestParam int range) {
        List<DoctorDto> doctors;
        logger.info("Getting doctors list");
        try {
            doctors = getDoctorsBySpecializationsAndLocationInRange(specializations, location, range);
            logger.debug("Successful got doctors list");
        } catch (LocationToCoordinatesException e) {
            logger.error("Error processing request to get doctor's list because of: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return new DoctorListDataDto(new DoctorFiltersDto(extractVisitTypes(doctors)), doctors);
    }

    @Operation(
            summary = "Gets specializations.",
            description = "A non-authenticated user can get a list of supported specializations."
    )
    @GetMapping("specialization")
    public Specialization[] getSpecializations() {
        return Specialization.values();
    }

    @Operation(
            summary = "Adds a doctor.",
            description = "An admin can add a doctor to the system. Personal data along with appointments data is required.",
            security = {@SecurityRequirement(name = "AuthorizationHeader")}
    )
    @PostMapping
    @PreAuthorize("hasRole('APPOINTMED_ADMIN')")
    public void addDoctor(@RequestBody DoctorDto doctorDto) {
        logger.info("Adding a doctor");
        String temporaryPassword = userService.addUser(doctorDto.getName(), doctorDto.getSurname(), doctorDto.getEmail(),
                doctorDto.getAttributes(), new String[]{"APPOINTMED_DOCTOR"});
        doctorService.addDoctor(doctorMapper.doctorDtoToDoctor(doctorDto));
        try {
            emailService.sendHtmlEmail("appointmed@outlook.it", doctorDto.getEmail(), "Doctor account created.",
                    HtmlTemplateGenerator.generateAccountConfirmationEmail(doctorDto.getName(), doctorDto.getSurname(), temporaryPassword));
            logger.debug("Successful added the doctor");

        } catch (MessagingException e) {
            logger.error("Error processing request to add a doctor because of: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    private List<DoctorDto> getDoctorsBySpecializationsAndLocationInRange(List<Specialization> specializations, String location, int range) throws LocationToCoordinatesException {
        return doctorService.getDoctorsBySpecializationsAndLocationInRange(specializations, location, range)
                .stream()
                .map(doctorMapper::doctorToDoctorDto)
                .toList();
    }

    private String[] extractVisitTypes(List<DoctorDto> doctors) {
        Set<String> visitTypesSet = new HashSet<>();
        for (DoctorDto doctorDto : doctors) {
            for (LocationDto locationDto : doctorDto.getLocations()) {
                for (VisitDto visitDto : locationDto.getVisits()) {
                    visitTypesSet.add(visitDto.getType());
                }
            }
        }
        return visitTypesSet.toArray(new String[0]);
    }

}
