package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.AccessControlLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessControlLogRepository extends MongoRepository<AccessControlLog, String> {
}
