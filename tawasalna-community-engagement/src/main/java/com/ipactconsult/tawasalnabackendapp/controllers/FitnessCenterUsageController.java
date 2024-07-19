package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.models.FitnessCenterUsage;
import com.ipactconsult.tawasalnabackendapp.payload.request.FitnessCenterUsageRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.DailyUsageStats;
import com.ipactconsult.tawasalnabackendapp.payload.response.FitnessCenterUsageResponse;
import com.ipactconsult.tawasalnabackendapp.service.IFitnessCenterUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("fitness")
@CrossOrigin("*")
public class FitnessCenterUsageController {
    private final IFitnessCenterUsageService service;
    @PostMapping
    public ResponseEntity<String> createUsage(@Valid @RequestBody FitnessCenterUsageRequest usageRequest) {
        return ResponseEntity.ok(service.save(usageRequest));
    }

    @GetMapping
    public ResponseEntity<List<FitnessCenterUsageResponse>> getAllUsages() {
        return ResponseEntity.ok(service.getAllUsages());
    }

    @GetMapping("/equipment-usage-stats")
    public List<DailyUsageStats> getAllEquipmentUsageStats() {
        return service.getAllEquipmentUsageStats();
    }
}

