package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.WaterSourceSummary;
import com.tawasalna.MaintenanceAgent.repos.WaterSourceSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/water-source-summaries")  // Endpoint for Water Source Summaries
@RequiredArgsConstructor
@Slf4j
public class WaterSourceSummaryController {

    private final WaterSourceSummaryRepository waterSourceSummaryRepository;

    @GetMapping("/")
    public ResponseEntity<List<WaterSourceSummary>> getAllWaterSourceSummaries() {
        List<WaterSourceSummary> summaries = waterSourceSummaryRepository.findAll();
        return ResponseEntity.ok().body(summaries);
    }

    @PostMapping("/")
    public ResponseEntity<WaterSourceSummary> createWaterSourceSummary(@RequestBody WaterSourceSummary summary) {
        WaterSourceSummary createdSummary = waterSourceSummaryRepository.save(summary);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSummary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterSourceSummary> getWaterSourceSummaryById(@PathVariable String id) {
        Optional<WaterSourceSummary> summary = waterSourceSummaryRepository.findById(id);
        return summary.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaterSourceSummary> updateWaterSourceSummary(@PathVariable String id, @RequestBody WaterSourceSummary summary) {
        if (!waterSourceSummaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        summary.setId(id);
        WaterSourceSummary updatedSummary = waterSourceSummaryRepository.save(summary);
        return ResponseEntity.ok().body(updatedSummary);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllWaterSourceSummaries() {
        waterSourceSummaryRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterSourceSummaryById(@PathVariable String id) {
        if (!waterSourceSummaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        waterSourceSummaryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
