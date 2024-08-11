package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.EnergieConsumptionSummary;
import com.tawasalna.MaintenanceAgent.repos.EnergieConsumptionSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/energie-consumption-summaries")
@RequiredArgsConstructor
@Slf4j
public class EnergieConsumptionSummaryController {

    private final EnergieConsumptionSummaryRepository energieConsumptionSummaryRepository;

    @GetMapping("/")
    public ResponseEntity<List<EnergieConsumptionSummary>> getAllEnergieConsumptionSummaries() {
        List<EnergieConsumptionSummary> summaries = energieConsumptionSummaryRepository.findAll();
        return ResponseEntity.ok().body(summaries);
    }
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllEnergieConsumptionSummaries() {
        energieConsumptionSummaryRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
