package com.tawasalna.social.repository;


import com.tawasalna.shared.communityapi.model.Community;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends MongoRepository<Community, String> {
    Optional<Community> findByName(String name);
    List<Community> findByAdmins_Id(String adminId);
    boolean existsByName(String name);
    @Query("{ 'admins.id': ?0 }")
    List<Community> findByAdminId(String userId);
}
