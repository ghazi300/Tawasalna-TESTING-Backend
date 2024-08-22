package com.example.managementcoordination.controllers;

import com.example.managementcoordination.Services.LegalCaseService;
import com.example.managementcoordination.entities.LegalCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/legal-cases")
@CrossOrigin(origins = "http://localhost:4200")

public class LegalCaseController {
    @Autowired
    private LegalCaseService legalCaseService;
    @GetMapping
    public ResponseEntity<List<LegalCase>> getAllCases() {
        return ResponseEntity.ok(legalCaseService.getAllCases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LegalCase> getCaseById(@PathVariable String id) {
        Optional<LegalCase> legalCase = legalCaseService.getCaseById(id);
        return legalCase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LegalCase> createCase(@RequestBody LegalCase legalCase) {
        LegalCase savedCase = legalCaseService.saveCase(legalCase);
        return ResponseEntity.ok(savedCase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LegalCase> updateCase(@PathVariable String id, @RequestBody LegalCase updatedCase) {
        Optional<LegalCase> existingCase = legalCaseService.getCaseById(id);
        if (existingCase.isPresent()) {
            updatedCase.setId(id); // Ensure the ID is retained
            LegalCase savedCase = legalCaseService.saveCase(updatedCase);
            return ResponseEntity.ok(savedCase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCase(@PathVariable String id) {
        legalCaseService.deleteCase(id);
        return ResponseEntity.noContent().build();
    }
}
