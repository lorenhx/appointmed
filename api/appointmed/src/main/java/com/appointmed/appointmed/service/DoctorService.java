package com.appointmed.appointmed.service;

import com.appointmed.appointmed.constant.Specialization;
import com.appointmed.appointmed.exception.LocationToCoordinatesException;
import com.appointmed.appointmed.model.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {
    List<Doctor> getDoctorsBySpecializations(List<Specialization> specializations);

    List<Doctor> getDoctorsBySpecializationsAndLocationInRange(List<Specialization> specializations, String locationAddress, int range) throws LocationToCoordinatesException;

    void addDoctor(Doctor doctor);

}
