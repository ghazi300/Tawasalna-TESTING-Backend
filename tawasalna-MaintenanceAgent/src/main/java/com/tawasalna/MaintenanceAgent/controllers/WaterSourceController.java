package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.WaterSource;
import com.tawasalna.MaintenanceAgent.repos.WaterSourceRepository;
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
@RequestMapping("/api/water-sources")
@RequiredArgsConstructor
@Slf4j
public class WaterSourceController {

    @Autowired
    private final WaterSourceRepository waterSourceRepository;

    @GetMapping("/")
    public ResponseEntity<List<WaterSource>> getAllWaterSources() {
        List<WaterSource> waterSources = waterSourceRepository.findAll();
        return ResponseEntity.ok().body(waterSources);
    }

    @PostMapping("/")
    public ResponseEntity<WaterSource> createWaterSource(@RequestBody WaterSource waterSource) {
        WaterSource createdWaterSource = waterSourceRepository.save(waterSource);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWaterSource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterSource> getWaterSourceById(@PathVariable String id) {
        Optional<WaterSource> waterSource = waterSourceRepository.findById(id);
        return waterSource.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaterSource> updateWaterSource(@PathVariable String id, @RequestBody WaterSource waterSource) {
        if (!waterSourceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        waterSource.setId(id);
        WaterSource updatedWaterSource = waterSourceRepository.save(waterSource);
        return ResponseEntity.ok().body(updatedWaterSource);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllWaterSources() {
        waterSourceRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterSourceById(@PathVariable String id) {
        if (!waterSourceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        waterSourceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
