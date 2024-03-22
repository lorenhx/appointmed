package com.appointmed.appointmed.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @GetMapping("/")
    public List<String> getAppointments(){
        return List.of("String", "String2");
    }

    @PostMapping("/")
    public List<String> addAppointmentInPendingState(){
        return List.of("String", "Patient");
    }

    @PatchMapping("/")
    public List<String> confirmOrRejectAppointment(){
        return List.of("String", "Patient");
    }


}
