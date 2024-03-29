package com.appointmed.appointmed.service;

import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.exception.*;
import com.appointmed.appointmed.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface AppointmentService {

    List<Appointment> getAppointmentsByVisitType(String visitType);

    List<Appointment> getAppointmentsByDoctorEmail(String email) throws DoctorNotFound;

    void createAppointment(Appointment appointment) throws VisitNotFound, DoctorNotFound, AppointmentAlreadyExists, IDORException;

    void updateAppointmentStatus(String id, ReservationStatus status, String notes) throws AppointmentNotFound, IDORException, DoctorNotFound;


    Appointment getAppointmentById(String appointmentId) throws AppointmentNotFound;
}
