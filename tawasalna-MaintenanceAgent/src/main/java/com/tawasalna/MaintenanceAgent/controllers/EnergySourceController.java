package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.EnergySource;
import com.tawasalna.MaintenanceAgent.repos.EnergySourceRepository;
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
@RequestMapping("/api/energy-sources")
@RequiredArgsConstructor
@Slf4j
public class EnergySourceController {

    @Autowired
    private final EnergySourceRepository energySourceRepository;

    @GetMapping("/")
    public ResponseEntity<List<EnergySource>> getAllEnergySources() {
        List<EnergySource> energySources = energySourceRepository.findAll();
        return ResponseEntity.ok().body(energySources);
    }

    @PostMapping("/")
    public ResponseEntity<EnergySource> createEnergySource(@RequestBody EnergySource energySource) {
        EnergySource createdEnergySource = energySourceRepository.save(energySource);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnergySource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergySource> getEnergySourceById(@PathVariable String id) {
        Optional<EnergySource> energySource = energySourceRepository.findById(id);
        return energySource.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnergySource> updateEnergySource(@PathVariable String id, @RequestBody EnergySource energySource) {
        if (!energySourceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        energySource.setId(id);
        EnergySource updatedEnergySource = energySourceRepository.save(energySource);
        return ResponseEntity.ok().body(updatedEnergySource);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllEnergySources() {
        energySourceRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnergySourceById(@PathVariable String id) {
        if (!energySourceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        energySourceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
