package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.Compliance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplianceRepository extends MongoRepository<Compliance, Long> {
    Optional<Compliance> findById(String id);

    void deleteById(String id);
    long countByStatus(String status);

}
