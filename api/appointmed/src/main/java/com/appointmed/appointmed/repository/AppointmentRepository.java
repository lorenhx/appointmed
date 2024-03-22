package com.appointmed.appointmed.repository;

import com.appointmed.appointmed.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
}
