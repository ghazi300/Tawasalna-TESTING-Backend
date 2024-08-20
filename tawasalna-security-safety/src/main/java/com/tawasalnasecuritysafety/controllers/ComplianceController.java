package com.tawasalnasecuritysafety.controllers;

import com.tawasalnasecuritysafety.models.Compliance;
import com.tawasalnasecuritysafety.services.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compliances")
@CrossOrigin("*")

public class ComplianceController {
    @Autowired
    private ComplianceService complianceService;

    @GetMapping
    public List<Compliance> getAllCompliances() {
        return complianceService.getAllCompliances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compliance> getComplianceById(@PathVariable String id) {
        Compliance compliance = complianceService.getComplianceById(id).orElseThrow(() -> new RuntimeException("Compliance not found"));
        return ResponseEntity.ok(compliance);
    }

    @PostMapping
    public Compliance createCompliance(@RequestBody Compliance compliance) {
        return complianceService.createCompliance(compliance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compliance> updateCompliance(@PathVariable String id, @RequestBody Compliance complianceDetails) {
        Compliance updatedCompliance = complianceService.updateCompliance(id, complianceDetails);
        return ResponseEntity.ok(updatedCompliance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompliance(@PathVariable String id) {
        complianceService.deleteCompliance(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/pending-percentage")
    public double getPendingCompliancePercentage() {
        return complianceService.getPendingCompliancePercentage();
    }

    @GetMapping("/completed-percentage")
    public double getCompletedCompliancePercentage() {
        return complianceService.getCompletedCompliancePercentage();
    }
}
