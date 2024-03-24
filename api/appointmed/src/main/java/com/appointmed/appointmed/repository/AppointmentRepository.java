package com.appointmed.appointmed.repository;

import com.appointmed.appointmed.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    @Query("{'visit.type': ?0}")
    List<Appointment> findByVisitType(String visitType);

}
