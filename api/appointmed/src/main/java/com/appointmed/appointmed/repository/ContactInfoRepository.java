package com.appointmed.appointmed.repository;

import com.appointmed.appointmed.model.ContactInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactInfoRepository extends MongoRepository<ContactInfo, String> {
}
