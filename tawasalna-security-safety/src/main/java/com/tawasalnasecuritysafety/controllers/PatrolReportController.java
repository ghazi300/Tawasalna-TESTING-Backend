package com.tawasalnasecuritysafety.controllers;


import com.tawasalnasecuritysafety.models.PatrolReport;
import com.tawasalnasecuritysafety.services.PatrolReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patrol-reports")
@CrossOrigin("*")

public class PatrolReportController {
    @Autowired
    private PatrolReportService patrolReportService;

    @PostMapping
    public PatrolReport createPatrolReport(@RequestBody PatrolReport report) {
        return patrolReportService.createPatrolReport(report);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatrolReport> getPatrolReportById(@PathVariable String id) {
        Optional<PatrolReport> report = patrolReportService.getPatrolReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PatrolReport> getAllPatrolReports() {
        return patrolReportService.getAllPatrolReports();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatrolReport> updatePatrolReport(@PathVariable String id, @RequestBody PatrolReport report) {
        PatrolReport updatedReport = patrolReportService.updatePatrolReport(id, report);
        return updatedReport != null ? ResponseEntity.ok(updatedReport) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatrolReport(@PathVariable String id) {
        patrolReportService.deletePatrolReport(id);
        return ResponseEntity.noContent().build();
    }
}
