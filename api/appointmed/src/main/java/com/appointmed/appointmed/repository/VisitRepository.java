package com.appointmed.appointmed.repository;

import com.appointmed.appointmed.model.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<Visit, String> {
}
