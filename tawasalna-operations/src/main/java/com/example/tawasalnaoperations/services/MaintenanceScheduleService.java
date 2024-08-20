package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.repositories.MaintenanceScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<MaintenanceSchedule>getAllMaintenances(){return repository.findAll();}
    public Optional<MaintenanceSchedule> updateSchedule(String id, MaintenanceSchedule updatedSchedule) {
        Optional<MaintenanceSchedule> existingSchedule = repository.findById(id);
        if (existingSchedule.isPresent()) {
            MaintenanceSchedule schedule = existingSchedule.get();
            schedule.setGardenId(updatedSchedule.getGardenId());
            schedule.setDateDebut(updatedSchedule.getDateDebut());
            schedule.setDateFin(updatedSchedule.getDateFin());
            schedule.setTasks(updatedSchedule.getTasks());
            return Optional.of(repository.save(schedule));
        } else {
            return Optional.empty();
        }
    }

    public void deleteMaintenance(String id) {
        repository.deleteById(id);
    }

}
