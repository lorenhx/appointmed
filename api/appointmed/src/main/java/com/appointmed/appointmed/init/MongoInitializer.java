package com.appointmed.appointmed.init;

import com.appointmed.appointmed.constant.*;
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
        Visit visit1 = new Visit("Consultation", 100.0f, Specialization.CARDIOLOGIST, 45);
        Visit visit2 = new Visit("Routine Checkup", 150.0f, Specialization.DERMATOLOGIST, 30);
        Visit visit3 = new Visit("Simple Checkup", 120.0f, Specialization.ENDOCRINOLOGIST, 60);
        Visit visit4 = new Visit("Special Procedure", 200.0f, Specialization.GASTROENTEROLOGIST, 60);
        Visit visit5 = new Visit("Neurological Evaluation", 80.0f, Specialization.NEUROLOGIST, 30);
        Visit visit6 = new Visit("Oncology Consultation", 90.0f, Specialization.ONCOLOGIST, 45);
        Visit visit7 = new Visit("Eye Examination", 130.0f, Specialization.OPHTHALMOLOGIST, 30);
        Visit visit8 = new Visit("Orthopedic Consultation", 110.0f, Specialization.ORTHOPEDIC_SURGEON, 45);
        Visit visit9 = new Visit("Ear, Nose, and Throat Checkup", 120.0f, Specialization.OTOLARYNGOLOGIST, 60);
        Visit visit10 = new Visit("Pediatric Consultation", 100.0f, Specialization.PEDIATRICIAN, 45);
        Visit visit11 = new Visit("Psychiatric Evaluation", 160.0f, Specialization.PSYCHIATRIST, 30);
        Visit visit12 = new Visit("Respiratory Assessment", 100.0f, Specialization.PULMONOLOGIST, 45);
        Visit visit13 = new Visit("Urology Checkup", 90.0f, Specialization.UROLOGIST, 30);
        Visit visit14 = new Visit("Gynecological Examination", 120.0f, Specialization.GYNECOLOGIST, 60);
        Visit visit15 = new Visit("Kidney Function Assessment", 100.0f, Specialization.NEPHROLOGIST, 45);
        Visit visit16 = new Visit("Rheumatology Consultation", 200.0f, Specialization.RHEUMATOLOGIST, 60);
        Visit visit17 = new Visit("Infectious Disease Evaluation", 90.0f, Specialization.INFECTIOUS_DISEASE_SPECIALIST, 30);
        Visit visit18 = new Visit("Allergy Testing", 80.0f, Specialization.ALLERGIST, 30);
        Visit visit19 = new Visit("Blood Disorder Consultation", 110.0f, Specialization.HEMATOLOGIST, 45);
        Visit visit20 = new Visit("Endocrine Surgery Consultation", 130.0f, Specialization.ENDOCRINE_SURGEON, 30);
        Visit visit21 = new Visit("Cardiothoracic Surgery Consultation", 100.0f, Specialization.CARDIOTHORACIC_SURGEON, 45);
        Visit visit22 = new Visit("Vascular Surgery Consultation", 160.0f, Specialization.VASCULAR_SURGEON, 30);
        Visit visit23 = new Visit("Plastic Surgery Consultation", 100.0f, Specialization.PLASTIC_SURGEON, 45);
        Visit visit24 = new Visit("Maxillofacial Surgery Consultation", 120.0f, Specialization.MAXILLOFACIAL_SURGEON, 60);
        Visit visit25 = new Visit("Dental Consultation", 90.0f, Specialization.ORAL_SURGEON, 30);
        Visit visit26 = new Visit("Routine Checkup", 150.0f, Specialization.CARDIOLOGIST, 60);
        Visit visit27 = new Visit("Echo Test", 200.0f, Specialization.CARDIOLOGIST, 60);
        Visit visit28 = new Visit("Holter Monitoring", 250.0f, Specialization.CARDIOLOGIST, 90);
        visitRepository.saveAll(Arrays.asList(visit1, visit2, visit3, visit4, visit5,
                visit6, visit7, visit8, visit9, visit10,
                visit11, visit12, visit13, visit14, visit15,
                visit16, visit17, visit18, visit19, visit20,
                visit21, visit22, visit23, visit24, visit25, visit26, visit27, visit28));


        // Initialize contact info
        ContactInfo contactInfo0 = new ContactInfo("3294239944", "studiomedicocinicolostaff@email.com", "", "");
        ContactInfo contactInfo1 = new ContactInfo("333222444", "studiomedicocinicolo@email.com", "", "");
        ContactInfo contactInfo2 = new ContactInfo("393999888", "studiomedicobuonocore@gmail.com", "", "");
        ContactInfo contactInfo3 = new ContactInfo("345347894", "studiomedicogallo@gmail.com", "", "");
        ContactInfo contactInfo4 = new ContactInfo("333982743", "studiomedicoesposito@gmail.com", "", "");
        ContactInfo contactInfo5 = new ContactInfo("320231043", "studiomedicodesantis@gmail.com", "", "");
        ContactInfo contactInfo6 = new ContactInfo("313232043", "studiomedicoferrara@gmail.com", "", "");
        ContactInfo contactInfo7 = new ContactInfo("356010232", "studiomedicoconti@email.com", "", "");
        ContactInfo contactInfo8 = new ContactInfo("323040232", "studiomedicoconti@email.com", "", "");

        contactInfoRepository.saveAll(List.of(contactInfo1, contactInfo2, contactInfo3, contactInfo4, contactInfo5, contactInfo6, contactInfo7, contactInfo8));


        // Initialize locations
        Location locationFabioCinicolo1 = new Location("Via Napoli, Pozzuoli", "Studio medico Cinicolo", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.CHILDREN), Collections.singletonList(contactInfo1), List.of(visit1, visit26));
        Location locationFabioCinicolo2 = new Location("Via Roma, Napoli", "Studio medico Cinicolo", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.PREGNANT), Collections.singletonList(contactInfo0), List.of(visit27, visit28, visit26));
        Location locationAnielloBuonocore1 = new Location("Via Toledo, Napoli", "Studio medico Buonocore", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.PREGNANT), Collections.singletonList(contactInfo2), List.of(visit3, visit4, visit26));
        Location locationGiulioEsposito1 = new Location("Corso Umberto I, Napoli", "Studio medico Esposito", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.CHILDREN), Collections.singletonList(contactInfo4), List.of(visit5, visit6, visit27));
        Location locationLorenzoGallo1 = new Location("Via Caracciolo Napoli", "Studio medico Gallo", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.HEARING_IMPAIRMENT), Collections.singletonList(contactInfo3), List.of(visit7, visit8, visit28));
        Location locationSofiaDeSantis1 = new Location("Via San Gregorio Armeno, Napoli", "Studio medico De Santis", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.HEARING_IMPAIRMENT), Collections.singletonList(contactInfo5), List.of(visit9, visit10, visit1));
        Location locationAntonioFerrara1 = new Location("Corso Protopisani Nord", "Studio medico Ferrara", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.HEARING_IMPAIRMENT), Collections.singletonList(contactInfo6), List.of(visit11, visit12, visit2));
        Location locationLauraRizzo1 = new Location("Piazze Del Plebiscito, Napoli", "Studio medico Rizzo", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.PREGNANT), Collections.singletonList(contactInfo7), List.of(visit13, visit14, visit3));
        Location locationMarcoConti1 = new Location("Piazza Cavour, Napoli", "Studio medico Conti", "9:00 AM - 5:00 PM",
                Collections.singletonList(PaymentType.CASH), Collections.singletonList(Accessibility.HEARING_IMPAIRMENT), Collections.singletonList(contactInfo8), List.of(visit15, visit16, visit4));

        // Save all doctor locations
        locationRepository.saveAll(List.of(locationFabioCinicolo1, locationFabioCinicolo2, locationAnielloBuonocore1, locationGiulioEsposito1, locationLorenzoGallo1,
                locationSofiaDeSantis1, locationAntonioFerrara1, locationLauraRizzo1, locationMarcoConti1));


        // Initialize doctors
        Doctor doctorFabioCinicolo = new Doctor("fabiocinicolo@gmail.com", null, Collections.singletonList(Specialization.CARDIOLOGIST),
                List.of(locationFabioCinicolo1, locationFabioCinicolo2), Collections.emptyList());
        Doctor doctorAnielloBuonocore = new Doctor("anibuonocore@gmail.com", null, Collections.singletonList(Specialization.ENDOCRINOLOGIST),
                List.of(locationAnielloBuonocore1), Collections.emptyList());
        Doctor doctorGiulioEsposito = new Doctor("g_esposito@gmail.com", null, Collections.singletonList(Specialization.NEUROLOGIST),
                List.of(locationGiulioEsposito1), Collections.emptyList());
        Doctor doctorLorenzoGallo = new Doctor("lorenzo.gallo@gmail.com", null, Collections.singletonList(Specialization.ONCOLOGIST),
                List.of(locationLorenzoGallo1), Collections.emptyList());
        Doctor doctorSofiaDeSantis = new Doctor("sant.sofia@gmail.com", null, Collections.singletonList(Specialization.OPHTHALMOLOGIST),
                List.of(locationSofiaDeSantis1), Collections.emptyList());
        Doctor doctorAntonioFerrara = new Doctor("ferrara.antonio@gmail.com", null, Collections.singletonList(Specialization.ORTHOPEDIC_SURGEON),
                List.of(locationAntonioFerrara1), Collections.emptyList());
        Doctor doctorLauraRizzo = new Doctor("rizzo.laura@gmail.com", null, Collections.singletonList(Specialization.OTOLARYNGOLOGIST),
                List.of(locationLauraRizzo1), Collections.emptyList());
        Doctor doctorMarcoConti = new Doctor("marco.conti@gmail.com", null, Collections.singletonList(Specialization.PEDIATRICIAN),
                List.of(locationMarcoConti1), Collections.emptyList());

        // Save all doctors
        doctorRepository.saveAll(List.of(doctorFabioCinicolo, doctorAnielloBuonocore, doctorGiulioEsposito, doctorLorenzoGallo,
                doctorSofiaDeSantis, doctorAntonioFerrara, doctorLauraRizzo, doctorMarcoConti));


        // Initialize appointments
        Appointment appointmentFabioCinicolo1 = new Appointment("evilpippozzo@gmail.com", "fabiocinicolo@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "It is recommended to come 5 min before the actual appointment.", ReservationStatus.CONFIRMED, visit1, locationFabioCinicolo1);
        Appointment appointmentFabioCinicolo2 = new Appointment("andrearossi@gmail.com", "fabiocinicolo@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.PENDING, visit1, locationFabioCinicolo2);
        Appointment appointmentFabioCinicolo3 = new Appointment("mariarossi@gmail.com", "fabiocinicolo@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.REJECTED, visit26, locationFabioCinicolo1);
        Appointment appointmentFabioCinicolo4 = new Appointment("giuseppe.verdi@gmail.com", "fabiocinicolo@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.PENDING, visit27, locationFabioCinicolo1);
        Appointment appointmentFabioCinicolo5 = new Appointment("annabianchi@gmail.com", "fabiocinicolo@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit28, locationFabioCinicolo1);

        Appointment appointmentAnielloBuonocore1 = new Appointment("maria.rossi@gmail.com", "anibuonocore@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "Please bring your medical history documents.", ReservationStatus.REJECTED, visit1, locationAnielloBuonocore1);
        Appointment appointmentAnielloBuonocore2 = new Appointment("luca.verdi@gmail.com", "anibuonocore@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.PENDING, visit3, locationAnielloBuonocore1);
        Appointment appointmentAnielloBuonocore3 = new Appointment("chiara.bianchi@gmail.com", "anibuonocore@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit5, locationAnielloBuonocore1);
        Appointment appointmentAnielloBuonocore4 = new Appointment("simone.gialli@gmail.com", "anibuonocore@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.PENDING, visit7, locationAnielloBuonocore1);
        Appointment appointmentAnielloBuonocore5 = new Appointment("ilaria.verdi@gmail.com", "anibuonocore@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.REJECTED, visit9, locationAnielloBuonocore1);

        Appointment appointmentGiulioEsposito1 = new Appointment("stefano.rossi@gmail.com", "g_esposito@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "Remember to fast for 12 hours before the appointment.", ReservationStatus.PENDING, visit2, locationGiulioEsposito1);
        Appointment appointmentGiulioEsposito2 = new Appointment("giovanna.verdi@gmail.com", "g_esposito@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit4, locationGiulioEsposito1);
        Appointment appointmentGiulioEsposito3 = new Appointment("giorgio.bianchi@gmail.com", "g_esposito@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.REJECTED, visit6, locationGiulioEsposito1);
        Appointment appointmentGiulioEsposito4 = new Appointment("chiara.gialli@gmail.com", "g_esposito@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.PENDING, visit8, locationGiulioEsposito1);
        Appointment appointmentGiulioEsposito5 = new Appointment("mario.rossi@gmail.com", "g_esposito@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit10, locationGiulioEsposito1);

        Appointment appointmentLorenzoGallo1 = new Appointment("Giulio.rossi@gmail.com", "lorenzo.gallo@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "Please bring your previous medical reports.", ReservationStatus.CONFIRMED, visit11, locationLorenzoGallo1);
        Appointment appointmentLorenzoGallo2 = new Appointment("andrea.verdi@gmail.com", "lorenzo.gallo@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.REJECTED, visit13, locationLorenzoGallo1);
        Appointment appointmentLorenzoGallo3 = new Appointment("anna.bianchi@gmail.com", "lorenzo.gallo@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.PENDING, visit15, locationLorenzoGallo1);
        Appointment appointmentLorenzoGallo4 = new Appointment("michele.gialli@gmail.com", "lorenzo.gallo@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit17, locationLorenzoGallo1);
        Appointment appointmentLorenzoGallo5 = new Appointment("giorgio.rossi@gmail.com", "lorenzo.gallo@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.PENDING, visit19, locationLorenzoGallo1);

        Appointment appointmentSofiaDeSantis1 = new Appointment("luca.rossi@gmail.com", "sant.sofia@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "Please bring your insurance details.", ReservationStatus.PENDING, visit12, locationSofiaDeSantis1);
        Appointment appointmentSofiaDeSantis2 = new Appointment("alessia.verdi@gmail.com", "sant.sofia@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit14, locationSofiaDeSantis1);
        Appointment appointmentSofiaDeSantis3 = new Appointment("giovanni.bianchi@gmail.com", "sant.sofia@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.REJECTED, visit16, locationSofiaDeSantis1);
        Appointment appointmentSofiaDeSantis4 = new Appointment("anna.gialli@gmail.com", "sant.sofia@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.PENDING, visit18, locationSofiaDeSantis1);
        Appointment appointmentSofiaDeSantis5 = new Appointment("marco.rossi@gmail.com", "sant.sofia@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit20, locationSofiaDeSantis1);

        Appointment appointmentAntonioFerrara1 = new Appointment("chiara.rossi@gmail.com", "ferrara.antonio@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "Please arrive 10 minutes before the scheduled time.", ReservationStatus.REJECTED, visit21, locationAntonioFerrara1);
        Appointment appointmentAntonioFerrara2 = new Appointment("luigi.verdi@gmail.com", "ferrara.antonio@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.PENDING, visit23, locationAntonioFerrara1);
        Appointment appointmentAntonioFerrara3 = new Appointment("marta.bianchi@gmail.com", "ferrara.antonio@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit25, locationAntonioFerrara1);
        Appointment appointmentAntonioFerrara4 = new Appointment("paolo.gialli@gmail.com", "ferrara.antonio@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.PENDING, visit27, locationAntonioFerrara1);
        Appointment appointmentAntonioFerrara5 = new Appointment("maria.rossi@gmail.com", "ferrara.antonio@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.REJECTED, visit3, locationAntonioFerrara1);

        // Initialize Marco Conti appointments
        Appointment appointmentMarcoConti1 = new Appointment("anna.rossi@gmail.com", "marco.conti@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "Please bring any relevant medical documents.", ReservationStatus.PENDING, visit25, locationMarcoConti1);
        Appointment appointmentMarcoConti2 = new Appointment("paolo.verdi@gmail.com", "marco.conti@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit23, locationMarcoConti1);
        Appointment appointmentMarcoConti3 = new Appointment("marta.bianchi@gmail.com", "marco.conti@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.REJECTED, visit21, locationMarcoConti1);
        Appointment appointmentMarcoConti4 = new Appointment("luigi.gialli@gmail.com", "marco.conti@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.PENDING, visit19, locationMarcoConti1);
        Appointment appointmentMarcoConti5 = new Appointment("andrea.rossi@gmail.com", "marco.conti@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit17, locationMarcoConti1);

        // Initialize Laura Rizzo appointments
        Appointment appointmentLauraRizzo1 = new Appointment("marco.rossi@gmail.com", "rizzo.laura@gmail.com", Instant.now().plusSeconds(3600),
                Instant.now(), "Please arrive 15 minutes before the appointment.", ReservationStatus.CONFIRMED, visit15, locationLauraRizzo1);
        Appointment appointmentLauraRizzo2 = new Appointment("chiara.verdi@gmail.com", "rizzo.laura@gmail.com", Instant.now().plusSeconds(7200),
                Instant.now(), null, ReservationStatus.REJECTED, visit13, locationLauraRizzo1);
        Appointment appointmentLauraRizzo3 = new Appointment("luigi.bianchi@gmail.com", "rizzo.laura@gmail.com", Instant.now().plusSeconds(10800),
                Instant.now(), null, ReservationStatus.PENDING, visit11, locationLauraRizzo1);
        Appointment appointmentLauraRizzo4 = new Appointment("anna.gialli@gmail.com", "rizzo.laura@gmail.com", Instant.now().plusSeconds(14400),
                Instant.now(), null, ReservationStatus.CONFIRMED, visit9, locationLauraRizzo1);
        Appointment appointmentLauraRizzo5 = new Appointment("mario.rossi@gmail.com", "rizzo.laura@gmail.com", Instant.now().plusSeconds(18000),
                Instant.now(), null, ReservationStatus.PENDING, visit7, locationLauraRizzo1);

        // Save all appointments
        appointmentRepository.saveAll(Arrays.asList(
                appointmentFabioCinicolo1, appointmentFabioCinicolo2, appointmentFabioCinicolo3, appointmentFabioCinicolo4, appointmentFabioCinicolo5,
                appointmentAnielloBuonocore1, appointmentAnielloBuonocore2, appointmentAnielloBuonocore3, appointmentAnielloBuonocore4, appointmentAnielloBuonocore5,
                appointmentGiulioEsposito1, appointmentGiulioEsposito2, appointmentGiulioEsposito3, appointmentGiulioEsposito4, appointmentGiulioEsposito5,
                appointmentLorenzoGallo1, appointmentLorenzoGallo2, appointmentLorenzoGallo3, appointmentLorenzoGallo4, appointmentLorenzoGallo5,
                appointmentSofiaDeSantis1, appointmentSofiaDeSantis2, appointmentSofiaDeSantis3, appointmentSofiaDeSantis4, appointmentSofiaDeSantis5,
                appointmentAntonioFerrara1, appointmentAntonioFerrara2, appointmentAntonioFerrara3, appointmentAntonioFerrara4, appointmentAntonioFerrara5,
                appointmentMarcoConti1, appointmentMarcoConti2, appointmentMarcoConti3, appointmentMarcoConti4, appointmentMarcoConti5,
                appointmentLauraRizzo1, appointmentLauraRizzo2, appointmentLauraRizzo3, appointmentLauraRizzo4, appointmentLauraRizzo5
        ));

        // Initialize reviews
        Review reviewFabioCinicolo1 = new Review("Excellent service", "Dr. Fabio Cinicolo provided exceptional care during my consultation. Highly recommended.", ReviewStars.FIVE, "evilpippozzo@gmail.com", appointmentFabioCinicolo1.getId());
        Review reviewFabioCinicolo2 = new Review("Disappointing experience", "My appointment with Dr. Fabio Cinicolo was rushed and unsatisfactory. Didn't address all my concerns.", ReviewStars.TWO, "andrearossi@gmail.com", appointmentFabioCinicolo2.getId());
        Review reviewFabioCinicolo3 = new Review("Professional and caring", "Dr. Fabio Cinicolo was professional and caring. Listened to my concerns and provided valuable insights.", ReviewStars.FOUR, "mariarossi@gmail.com", appointmentFabioCinicolo3.getId());
        Review reviewFabioCinicolo4 = new Review("Could have been better", "I had a negative experience with Dr. Fabio Cinicolo. The treatment didn't meet my expectations.", ReviewStars.ONE, "giuseppe.verdi@gmail.com", appointmentFabioCinicolo4.getId());
        Review reviewFabioCinicolo5 = new Review("Great consultation", "Dr. Fabio Cinicolo conducted a thorough consultation. I'm satisfied with the service provided.", ReviewStars.FOUR, "annabianchi@gmail.com", appointmentFabioCinicolo5.getId());

        Review reviewAnielloBuonocore1 = new Review("Good consultation", "Dr. Aniello Buonocore provided a thorough consultation and addressed all my concerns.", ReviewStars.FOUR, "maria.rossi@gmail.com", appointmentAnielloBuonocore1.getId());
        Review reviewAnielloBuonocore2 = new Review("Unsatisfactory experience", "My experience with Dr. Aniello Buonocore was disappointing. Didn't feel heard during the appointment.", ReviewStars.ONE, "luca.verdi@gmail.com", appointmentAnielloBuonocore2.getId());
        Review reviewAnielloBuonocore5 = new Review("Satisfied with the service", "Overall, I'm satisfied with the service provided by Dr. Aniello Buonocore.", ReviewStars.THREE, "ilaria.verdi@gmail.com", appointmentAnielloBuonocore5.getId());

        Review reviewGiulioEsposito1 = new Review("Excellent dermatologist", "Dr. Giulio Esposito is an excellent dermatologist. I'm satisfied with the treatment.", ReviewStars.FIVE, "stefano.rossi@gmail.com", appointmentGiulioEsposito1.getId());

        Review reviewLorenzoGallo1 = new Review("Professional and caring", "Dr. Lorenzo Gallo was professional and caring during the consultation. Satisfied with the service.", ReviewStars.FOUR, "Giulio.rossi@gmail.com", appointmentLorenzoGallo1.getId());
        Review reviewLorenzoGallo2 = new Review("Unsatisfactory experience", "My experience with Dr. Lorenzo Gallo was unsatisfactory. Didn't feel heard.", ReviewStars.ONE, "andrea.verdi@gmail.com", appointmentLorenzoGallo2.getId());
        Review reviewLorenzoGallo3 = new Review("Could have been better", "Expected better treatment from Dr. Lorenzo Gallo. Disappointed with the outcome.", ReviewStars.TWO, "anna.bianchi@gmail.com", appointmentLorenzoGallo3.getId());
        Review reviewLorenzoGallo4 = new Review("Good service", "Overall, I received good service from Dr. Lorenzo Gallo.", ReviewStars.FOUR, "michele.gialli@gmail.com", appointmentLorenzoGallo4.getId());


        Review reviewSofiaDeSantis5 = new Review("Satisfied", "Overall, I'm satisfied with the treatment provided by Dr. Sofia De Santis.", ReviewStars.THREE, "marco.rossi@gmail.com", appointmentSofiaDeSantis5.getId());

        Review reviewAntonioFerrara1 = new Review("Excellent service", "Dr. Antonio Ferrara provided excellent service during the consultation. Highly recommended.", ReviewStars.FIVE, "chiara.rossi@gmail.com", appointmentAntonioFerrara1.getId());
        Review reviewAntonioFerrara2 = new Review("Could have been better", "Expected better treatment from Dr. Antonio Ferrara. Didn't feel satisfied with the outcome.", ReviewStars.TWO, "luigi.verdi@gmail.com", appointmentAntonioFerrara2.getId());
        Review reviewAntonioFerrara3 = new Review("Professionalism", "Appreciate Dr. Antonio Ferrara's professionalism and expertise in the field.", ReviewStars.FOUR, "marta.bianchi@gmail.com", appointmentAntonioFerrara3.getId());
        Review reviewAntonioFerrara4 = new Review("Disappointing experience", "My experience with Dr. Antonio Ferrara was disappointing. Didn't feel heard during the consultation.", ReviewStars.ONE, "paolo.gialli@gmail.com", appointmentAntonioFerrara4.getId());
        Review reviewAntonioFerrara5 = new Review("Satisfied", "Overall, I'm satisfied with the treatment provided by Dr. Antonio Ferrara.", ReviewStars.THREE, "maria.rossi@gmail.com", appointmentAntonioFerrara5.getId());

        Review reviewMarcoConti1 = new Review("Good consultation", "Had a good consultation with Dr. Marco Conti. Listened to my concerns.", ReviewStars.FOUR, "anna.rossi@gmail.com", appointmentMarcoConti1.getId());
        Review reviewMarcoConti2 = new Review("Unsatisfactory experience", "My experience with Dr. Marco Conti was unsatisfactory. Expected more from the appointment.", ReviewStars.ONE, "paolo.verdi@gmail.com", appointmentMarcoConti2.getId());
        Review reviewMarcoConti3 = new Review("Satisfied", "Overall, I'm satisfied with the treatment provided by Dr. Marco Conti.", ReviewStars.FIVE, "marta.bianchi@gmail.com", appointmentMarcoConti3.getId());

        Review reviewLauraRizzo1 = new Review("Excellent service", "Dr. Laura Rizzo provided excellent service during the consultation. Highly recommended.", ReviewStars.FIVE, "marco.rossi@gmail.com", appointmentLauraRizzo1.getId());
        Review reviewLauraRizzo2 = new Review("Could have been better", "Expected better treatment from Dr. Laura Rizzo. Didn't feel satisfied with the outcome.", ReviewStars.TWO, "chiara.verdi@gmail.com", appointmentLauraRizzo2.getId());

        reviewRepository.saveAll(Arrays.asList(
                reviewFabioCinicolo1, reviewFabioCinicolo2, reviewFabioCinicolo3, reviewFabioCinicolo4, reviewFabioCinicolo5,
                reviewAnielloBuonocore1, reviewAnielloBuonocore2, reviewAnielloBuonocore5,
                reviewGiulioEsposito1,
                reviewLorenzoGallo1, reviewLorenzoGallo2, reviewLorenzoGallo3, reviewLorenzoGallo4,
                reviewSofiaDeSantis5,
                reviewAntonioFerrara1, reviewAntonioFerrara2, reviewAntonioFerrara3, reviewAntonioFerrara4, reviewAntonioFerrara5,
                reviewMarcoConti1, reviewMarcoConti2, reviewMarcoConti3,
                reviewLauraRizzo1, reviewLauraRizzo2
        ));

        // Add doctor reviews
        doctorFabioCinicolo.setReviews(List.of(reviewFabioCinicolo1, reviewFabioCinicolo2, reviewFabioCinicolo3, reviewFabioCinicolo4, reviewFabioCinicolo5));
        doctorAnielloBuonocore.setReviews(List.of(reviewAnielloBuonocore1, reviewAnielloBuonocore2, reviewAnielloBuonocore5));
        doctorGiulioEsposito.setReviews(List.of(reviewGiulioEsposito1));
        doctorLorenzoGallo.setReviews(List.of(reviewLorenzoGallo1, reviewLorenzoGallo2, reviewLorenzoGallo3, reviewLorenzoGallo4));
        doctorSofiaDeSantis.setReviews(List.of(reviewSofiaDeSantis5));
        doctorAntonioFerrara.setReviews(List.of(reviewAntonioFerrara1, reviewAntonioFerrara2, reviewAntonioFerrara3, reviewAntonioFerrara4, reviewAntonioFerrara5));
        doctorMarcoConti.setReviews(List.of(reviewMarcoConti1, reviewMarcoConti2, reviewMarcoConti3));
        doctorLauraRizzo.setReviews(List.of(reviewLauraRizzo1, reviewLauraRizzo2));

        // Set appointments
        doctorFabioCinicolo.setAppointments(List.of(appointmentFabioCinicolo1, appointmentFabioCinicolo2, appointmentFabioCinicolo3, appointmentFabioCinicolo4, appointmentFabioCinicolo5));
        doctorAnielloBuonocore.setAppointments(List.of(appointmentAnielloBuonocore1, appointmentAnielloBuonocore2, appointmentAnielloBuonocore3, appointmentAnielloBuonocore4, appointmentAnielloBuonocore5));
        doctorGiulioEsposito.setAppointments(List.of(appointmentGiulioEsposito1, appointmentGiulioEsposito2, appointmentGiulioEsposito3, appointmentGiulioEsposito4, appointmentGiulioEsposito5));
        doctorLorenzoGallo.setAppointments(List.of(appointmentLorenzoGallo1, appointmentLorenzoGallo2, appointmentLorenzoGallo3, appointmentLorenzoGallo4, appointmentLorenzoGallo5));
        doctorSofiaDeSantis.setAppointments(List.of(appointmentSofiaDeSantis1, appointmentSofiaDeSantis2, appointmentSofiaDeSantis3, appointmentSofiaDeSantis4, appointmentSofiaDeSantis5));
        doctorAntonioFerrara.setAppointments(List.of(appointmentAntonioFerrara1, appointmentAntonioFerrara2, appointmentAntonioFerrara3, appointmentAntonioFerrara4, appointmentAntonioFerrara5));
        doctorMarcoConti.setAppointments(List.of(appointmentMarcoConti1, appointmentMarcoConti2, appointmentMarcoConti3, appointmentMarcoConti4, appointmentMarcoConti5));
        doctorLauraRizzo.setAppointments(List.of(appointmentLauraRizzo1, appointmentLauraRizzo2, appointmentLauraRizzo3, appointmentLauraRizzo4, appointmentLauraRizzo5));

        doctorRepository.saveAll(List.of(doctorAnielloBuonocore, doctorFabioCinicolo, doctorAntonioFerrara, doctorLauraRizzo, doctorMarcoConti, doctorGiulioEsposito, doctorLorenzoGallo, doctorSofiaDeSantis));

        log.info("Successfully populated MongoDB data!");
    }

    private void removeData() {
        appointmentRepository.deleteAll();
        reviewRepository.deleteAll();
        doctorRepository.deleteAll();
        contactInfoRepository.deleteAll();
        locationRepository.deleteAll();
        visitRepository.deleteAll();
    }

    private void printData() {
        log.info("APPOINTMENTS: {}", appointmentRepository.findAll());
        log.info("REVIEWS: {}", reviewRepository.findAll());
        log.info("DOCTORS: {}", doctorRepository.findAll());
        log.info("CONTACT INFO: {}", contactInfoRepository.findAll());
        log.info("LOCATION REPOSITORY: {}", locationRepository.findAll());
        log.info("VISIT REPOSITORY: {}", visitRepository.findAll());
    }
}

