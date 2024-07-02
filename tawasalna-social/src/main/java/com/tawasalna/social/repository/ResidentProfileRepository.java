package com.tawasalna.social.repository;

import com.tawasalna.shared.userapi.model.ResidentProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentProfileRepository extends MongoRepository<ResidentProfile, String> {
}
