package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.MaterialType;
import com.example.tawasalnaoperations.entities.RecyclingMetric;
import com.example.tawasalnaoperations.services.RecyclingMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/recycling-metrics")
@CrossOrigin(origins = "http://localhost:4200")

public class RecyclingMetricController {

    @Autowired
    private RecyclingMetricService recyclingMetricService;

    // Create
    @PostMapping
    public RecyclingMetric createRecyclingMetric(@RequestBody RecyclingMetric metric) {
        return recyclingMetricService.createRecyclingMetric(metric);
    }

    // Read all
    @GetMapping
    public List<RecyclingMetric> getAllMetrics() {
        return recyclingMetricService.getAllMetrics();
    }

    @GetMapping("/statistics")
    public Map<MaterialType, Long> getRecyclingStatistics() {
        return recyclingMetricService.getRecyclingStatistics();
    }


    // Read by ID
    @GetMapping("/{metricId}")
    public ResponseEntity<RecyclingMetric> getRecyclingMetricById(@PathVariable String metricId) {
        Optional<RecyclingMetric> metric = recyclingMetricService.getRecyclingMetricById(metricId);
        return metric.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{metricId}")
    public ResponseEntity<RecyclingMetric> updateRecyclingMetric(
            @PathVariable String metricId,
            @RequestBody RecyclingMetric updatedMetric) {
        try {
            RecyclingMetric metric = recyclingMetricService.updateRecyclingMetric(metricId, updatedMetric);
            return ResponseEntity.ok(metric);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{metricId}")
    public ResponseEntity<Void> deleteRecyclingMetric(@PathVariable String metricId) {
        recyclingMetricService.deleteRecyclingMetric(metricId);
        return ResponseEntity.noContent().build();
    }
}
