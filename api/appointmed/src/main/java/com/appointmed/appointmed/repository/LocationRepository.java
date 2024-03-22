package com.appointmed.appointmed.repository;

import com.appointmed.appointmed.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {
}
