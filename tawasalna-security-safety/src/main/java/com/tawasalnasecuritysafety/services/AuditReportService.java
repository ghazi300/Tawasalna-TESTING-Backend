package com.tawasalnasecuritysafety.services;

import com.tawasalnasecuritysafety.models.AuditReport;
import com.tawasalnasecuritysafety.repos.AuditReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuditReportService {
    @Autowired
    private AuditReportRepository auditReportRepository;

    public List<AuditReport> getAllAuditReports() {
        return auditReportRepository.findAll();
    }

    public Optional<AuditReport> getAuditReportById(String id) {
        return auditReportRepository.findById(id);
    }

    public AuditReport createAuditReport(AuditReport auditReport) {
        return auditReportRepository.save(auditReport);
    }

    public AuditReport updateAuditReport(String id, AuditReport auditReportDetails) {
        AuditReport auditReport = auditReportRepository.findById(id).orElseThrow(() -> new RuntimeException("Audit Report not found"));
        auditReport.setSummary(auditReportDetails.getSummary());
        auditReport.setStatus(auditReportDetails.getStatus());
        auditReport.setDate(auditReportDetails.getDate());
        auditReport.setFindings(auditReportDetails.getFindings());
        auditReport.setRecommendations(auditReportDetails.getRecommendations());
        return auditReportRepository.save(auditReport);
    }

    public void deleteAuditReport(String id) {
        auditReportRepository.deleteById(id);
    }
}
