package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.EnergySourceSummary;
import com.tawasalna.MaintenanceAgent.repos.EnergieSourceSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/energy-source-summaries")
@RequiredArgsConstructor
@Slf4j
public class EnergySourceSummaryController {

    private final EnergieSourceSummaryRepository energySourceSummaryRepository;

    @GetMapping("/")
    public ResponseEntity<List<EnergySourceSummary>> getAllEnergySourceSummaries() {
        List<EnergySourceSummary> summaries = energySourceSummaryRepository.findAll();
        return ResponseEntity.ok().body(summaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergySourceSummary> getEnergySourceSummaryById(@PathVariable String id) {
        Optional<EnergySourceSummary> summary = energySourceSummaryRepository.findById(id);
        return summary.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<EnergySourceSummary> createEnergySourceSummary(@RequestBody EnergySourceSummary energySourceSummary) {
        EnergySourceSummary createdSummary = energySourceSummaryRepository.save(energySourceSummary);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSummary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnergySourceSummary> updateEnergySourceSummary(@PathVariable String id, @RequestBody EnergySourceSummary energySourceSummary) {
        if (!energySourceSummaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        energySourceSummary.setId(id); // Assuming your entity has a setId method
        EnergySourceSummary updatedSummary = energySourceSummaryRepository.save(energySourceSummary);
        return ResponseEntity.ok().body(updatedSummary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnergySourceSummary(@PathVariable String id) {
        if (!energySourceSummaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        energySourceSummaryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllEnergieConsumptionSummaries() {
        energySourceSummaryRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
