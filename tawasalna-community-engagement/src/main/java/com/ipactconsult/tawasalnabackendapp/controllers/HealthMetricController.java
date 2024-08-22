package com.ipactconsult.tawasalnabackendapp.controllers;


import com.ipactconsult.tawasalnabackendapp.models.HealthMetric;
import com.ipactconsult.tawasalnabackendapp.payload.request.HealthMetricRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.service.IHealthMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("health-metrics")
@CrossOrigin("*")

public class HealthMetricController {
    private final IHealthMetricService healthMetricService;
    @PostMapping
    public ResponseEntity<String> addMetric(@Valid @RequestBody HealthMetricRequest metricRequest) {
        return ResponseEntity.ok(healthMetricService.save(metricRequest));

    }

    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<HealthMetric>> getMetricsByParticipant(@PathVariable String participantId) {
        return  null ;
    }

    @GetMapping
    public ResponseEntity<List<HealthMetric>> getAllMetrics() {
        List<HealthMetric> metrics = healthMetricService.getAllMetrics();
        return ResponseEntity.ok(metrics);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteHealth(@PathVariable String id) {
        healthMetricService.deleteHealth(id);
        return ResponseEntity.noContent().build();
    }
}
