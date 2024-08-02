package com.tawasalnasecuritysafety.controllers;

import com.tawasalnasecuritysafety.models.AuditReport;
import com.tawasalnasecuritysafety.services.AuditReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auditreports")
@CrossOrigin("*")

public class AuditReportController {
    @Autowired
    private AuditReportService auditReportService;

    @GetMapping
    public List<AuditReport> getAllAuditReports() {
        return auditReportService.getAllAuditReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditReport> getAuditReportById(@PathVariable String id) {
        AuditReport auditReport = auditReportService.getAuditReportById(id).orElseThrow(() -> new RuntimeException("Audit Report not found"));
        return ResponseEntity.ok(auditReport);
    }

    @PostMapping
    public AuditReport createAuditReport(@RequestBody AuditReport auditReport) {
        return auditReportService.createAuditReport(auditReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditReport> updateAuditReport(@PathVariable String id, @RequestBody AuditReport auditReportDetails) {
        AuditReport updatedAuditReport = auditReportService.updateAuditReport(id, auditReportDetails);
        return ResponseEntity.ok(updatedAuditReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditReport(@PathVariable String id) {
        auditReportService.deleteAuditReport(id);
        return ResponseEntity.noContent().build();
    }
}
