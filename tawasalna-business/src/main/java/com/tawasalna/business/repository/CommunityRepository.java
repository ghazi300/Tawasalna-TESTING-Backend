package com.tawasalna.business.repository;

import com.tawasalna.shared.communityapi.model.Community;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends MongoRepository<Community, String> {
}
