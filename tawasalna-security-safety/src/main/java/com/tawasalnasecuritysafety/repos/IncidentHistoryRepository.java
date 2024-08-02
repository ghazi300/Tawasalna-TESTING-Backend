package com.tawasalnasecuritysafety.repos;


import com.tawasalnasecuritysafety.models.IncidentHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncidentHistoryRepository extends MongoRepository<IncidentHistory, Long> {
    Optional<IncidentHistory> findById(String id);

    void deleteById(String id);
}
