package com.appointmed.appointmed.service;

import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.exception.AppointmentNotFound;
import com.appointmed.appointmed.exception.DoctorNotFound;
import com.appointmed.appointmed.exception.IDORException;
import com.appointmed.appointmed.exception.VisitNotFound;
import com.appointmed.appointmed.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface AppointmentService {

    List<Appointment> getAppointmentsByVisitType(String visitType);

    List<Appointment> getAppointmentsByDoctorEmail(String email) throws DoctorNotFound;

    void createAppointment(Appointment appointment) throws VisitNotFound, DoctorNotFound;

    void updateAppointmentStatus(String id, ReservationStatus status, String notes) throws AppointmentNotFound, IDORException;


    Appointment getAppointmentById(String appointmentId) throws AppointmentNotFound;
}
