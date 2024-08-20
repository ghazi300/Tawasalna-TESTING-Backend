package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import com.example.tawasalnaoperations.services.MaintenanceScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
@CrossOrigin(origins = "http://localhost:4200")

public class MaintenanceScheduleController {

    @Autowired
    private MaintenanceScheduleService service;

    @PostMapping
    public ResponseEntity<MaintenanceSchedule> createSchedule(@RequestBody MaintenanceSchedule schedule) {
        MaintenanceSchedule createdSchedule = service.createSchedule(schedule);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MaintenanceSchedule>> getAllMaintenances() {
        List<MaintenanceSchedule> maintenances = service.getAllMaintenances();
        return ResponseEntity.ok(maintenances);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceSchedule> updateSchedule(
            @PathVariable String id,
            @RequestBody MaintenanceSchedule updatedSchedule) {

        Optional<MaintenanceSchedule> updated = service.updateSchedule(id, updatedSchedule);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable String id) {
        service.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceSchedule> getScheduleById(@PathVariable String id) {
        Optional<MaintenanceSchedule> schedule = service.getScheduleById(id);

        if (schedule.isPresent()) {
            return ResponseEntity.ok(schedule.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}