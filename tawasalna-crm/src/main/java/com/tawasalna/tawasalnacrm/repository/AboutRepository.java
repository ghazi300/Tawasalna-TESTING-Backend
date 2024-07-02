package com.tawasalna.tawasalnacrm.repository;

import com.tawasalna.tawasalnacrm.models.About;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutRepository extends MongoRepository<About, String> {
}
