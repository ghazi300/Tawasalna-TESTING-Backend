package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.PatrolReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolReportRepository extends MongoRepository<PatrolReport, String > {
}
