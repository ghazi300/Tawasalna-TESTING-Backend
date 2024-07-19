package com.tawasalna.auth.repository;

import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.RolesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<Users> findByEmail(String email);

    List<Users> findByRolesId(String roleId);

    Optional<Users> findUsersByUsername(String username);

    List<Users> findByResidentProfileFullNameContainingIgnoreCase(String fullName);

    List<Users> findByCommunityId(String CommunityId);


    List<Users> findByRolesNameAndCommunityId(RolesEnum roleName, String communityId);

    Page<Users> findByCommunityId(String id, Pageable pageable);
}
