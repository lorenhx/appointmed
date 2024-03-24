package com.appointmed.appointmed;

import com.appointmed.appointmed.constant.*;
import com.appointmed.appointmed.model.*;
import com.appointmed.appointmed.repository.*;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DataGenerator implements CommandLineRunner {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private Keycloak keycloak; // Autowire Keycloak Admin Client
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Override
    public void run(String... args) throws Exception {

        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();
        locationRepository.deleteAll();
        visitRepository.deleteAll();
        reviewRepository.deleteAll();
        contactInfoRepository.deleteAll();

        // Generate synthetic data
        generateData();

        // Query MongoDB to verify data
        queryData();

        // Query Keycloak to retrieve all users
        List<UserRepresentation> users = getAllUsersFromKeycloakRealm();
        System.out.println("Users:");
        for (UserRepresentation user : users) {
            System.out.println(user.getEmail());
        }
    }

    private void generateData() {

        // Generate synthetic data for reviews
        Review review = new Review();
        review.setTitle("Great Experience");
        review.setDescription("The doctor was very knowledgeable and attentive.");
        review.setStars(ReviewStars.FOUR);
        review.setPatientEmail("fabiocinicolo@gmail.com");

        List<Review> l = new ArrayList<>();
        l.add(review);
        reviewRepository.saveAll(l);


        List<ContactInfo> contactInfo = new ArrayList<>();
        contactInfo.add(new ContactInfo("123-456-7890", "fabiocinicolo@gmail.com", null, "www.clinicxyz.com"));
        contactInfoRepository.saveAll(contactInfo);


        List<Location> locations = new ArrayList<>();
        // Generate synthetic data for locations near Naples, Italy
        Location location1 = new Location("Via Toledo, Naples", "Hospital ABC", "24/7",
                Arrays.asList(PaymentType.CASH, PaymentType.CREDIT_CARD), List.of(Accessibility.PREGNANT),
                contactInfo,
                null);
        locations.add(location1);

        List<Visit> visits = new ArrayList<>();
        visits.add(new Visit("Consultation", 100.0f, Specialization.CARDIOLOGIST, 30));
        visitRepository.saveAll(visits);

        contactInfo = new ArrayList<>();
        contactInfo.add(new ContactInfo("098-765-4321", "fabiocinicolo@gmail.com", null, "www.hospitalabc.com"));
        contactInfoRepository.saveAll(contactInfo);

        visits = new ArrayList<>();
        visits.add(new Visit("Emergency Care", 150.0f, Specialization.EMERGENCY_MEDICINE_PHYSICIAN, 60));

        visitRepository.saveAll(visits);

        locations = new ArrayList<>();
        // Generate synthetic data for locations near Naples, Italy
        Location location2 = new Location("Via Cacciapuoti, Pozzuoli", "Clinic XYZ", "9:00 AM - 5:00 PM",
                Arrays.asList(PaymentType.CREDIT_CARD, PaymentType.DEBIT_CARD), List.of(Accessibility.MOBILITY_IMPAIRMENT),
                contactInfo,
                visits);
        locations.add(location2);

        // Save locations
        List<Location> savedLocations = locationRepository.saveAll(locations);

        // Generate synthetic data for doctors
        Doctor doctor = new Doctor("fabiocinicolo@gmail.com", l, List.of(Specialization.CARDIOLOGIST), locations, new ArrayList<>());
        doctorRepository.save(doctor);

        // Generate synthetic data for appointments
        for (Location location : savedLocations) {
            for (Visit visit : location.getVisits()) {
                Appointment appointment = new Appointment();
                appointment.setPatientEmail("fabiocinicolo@gmail.com");
                appointment.setStartTimestamp(Instant.now());
                appointment.setIssuedTimestamp(Instant.now());
                appointment.setNotes("Follow-up appointment");
                appointment.setStatus(ReservationStatus.CONFIRMED);
                appointment.setVisit(new Visit("Emergency Care", 150.0f, Specialization.EMERGENCY_MEDICINE_PHYSICIAN, 60));
                appointmentRepository.save(appointment);
                Appointment appointment1 = new Appointment();
                appointment1.setPatientEmail("fabiocinicolo@gmail.com");
                appointment1.setStartTimestamp(Instant.now());
                appointment1.setIssuedTimestamp(Instant.now());
                appointment1.setNotes("Follow-up appointment");
                appointment1.setStatus(ReservationStatus.CONFIRMED);
                appointment1.setVisit(new Visit("Emergency Care", 150.0f, Specialization.EMERGENCY_MEDICINE_PHYSICIAN, 60));
                appointmentRepository.save(appointment1);
                Optional<Doctor> doctor1 = doctorRepository.findById("fabiocinicolo@gmail.com");
                Doctor doctor2 = doctor1.get();
                doctor2.getAppointments().add(appointment);
                doctor2.getAppointments().add(appointment1);
                doctorRepository.save(doctor2);
            }
        }
    }

    private void queryData() {


        System.out.println("Reivews:");

        System.out.println(reviewRepository.findAll());
        // Query and print data to verify
        System.out.println("Appointments:");
        System.out.println(appointmentRepository.findAll());

        System.out.println("Doctors:");
        System.out.println(doctorRepository.findAll());

        System.out.println("Locations:");
        System.out.println(locationRepository.findAll());

        System.out.println("Visits:");
        System.out.println(visitRepository.findAll());
    }

    private List<UserRepresentation> getAllUsersFromKeycloakRealm() {
        // Get the realm resource
        RealmResource realmResource = keycloak.realm("master");

        // Get the users resource
        UsersResource usersResource = realmResource.users();

        // Retrieve all users from the realm
        return usersResource.list();
    }
}
