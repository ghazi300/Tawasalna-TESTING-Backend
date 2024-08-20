package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.services.PestControlReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "http://localhost:4200")

public class PestControlReportController {

    @Autowired
    private PestControlReportService service;

    @PostMapping
    public ResponseEntity<PestControlReport> createReport(@RequestBody PestControlReport report) {
        PestControlReport createdReport = service.createReport(report);
        return ResponseEntity.ok(createdReport);
    }

    @GetMapping
    public ResponseEntity<List<PestControlReport>> getAllReports() {
        List<PestControlReport> reports = service.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PestControlReport> getReportById(@PathVariable String id) {
        Optional<PestControlReport> report = service.getReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PestControlReport> updateReport(@PathVariable String id, @RequestBody PestControlReport updatedReport) {
        PestControlReport updated = service.updateReport(id, updatedReport);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build(); // Retourner 404 si le rapport n'est pas trouvé
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable String id) {
        service.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
