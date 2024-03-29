package com.appointmed.appointmed.service.implementation;

import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.exception.*;
import com.appointmed.appointmed.model.Appointment;
import com.appointmed.appointmed.model.Doctor;
import com.appointmed.appointmed.model.Location;
import com.appointmed.appointmed.model.Visit;
import com.appointmed.appointmed.repository.AppointmentRepository;
import com.appointmed.appointmed.repository.DoctorRepository;
import com.appointmed.appointmed.repository.VisitRepository;
import com.appointmed.appointmed.service.AppointmentService;
import com.appointmed.appointmed.util.Oauth2TokenIntrospection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<Appointment> getAppointmentsByVisitType(String visitType) {
        return appointmentRepository.findByVisitType(visitType);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorEmail(String email) throws DoctorNotFound {
        Optional<Doctor> doctorOptional = doctorRepository.findById(email);
        if (doctorOptional.isEmpty())
            throw new DoctorNotFound("Doctor with email " + email + " not found.");

        Doctor doctor = doctorOptional.get();
        return doctor.getAppointments();
    }

    @Override
    public void createAppointment(Appointment appointment) throws VisitNotFound, DoctorNotFound, RuntimeException, AppointmentAlreadyExists, IDORException {
        if(!appointment.getPatientEmail().equals(Oauth2TokenIntrospection.extractEmail()))
            throw new IDORException("You are trying to create an appointment for another user!");
        Doctor doctor = findDoctorByEmail(appointment.getDoctorEmail());

        Optional<Visit> visitOptional = findVisitById(appointment.getVisit().getId());
        if (visitOptional.isEmpty())
            throw new VisitNotFound("Could not create appointment because visit with id " + appointment.getVisit().getId() + " does not exist");

        Visit visit = visitOptional.get();

        Location matchingLocation = findMatchingLocation(doctor, appointment.getLocation().getAddress());

        validateNewAppointment(doctor, appointment);

        setupNewAppointment(doctor, appointment, visit, matchingLocation);

        saveDoctorAndAppointment(doctor, appointment);


    }

    private Optional<Visit> findVisitById(String visitId) {
        return visitRepository.findById(visitId);
    }

    private void validateNewAppointment(Doctor doctor, Appointment newAppointment) throws RuntimeException, AppointmentAlreadyExists {
        boolean appointmentExists = doctor.getAppointments().stream()
                .anyMatch(existingAppointment ->
                        existingAppointment.getVisit().getId().equals(newAppointment.getVisit().getId()) &&
                                existingAppointment.getStartTimestamp().equals(newAppointment.getStartTimestamp()));

        if (appointmentExists)
            throw new AppointmentAlreadyExists("Appointment already exists");

    }

    private void setupNewAppointment(Doctor doctor, Appointment newAppointment, Visit visit, Location location) {
        newAppointment.setVisit(visit);
        newAppointment.setIssuedTimestamp(Instant.now());
        newAppointment.setLocation(location);
        doctor.getAppointments().add(newAppointment);
    }

    private void saveDoctorAndAppointment(Doctor doctor, Appointment appointment) {
        appointmentRepository.save(appointment);
        doctorRepository.save(doctor);
    }


    @Override
    public void updateAppointmentStatus(String id, ReservationStatus status, String notes) throws AppointmentNotFound, IDORException, DoctorNotFound {
        Appointment appointment = findAppointmentById(id);
        validateOwnership(appointment);
        updateAppointment(appointment, status, notes);
    }

    @Override
    public Appointment getAppointmentById(String id) throws AppointmentNotFound {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty())
            throw new AppointmentNotFound("Could not update status of appointment with id: " + id + " because it does not exists.");
        return optionalAppointment.get();
    }

    private Appointment findAppointmentById(String id) throws AppointmentNotFound {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty())
            throw new AppointmentNotFound("Could not update status of appointment with id: " + id + " because it does not exist.");
        return optionalAppointment.get();
    }

    private void validateOwnership(Appointment appointment) throws IDORException {
        String doctorEmail = Oauth2TokenIntrospection.extractEmail();
        if (!appointment.getDoctorEmail().equals(doctorEmail))
            throw new IDORException("You are trying to access an appointment you do not own!");
    }

    private void updateAppointment(Appointment appointment, ReservationStatus status, String notes) throws DoctorNotFound {
        appointment.setStatus(status);
        appointment.setNotes(notes);
        appointmentRepository.save(appointment);

        Doctor doctor = findDoctorByEmail(Oauth2TokenIntrospection.extractEmail());
        updateDoctorAppointment(doctor, appointment, status, notes);
    }

    private Doctor findDoctorByEmail(String email) throws DoctorNotFound {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(email);
        if (optionalDoctor.isEmpty())
            throw new DoctorNotFound("Doctor with email " + email + " not found.");
        return optionalDoctor.get();
    }

    private void updateDoctorAppointment(Doctor doctor, Appointment appointment, ReservationStatus status, String notes) {
        doctor.getAppointments().stream()
                .filter(a -> a.getId().equals(appointment.getId()))
                .findFirst()
                .ifPresent(a -> {
                    a.setStatus(status);
                    a.setNotes(notes);
                });
        doctorRepository.save(doctor);
    }

    private Location findMatchingLocation(Doctor doctor, String address) {
        return doctor.getLocations().stream()
                .filter(location -> address.equals(location.getAddress()))
                .findFirst()
                .orElse(null);
    }
}
