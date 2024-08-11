package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.ComponentType;
import com.tawasalna.MaintenanceAgent.models.EnergieConsumption;
import com.tawasalna.MaintenanceAgent.models.LocationTotal;
import com.tawasalna.MaintenanceAgent.repos.EnergieConsumptionRepository;
import com.tawasalna.MaintenanceAgent.services.EnergieConsumptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/energie-consumptions")
@RequiredArgsConstructor
@Slf4j

public class EnergieConsumptionController {
    @Autowired
    private EnergieConsumptionService service;

    private final EnergieConsumptionRepository energieConsumptionRepository;

    @GetMapping("/")
    public ResponseEntity<List<EnergieConsumption>> getAllEnergieConsumptions() {
        List<EnergieConsumption> energieConsumptions = energieConsumptionRepository.findAll();
        return ResponseEntity.ok().body(energieConsumptions);
    }

    @PostMapping("/")
    public ResponseEntity<EnergieConsumption> createEnergieConsumption(@RequestBody EnergieConsumption energieConsumption) {
        EnergieConsumption createdEnergieConsumption = energieConsumptionRepository.save(energieConsumption);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnergieConsumption);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergieConsumption> getEnergieConsumptionById(@PathVariable String id) {
        Optional<EnergieConsumption> energieConsumption = energieConsumptionRepository.findById(id);
        return energieConsumption.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<EnergieConsumption> updateEnergieConsumption(@PathVariable String id, @RequestBody EnergieConsumption energieConsumption) {
        if (!energieConsumptionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        energieConsumption.setId(id); // Assuming your entity has a setId method
        EnergieConsumption updatedEnergieConsumption = energieConsumptionRepository.save(energieConsumption);
        return ResponseEntity.ok().body(updatedEnergieConsumption);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteEnergieConsumption() {
            energieConsumptionRepository.deleteAll();
            return ResponseEntity.noContent().build();

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnergieConsumptionById(@PathVariable String id) {
        if (!energieConsumptionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        energieConsumptionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/location/{location}")
    public ResponseEntity<List<EnergieConsumption>> getByLocation(@PathVariable String location) {
        List<EnergieConsumption> consumptions = service.findByLocation(location);
        return ResponseEntity.ok(consumptions);
    }

    @GetMapping("/group-by-location")
    public ResponseEntity<List<Map<String, Object>>> groupByLocation() {
        List<Map<String, Object>> groupedData = service.groupAndSortByLocation();
        return ResponseEntity.ok(groupedData);
    }
    @GetMapping("/total-by-location")
    public List<LocationTotal> getTotalAmountByLocation() {
        return service.getTotalAmountByLocation();
    }
}
