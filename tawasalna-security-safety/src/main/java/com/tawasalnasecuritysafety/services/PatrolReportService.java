package com.tawasalnasecuritysafety.services;


import com.tawasalnasecuritysafety.models.PatrolReport;
import com.tawasalnasecuritysafety.repos.PatrolReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatrolReportService {
    @Autowired
    private PatrolReportRepository patrolReportRepository;

    public PatrolReport createPatrolReport(PatrolReport report) {
        return patrolReportRepository.save(report);
    }

    public Optional<PatrolReport> getPatrolReportById(String id) {
        return patrolReportRepository.findById(id);
    }

    public List<PatrolReport> getAllPatrolReports() {
        return patrolReportRepository.findAll();
    }

    public PatrolReport updatePatrolReport(String id, PatrolReport report) {
        if (patrolReportRepository.existsById(id)) {
            report.setId(id);
            return patrolReportRepository.save(report);
        }
        return null;
    }

    public void deletePatrolReport(String id) {
        patrolReportRepository.deleteById(id);
    }
}
