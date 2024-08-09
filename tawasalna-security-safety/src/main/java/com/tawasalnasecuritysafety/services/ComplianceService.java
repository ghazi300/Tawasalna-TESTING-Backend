package com.tawasalnasecuritysafety.services;

import com.tawasalnasecuritysafety.models.Compliance;
import com.tawasalnasecuritysafety.repos.ComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceService {
    @Autowired
    private ComplianceRepository complianceRepository;

    public List<Compliance> getAllCompliances() {
        return complianceRepository.findAll();
    }

    public Optional<Compliance> getComplianceById(String id) {
        return complianceRepository.findById(id);
    }

    public Compliance createCompliance(Compliance compliance) {
        return complianceRepository.save(compliance);
    }

    public Compliance updateCompliance(String id, Compliance complianceDetails) {
        Compliance compliance = (Compliance) complianceRepository.findById(id).orElseThrow(() -> new RuntimeException("Compliance not found"));
        compliance.setTitle(complianceDetails.getTitle());
        compliance.setStatus(complianceDetails.getStatus());
        compliance.setDeadline(String.valueOf(complianceDetails.getDeadline()));
        return complianceRepository.save(compliance);
    }

    public void deleteCompliance(String id) {
        complianceRepository.deleteById(id);
    }

    public double getPendingCompliancePercentage() {
        long totalCount = complianceRepository.count();
        long pendingCount = complianceRepository.countByStatus("Non-compliant");
        return totalCount == 0 ? 0 : ((double) pendingCount / totalCount) * 100;
    }

    public double getCompletedCompliancePercentage() {
        long totalCount = complianceRepository.count();
        long completedCount = complianceRepository.countByStatus("Compliant");
        return totalCount == 0 ? 0 : ((double) completedCount / totalCount) * 100;
    }
}
