package com.tawasalna.social.repository;

import com.tawasalna.shared.userapi.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<Users> findByEmail(String email);

    @Query("{'roles.id': ?0}")
    List<Users> findByRoleId(String roleId);

    List<Users> findByRolesId(String roleId);

    Optional<Users> findUsersByUsername(String username);

    List<Users> findByCommunityId(String roleId);

    List<Users> findByResidentProfileFullNameContainingIgnoreCase(String fullName);
}
