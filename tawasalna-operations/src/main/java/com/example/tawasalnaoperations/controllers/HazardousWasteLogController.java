package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.HazardousWasteLog;
import com.example.tawasalnaoperations.entities.MaterialType;
import com.example.tawasalnaoperations.services.HazardousWasteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/waste-logs")
@CrossOrigin(origins = "http://localhost:4200")

public class HazardousWasteLogController {

    @Autowired
    private HazardousWasteLogService service;

    @GetMapping
    public List<HazardousWasteLog> getAllLogs() {
        return service.getAllLogs();
    }

    @GetMapping("/{id}")
    public Optional<HazardousWasteLog> getLogById(@PathVariable String id) {
        return service.getLogById(id);
    }

    @PostMapping
    public HazardousWasteLog createLog(@RequestBody HazardousWasteLog log) {
        return service.createLog(log);
    }

    @PutMapping("/{id}")
    public HazardousWasteLog updateLog(@PathVariable String id, @RequestBody HazardousWasteLog updatedLog) {
        return service.updateLog(id, updatedLog);
    }

    @DeleteMapping("/{id}")
    public void deleteLog(@PathVariable String id) {
        service.deleteLog(id);
    }

    @GetMapping("/statistics")
    public Map<String, Long> getRecyclingStatistics() {
        return service.getWasteStatistics();
    }
    }
