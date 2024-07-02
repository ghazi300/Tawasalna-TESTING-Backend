package com.tawasalna.tawasalnacrm.repository;

import com.tawasalna.tawasalnacrm.models.ContactUs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends MongoRepository<ContactUs, String> {
}