package com.appointmed.appointmed.init;

import com.appointmed.appointmed.constant.Accessibility;
import com.appointmed.appointmed.constant.PaymentType;
import com.appointmed.appointmed.constant.ReservationStatus;
import com.appointmed.appointmed.constant.Specialization;
import com.appointmed.appointmed.constant.ReviewStars;
import com.appointmed.appointmed.model.*;
import com.appointmed.appointmed.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j

@RequiredArgsConstructor
@Component
public class MongoInitializer implements CommandLineRunner {

    private final DoctorRepository doctorRepository;
    private final LocationRepository locationRepository;
    private final AppointmentRepository appointmentRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final VisitRepository visitRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) {
        removeData();
        populatingData();
        printData();
    }

    private void populatingData() {

        log.info("Populating some synthetic data on MongoDB instance..");

        // Initialize visits
        Visit visit1 = new Visit("Consultation", 100.0f, Specialization.DERMATOLOGIST, 30);
        Visit visit2 = new Visit("Routine Checkup", 150.0f, Specialization.CARDIOLOGIST, 45);
        visitRepository.saveAll(Arrays.asList(visit1, visit2));

        List<Visit> visits = List.of(visit1, visit2);

        // Initialize contact info
        ContactInfo contactInfo = new ContactInfo("123456789", "fabiocinicolo@gmail.com", "", "");
        contactInfoRepository.save(contactInfo);

        // Initialize locations
        Location location1 = new Location("Doctor's Office Address 1", "Doctor's Office 1", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.HEARING_IMPAIRMENT), Collections.singletonList(contactInfo), visits);
        Location location2 = new Location("Doctor's Office Address 2", "Doctor's Office 2", "10:00 AM - 6:00 PM",
                Collections.singletonList(PaymentType.CREDIT_CARD), Collections.singletonList(Accessibility.PREGNANT), Collections.singletonList(contactInfo), visits);
        location1.setVisits(Collections.singletonList(visit1));
        location2.setVisits(Collections.singletonList(visit2));
        locationRepository.saveAll(Arrays.asList(location1, location2));

        // Initialize doctors
        Doctor doctor1 = new Doctor("fabiocinicolo@gmail.com", null, Collections.singletonList(Specialization.DERMATOLOGIST),
                Collections.singletonList(location1), Collections.emptyList());
        Doctor doctor2 = new Doctor("john.doe@example.com", null, Collections.singletonList(Specialization.CARDIOLOGIST),
                Collections.singletonList(location2), Collections.emptyList());
        doctorRepository.saveAll(Arrays.asList(doctor1, doctor2));


        // Initialize appointments
        Appointment appointment1 = new Appointment("patient@example.com", doctor1.getEmail(), Instant.now().plusSeconds(3600),
                Instant.now(), "Appointment notes for Doctor 1", ReservationStatus.CONFIRMED, visit1, location1);
        Appointment appointment2 = new Appointment("patient@example.com", doctor1.getEmail(), Instant.now().plusSeconds(7200),
                Instant.now(), "Appointment notes for Doctor 2", ReservationStatus.CONFIRMED, visit2, location2);
        Appointment a = appointmentRepository.save(appointment1);
        Appointment b = appointmentRepository.save(appointment2);

        // Initialize reviews
        Review review1 = new Review("Great experience with Doctor 1!", "Highly recommend Doctor 1.", ReviewStars.FIVE, "patient@example.com", a.getId());
        Review review2 = new Review("Great experience with Doctor 2!", "Highly recommend Doctor 2.", ReviewStars.FOUR, "patient@example.com", b.getId());

        reviewRepository.saveAll(Arrays.asList(review1, review2));

        // Add doctor reviews
        doctor1.setReviews(List.of(review1, review2));
        doctor1.setAppointments(List.of(appointment1, appointment2));
        doctorRepository.save(doctor1);

        log.info("Successfully populated MongoDB data!");

    }

    private void removeData(){
        appointmentRepository.deleteAll();
        reviewRepository.deleteAll();
        doctorRepository.deleteAll();
        contactInfoRepository.deleteAll();
        locationRepository.deleteAll();
        visitRepository.deleteAll();
    }

    private void printData(){
        log.info("{}", appointmentRepository.findAll());
        log.info("{}", reviewRepository.findAll());
        log.info("{}", doctorRepository.findAll());
        log.info("{}", contactInfoRepository.findAll());
        log.info("{}", locationRepository.findAll());
        log.info("{}", visitRepository.findAll());
    }
}
