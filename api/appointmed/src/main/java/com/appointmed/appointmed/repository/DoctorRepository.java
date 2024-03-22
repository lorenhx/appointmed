package com.appointmed.appointmed.repository;


import com.appointmed.appointmed.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
}
