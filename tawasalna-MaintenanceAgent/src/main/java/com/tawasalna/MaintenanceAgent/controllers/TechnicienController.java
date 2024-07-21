package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.Technicien;
import com.tawasalna.MaintenanceAgent.repos.TachnicienRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/techniciens")
@RequiredArgsConstructor
@Slf4j
public class TechnicienController {

    private final TachnicienRepository technicienRepository;

    @GetMapping("/")
    public ResponseEntity<List<Technicien>> getAllTechniciens() {
        List<Technicien> techniciens = technicienRepository.findAll();
        return ResponseEntity.ok().body(techniciens);
    }

    @PostMapping("/")
    public ResponseEntity<Technicien> createTechnicien(@RequestBody Technicien technicien) {
        Technicien createdTechnicien = technicienRepository.save(technicien);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTechnicien);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technicien> getTechnicienById(@PathVariable String id) {
        Optional<Technicien> technicien = technicienRepository.findById(id);
        return technicien.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technicien> updateTechnicien(@PathVariable String id, @RequestBody Technicien technicienDetails) {
        Optional<Technicien> existingTechnicien = technicienRepository.findById(id);
        if (existingTechnicien.isPresent()) {
            Technicien updatedTechnicien = existingTechnicien.get();
            updatedTechnicien.setRole(technicienDetails.getRole());
            updatedTechnicien.setYearsOfExperience(technicienDetails.getYearsOfExperience());
            updatedTechnicien.setContactInfo(technicienDetails.getContactInfo());
            updatedTechnicien.setName(technicienDetails.getName());

            updatedTechnicien.setCertification(technicienDetails.getCertification());
            updatedTechnicien.setStatus(technicienDetails.getStatus());
            updatedTechnicien.setAssignedTaskId(technicienDetails.getAssignedTaskId()); // Mise à jour de la propriété

            Technicien savedTechnicien = technicienRepository.save(updatedTechnicien);
            return ResponseEntity.ok(savedTechnicien);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnicien(@PathVariable String id) {
        Optional<Technicien> technicien = technicienRepository.findById(id);
        if (technicien.isPresent()) {
            technicienRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
