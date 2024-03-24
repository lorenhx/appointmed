package com.appointmed.appointmed.controller;


import com.appointmed.appointmed.constant.Specialization;
import com.appointmed.appointmed.dto.*;
import com.appointmed.appointmed.mapper.DoctorMapper;
import com.appointmed.appointmed.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;


    @Operation(
            summary = "Gets doctor list.",
            description = "A non-authenticated user can get a list of doctors for him to choose the visit from the preferred location.")
    @GetMapping()
    public DoctorListDataDto getDoctorListData(@RequestParam List<Specialization> specializations, @RequestParam String location, @RequestParam int range) {


        List<DoctorDto> doctors = getDoctorsBySpecializationsAndLocationInRange(specializations, location, range);

        System.out.println(doctors);
        String[] visitTypes = extractVisitTypes(doctors);

        return new DoctorListDataDto(new DoctorFiltersDto(visitTypes), doctors);
    }

    @Operation(
            summary = "Gets specializations.",
            description = "A non-authenticated user can get a list of supported specializations."
    )
    @GetMapping("specialization")
    public Specialization[] getSpecializations() {
        return Specialization.values();
    }


    private List<DoctorDto> getDoctorsBySpecializationsAndLocationInRange(List<Specialization> specializations, String location, int range) {
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
