package com.appointmed.appointmed.repository;


import com.appointmed.appointmed.constant.Specialization;
import com.appointmed.appointmed.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findBySpecializationsIn(List<Specialization> specializations);
}
