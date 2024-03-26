package com.appointmed.appointmed.service.implementation;

import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.exception.AppointmentNotFound;
import com.appointmed.appointmed.exception.DoctorNotFound;
import com.appointmed.appointmed.exception.IDORException;
import com.appointmed.appointmed.exception.VisitNotFound;
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
    public void createAppointment(Appointment appointment) throws VisitNotFound, DoctorNotFound {

        System.out.println(appointmentRepository.findAll());

        Optional<Doctor> doctorOptional = doctorRepository.findById(appointment.getDoctorEmail());
        if (doctorOptional.isEmpty())
            throw new DoctorNotFound("Doctor with email " + appointment.getDoctorEmail() + " not found.");
        Optional<Visit> visitOptional = visitRepository.findById(appointment.getVisit().getId());
        if (visitOptional.isEmpty())
            throw new VisitNotFound("Could not create appointment because visit with id " + appointment.getVisit().getId() + " does not exist");

        Doctor doctor = doctorOptional.get();
        Visit visit = visitOptional.get();
        Location matchingLocation = findMatchingLocation(doctor, appointment.getLocation().getAddress());

        appointment.setVisit(visit);
        appointment.setIssuedTimestamp(Instant.now());
        appointment.setLocation(matchingLocation);
        doctor.getAppointments().add(appointment);

        System.out.println(appointmentRepository.save(appointment).getId());
        doctorRepository.save(doctor);
    }

    @Override
    public void updateAppointmentStatus(String id, ReservationStatus status, String notes) throws AppointmentNotFound, IDORException {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty())
            throw new AppointmentNotFound("Could not update status of appointment with id: " + id + " because it does not exists.");

        Appointment appointment = optionalAppointment.get();
        if (!appointment.getDoctorEmail().equals(Oauth2TokenIntrospection.extractEmail()))
            throw new IDORException("You are trying to access an appointment you do not own!");

        appointment.setStatus(status);
        appointment.setNotes(notes);
        appointmentRepository.save(appointment);
        System.out.println(appointmentRepository.findAll());
    }

    @Override
    public Appointment getAppointmentById(String id) throws AppointmentNotFound {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty())
            throw new AppointmentNotFound("Could not update status of appointment with id: " + id + " because it does not exists.");
        return optionalAppointment.get();
    }

    private Location findMatchingLocation(Doctor doctor, String address) {
        return doctor.getLocations().stream()
                .filter(location -> address.equals(location.getAddress()))
                .findFirst()
                .orElse(null);
    }
}
