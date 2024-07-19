package com.tawasalnasecuritysafety.controllers;

import com.tawasalnasecuritysafety.models.Incident;
import com.tawasalnasecuritysafety.services.IncidentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incidents")
@AllArgsConstructor
@CrossOrigin("*")
    public class IncidentController {
        @Autowired
        private IncidentService incidentService;

        @PostMapping
        public Incident createIncident(@RequestBody Incident incident) {
            return incidentService.createIncident(incident);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Incident> getIncidentById(@PathVariable String id) {
            Optional<Incident> incident = incidentService.getIncidentById(id);
            return incident.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping
        public List<Incident> getAllIncidents() {
            return incidentService.getAllIncidents();
        }

        @PutMapping("/{id}")
        public ResponseEntity<Incident> updateIncident(@PathVariable String id, @RequestBody Incident incident) {
            Incident updatedIncident = incidentService.updateIncident(id, incident);
            return updatedIncident != null ? ResponseEntity.ok(updatedIncident) : ResponseEntity.notFound().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteIncident(@PathVariable String id) {
            incidentService.deleteIncident(id);
            return ResponseEntity.noContent().build();
        }


}
