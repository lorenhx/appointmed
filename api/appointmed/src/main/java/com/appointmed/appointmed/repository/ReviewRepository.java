package com.appointmed.appointmed.repository;

import com.appointmed.appointmed.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
