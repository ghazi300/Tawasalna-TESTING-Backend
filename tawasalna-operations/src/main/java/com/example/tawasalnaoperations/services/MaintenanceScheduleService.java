package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import com.example.tawasalnaoperations.repositories.MaintenanceScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaintenanceScheduleService {

    @Autowired
    private MaintenanceScheduleRepository repository;

    public MaintenanceSchedule createSchedule(MaintenanceSchedule schedule) {
        return repository.save(schedule);
    }

    public Optional<MaintenanceSchedule> getScheduleById(String id) {
        return repository.findById(id);
    }
}
