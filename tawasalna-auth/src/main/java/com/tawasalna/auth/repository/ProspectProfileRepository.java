package com.tawasalna.auth.repository;

import com.tawasalna.auth.models.ProspectProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProspectProfileRepository extends MongoRepository<ProspectProfile, String> {
}
