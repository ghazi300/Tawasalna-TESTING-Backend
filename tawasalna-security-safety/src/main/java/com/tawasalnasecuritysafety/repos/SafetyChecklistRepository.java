package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.SafetyChecklist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SafetyChecklistRepository extends MongoRepository<SafetyChecklist, Long> {
    Optional<SafetyChecklist> findById(String id);

    void deleteById(String id);
}
