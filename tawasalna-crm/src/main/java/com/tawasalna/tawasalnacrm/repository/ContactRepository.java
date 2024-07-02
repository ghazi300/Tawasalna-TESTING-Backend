package com.tawasalna.tawasalnacrm.repository;

import com.tawasalna.tawasalnacrm.models.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {
}
