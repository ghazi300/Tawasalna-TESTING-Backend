package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.WaterConsumption;
import com.tawasalna.MaintenanceAgent.repos.WaterConsumptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/water-consumptions")
@RequiredArgsConstructor
@Slf4j
public class WaterConsumptionController {

    @Autowired
    private final WaterConsumptionRepository waterConsumptionRepository;

    @GetMapping("/")
    public ResponseEntity<List<WaterConsumption>> getAllWaterConsumptions() {
        List<WaterConsumption> waterConsumptions = waterConsumptionRepository.findAll();
        return ResponseEntity.ok().body(waterConsumptions);
    }

    @PostMapping("/")
    public ResponseEntity<WaterConsumption> createWaterConsumption(@RequestBody WaterConsumption waterConsumption) {
        WaterConsumption createdWaterConsumption = waterConsumptionRepository.save(waterConsumption);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWaterConsumption);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterConsumption> getWaterConsumptionById(@PathVariable String id) {
        Optional<WaterConsumption> waterConsumption = waterConsumptionRepository.findById(id);
        return waterConsumption.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaterConsumption> updateWaterConsumption(@PathVariable String id, @RequestBody WaterConsumption waterConsumption) {
        if (!waterConsumptionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        waterConsumption.setId(id);
        WaterConsumption updatedWaterConsumption = waterConsumptionRepository.save(waterConsumption);
        return ResponseEntity.ok().body(updatedWaterConsumption);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllWaterConsumptions() {
        waterConsumptionRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterConsumptionById(@PathVariable String id) {
        if (!waterConsumptionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        waterConsumptionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
