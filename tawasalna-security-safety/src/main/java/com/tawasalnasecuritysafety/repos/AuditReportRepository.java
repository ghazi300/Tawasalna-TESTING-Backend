package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.AuditReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditReportRepository extends MongoRepository<AuditReport, Long> {
    Optional<AuditReport> findById(String id);

    void deleteById(String id);
}
