package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import com.example.tawasalnaoperations.services.MaintenanceScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class MaintenanceScheduleController {

    @Autowired
    private MaintenanceScheduleService service;

    @PostMapping
    public ResponseEntity<MaintenanceSchedule> createSchedule(@RequestBody MaintenanceSchedule schedule) {
        MaintenanceSchedule createdSchedule = service.createSchedule(schedule);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }
}