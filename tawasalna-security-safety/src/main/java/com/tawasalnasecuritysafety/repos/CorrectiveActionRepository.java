package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.CorrectiveAction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorrectiveActionRepository extends MongoRepository<CorrectiveAction, Long> {
    Optional<CorrectiveAction> findById(String id);

    void deleteById(String id);
}
