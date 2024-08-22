package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.WaterConsumptionSummary;  // Updated import
import com.tawasalna.MaintenanceAgent.repos.WaterConsumptionSummaryRepository;  // Updated import
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/water-consumption-summaries")  // Updated endpoint
@RequiredArgsConstructor
@Slf4j
public class WaterConsumptionSummaryController {

    private final WaterConsumptionSummaryRepository waterConsumptionSummaryRepository;  // Updated field

    @GetMapping("/")
    public ResponseEntity<List<WaterConsumptionSummary>> getAllWaterConsumptionSummaries() {  // Updated method name
        List<WaterConsumptionSummary> summaries = waterConsumptionSummaryRepository.findAll();
        return ResponseEntity.ok().body(summaries);
    }

    @PostMapping("/")
    public ResponseEntity<WaterConsumptionSummary> createWaterConsumptionSummary(@RequestBody WaterConsumptionSummary summary) {  // Updated method name
        WaterConsumptionSummary createdSummary = waterConsumptionSummaryRepository.save(summary);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSummary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterConsumptionSummary> getWaterConsumptionSummaryById(@PathVariable String id) {  // Updated method name
        Optional<WaterConsumptionSummary> summary = waterConsumptionSummaryRepository.findById(id);
        return summary.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaterConsumptionSummary> updateWaterConsumptionSummary(@PathVariable String id, @RequestBody WaterConsumptionSummary summary) {  // Updated method name
        if (!waterConsumptionSummaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        summary.setId(id);
        WaterConsumptionSummary updatedSummary = waterConsumptionSummaryRepository.save(summary);
        return ResponseEntity.ok().body(updatedSummary);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllWaterConsumptionSummaries() {  // Updated method name
        waterConsumptionSummaryRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterConsumptionSummaryById(@PathVariable String id) {  // Updated method name
        if (!waterConsumptionSummaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        waterConsumptionSummaryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
