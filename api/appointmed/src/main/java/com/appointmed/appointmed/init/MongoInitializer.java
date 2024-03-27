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
        Visit visit3 = new Visit("Simple Checkup", 150.0f, Specialization.FAMILY_PHYSICIAN, 45);
        visitRepository.saveAll(Arrays.asList(visit1, visit2, visit3));

        List<Visit> visits = new java.util.ArrayList<>(List.of(visit1, visit2));

        // Initialize contact info
        ContactInfo contactInfo = new ContactInfo("123456789", "fabiocinicolo@gmail.com", "", "");
        contactInfoRepository.save(contactInfo);

        // Initialize locations
        Location location1 = new Location("Naples", "Doctor's Office 1", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.HEARING_IMPAIRMENT), Collections.singletonList(contactInfo), visits);
        locationRepository.save(location1);

        List<Visit> visits2 = new java.util.ArrayList<>();
        visits2.add(visit3);
        Location location2 = new Location("Benevento, Italy", "Doctor's Office 2", "10:00 AM - 6:00 PM",
                Collections.singletonList(PaymentType.CREDIT_CARD), Collections.singletonList(Accessibility.PREGNANT), Collections.singletonList(contactInfo), visits2);

        locationRepository.save(location2);

        // Initialize doctors
        Doctor doctor1 = new Doctor("fabiocinicolo@gmail.com", null, Collections.singletonList(Specialization.DERMATOLOGIST),
               List.of(location1, location2), Collections.emptyList());

        doctorRepository.save(doctor1);


        // Initialize appointments
        Appointment appointment1 = new Appointment("fabiocinicolo@gmail.com", doctor1.getEmail(), Instant.now().plusSeconds(3600),
                Instant.now(), "Appointment notes for Doctor 1", ReservationStatus.CONFIRMED, visit1, location1);
        Appointment appointment2 = new Appointment("fabiocinicolo@gmail.com", doctor1.getEmail(), Instant.now().plusSeconds(7200),
                Instant.now(), "Appointment notes for Doctor 2", ReservationStatus.CONFIRMED, visit2, location2);
        Appointment a = appointmentRepository.save(appointment1);
        Appointment b = appointmentRepository.save(appointment2);

        // Initialize reviews
        Review review1 = new Review("Great experience with Doctor 1!", "Highly recommend Doctor 1.", ReviewStars.FIVE, "fabiocinicolo@gmail.com", a.getId());
        Review review2 = new Review("Great experience with Doctor 2!", "Highly recommend Doctor 2.", ReviewStars.FOUR, "fabiocinicolo@gmail.com", b.getId());

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
        log.info("APPOINTMENTS: {}", appointmentRepository.findAll());
        log.info("REVIEWS: {}", reviewRepository.findAll());
        log.info("DOCTORS: {}", doctorRepository.findAll());
        log.info("CONTACT INFO: {}", contactInfoRepository.findAll());
        log.info("LOCATION REPOSITORY: {}", locationRepository.findAll());
        log.info("VISIT REPOSITORY: {}", visitRepository.findAll());
    }
}
