package com.tawasalna.auth.repository;

import com.tawasalna.auth.models.UserVerifCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVerifCodeRepository extends MongoRepository<UserVerifCode, String> {

    Optional<UserVerifCode> findByCodeAndEmail(String code, String email);
}
