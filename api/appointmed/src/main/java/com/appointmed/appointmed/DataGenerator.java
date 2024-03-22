package com.appointmed.appointmed;

import com.appointmed.appointmed.constant.Accessibility;
import com.appointmed.appointmed.constant.PaymentType;
import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.constant.Specialization;
import com.appointmed.appointmed.model.Appointment;
import com.appointmed.appointmed.model.ContactInfo;
import com.appointmed.appointmed.model.Doctor;
import com.appointmed.appointmed.model.Location;
import com.appointmed.appointmed.model.Review;
import com.appointmed.appointmed.model.Visit;
import com.appointmed.appointmed.repository.AppointmentRepository;
import com.appointmed.appointmed.repository.DoctorRepository;
import com.appointmed.appointmed.repository.LocationRepository;
import com.appointmed.appointmed.repository.ReviewRepository;
import com.appointmed.appointmed.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataGenerator implements CommandLineRunner {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Override
    public void run(String... args) throws Exception {
        // Generate synthetic data
        generateData();

        // Query MongoDB to verify data
        queryData();
    }

    private void generateData() {

        // Generate synthetic data for reviews
        Review review = new Review();
        review.setTitle("Great Experience");
        review.setDescription("The doctor was very knowledgeable and attentive.");
        review.setStars(5);
        review.setPatientEmail("patient@example.com");
//        reviewRepository.save(review);

        List<Review> l = new ArrayList<>();
        l.add(review);

        // Generate synthetic data for doctors
        Doctor doctor = new Doctor("doctor@example.com",  l, List.of(Specialization.CARDIOLOGIST), new ArrayList<>());
        doctorRepository.save(doctor);

        // Generate synthetic data for locations
        Location location = new Location();
        location.setAddress("123 Main Street");
        location.setName("Clinic XYZ");
        location.setOpenHours("9:00 AM - 5:00 PM");
        location.setPaymentTypes(Arrays.asList(PaymentType.CASH, PaymentType.CREDIT_CARD));
        location.setAccessibility(List.of(Accessibility.PREGNANT));
        location.setContactInfo(List.of(new ContactInfo("123-456-7890", "clinic@example.com", null, "www.clinicxyz.com")));
        locationRepository.save(location);


        // Generate synthetic data for appointments
        Appointment appointment = new Appointment();
        appointment.setUserEmail("patient@example.com");
        appointment.setStartTimestamp(Instant.now());
        appointment.setIssuedTimestamp(Instant.now());
        appointment.setNotes("Follow-up appointment");
        appointment.setStatus(ReservationStatus.CONFIRMED);
        appointment.setVisit(new Visit("Consultation", 100.0f, Specialization.CARDIOLOGIST, 30));
        appointmentRepository.save(appointment);
    }

    private void queryData() {
        // Query and print data to verify
        System.out.println("Appointments:");
        System.out.println(appointmentRepository.findAll());

        System.out.println("Doctors:");
        System.out.println(doctorRepository.findAll());

        System.out.println("Locations:");
        System.out.println(locationRepository.findAll());

        System.out.println("Reviews:");
        System.out.println(reviewRepository.findAll());

        System.out.println("Visits:");
        System.out.println(visitRepository.findAll());
    }
}
