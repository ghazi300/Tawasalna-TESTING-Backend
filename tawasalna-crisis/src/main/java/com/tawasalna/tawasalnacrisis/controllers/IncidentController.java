package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.payload.IncidentDto;
import com.tawasalna.tawasalnacrisis.payload.IncidentPayload;
import com.tawasalna.tawasalnacrisis.payload.RecentIncidentDto;
import com.tawasalna.tawasalnacrisis.payload.ResourceAllocationRequest;
import com.tawasalna.tawasalnacrisis.services.IncidentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    private IncidentService incidentService;

    @PostMapping
    public ResponseEntity<Incident> createIncident(
          @Valid @RequestBody IncidentPayload incidentPayload
          ) {

        Optional<Incident> createdIncident = incidentService.createIncident(incidentPayload);

        return createdIncident.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @GetMapping
    public ResponseEntity<List<IncidentDto>> getIncidents() {
        List<IncidentDto> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }
    @GetMapping("/{id}")
    public ResponseEntity<IncidentDto> getIncidentById(@PathVariable String id) {
        Optional<IncidentDto> incident = incidentService.getIncidentById(id);
        return incident.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/recent")
    public ResponseEntity<List<RecentIncidentDto>> getRecentIncidents() {
        List<RecentIncidentDto> recentIncidents = incidentService.getRecentIncidents();
        return ResponseEntity.ok(recentIncidents);
    }
    @PutMapping("/allocate")
    public ResponseEntity<Incident> allocateResources(@RequestBody ResourceAllocationRequest request) {
        Incident updatedIncident = incidentService.allocateResources(request.getIncidentId(), request.getResourceIds());
        if (updatedIncident != null) {
            return ResponseEntity.ok(updatedIncident);
        }
        return ResponseEntity.notFound().build();
    }

}