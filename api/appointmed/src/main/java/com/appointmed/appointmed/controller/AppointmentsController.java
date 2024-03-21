package com.appointmed.appointmed.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class AppointmentsController {

    @GetMapping("/api/appointments")
    public List<String> getAppointments(){
        return List.of("String", "String2");
    }

    @GetMapping("/api/patients")
    public List<String> getPatients(){
        return List.of("String", "Patient");
    }

    @PostMapping("/api/appointments")
    public List<String> postAppointments(){
        return List.of("String", "String2");
    }

    //Expects api calls to methods like POST, PATCH,... to insert the CSRF token into the X-XSRF-TOKEN header
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

}
