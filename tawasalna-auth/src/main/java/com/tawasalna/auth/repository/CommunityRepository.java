package com.tawasalna.auth.repository;


import com.tawasalna.shared.communityapi.model.Community;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends MongoRepository<Community, String> {
    Optional<Community> findByName(String name);

    boolean existsByName(String name);

}
