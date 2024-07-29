package com.example.managementcoordination.repositories;

import com.example.managementcoordination.entities.Newsletter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends MongoRepository<Newsletter,String> {
}
