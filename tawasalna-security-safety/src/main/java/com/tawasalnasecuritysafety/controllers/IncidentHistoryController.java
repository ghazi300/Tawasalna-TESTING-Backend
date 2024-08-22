package com.tawasalnasecuritysafety.controllers;

import com.tawasalnasecuritysafety.models.IncidentHistory;
import com.tawasalnasecuritysafety.services.IncidentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/incidenthistories")
@CrossOrigin("*")
public class IncidentHistoryController {
    @Autowired
    private IncidentHistoryService incidentHistoryService;

    @GetMapping
    public List<IncidentHistory> getAllIncidentHistories() {
        return incidentHistoryService.getAllIncidentHistories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentHistory> getIncidentHistoryById(@PathVariable String id) {
        IncidentHistory incidentHistory = incidentHistoryService.getIncidentHistoryById(id).orElseThrow(() -> new RuntimeException("Incident History not found"));
        return ResponseEntity.ok(incidentHistory);
    }

    @PostMapping
    public IncidentHistory createIncidentHistory(@RequestBody IncidentHistory incidentHistory) {
        return incidentHistoryService.createIncidentHistory(incidentHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentHistory> updateIncidentHistory(@PathVariable String id, @RequestBody IncidentHistory incidentHistoryDetails) {
        IncidentHistory updatedIncidentHistory = incidentHistoryService.updateIncidentHistory(id, incidentHistoryDetails);
        return ResponseEntity.ok(updatedIncidentHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncidentHistory(@PathVariable String id) {
        incidentHistoryService.deleteIncidentHistory(id);
        return ResponseEntity.noContent().build();
    }
}
