package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.MaintenanceTask;
import com.tawasalna.MaintenanceAgent.repos.MaintenanceTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maintenance-tasks")
@RequiredArgsConstructor
@Slf4j
public class MaintenanceTaskController {

    private final MaintenanceTaskRepository maintenanceTaskRepository;

    @GetMapping("/")
    public ResponseEntity<List<MaintenanceTask>> getAllMaintenanceTasks() {
        List<MaintenanceTask> tasks = maintenanceTaskRepository.findAll();
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping("/")
    public ResponseEntity<MaintenanceTask> createMaintenanceTask(@RequestBody MaintenanceTask task) {
        MaintenanceTask createdTask = maintenanceTaskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceTask> getMaintenanceTaskById(@PathVariable String id) {
        Optional<MaintenanceTask> task = maintenanceTaskRepository.findById(id);
        return task.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceTask> updateMaintenanceTask(@PathVariable String id, @RequestBody MaintenanceTask taskDetails) {
        Optional<MaintenanceTask> existingTask = maintenanceTaskRepository.findById(id);
        if (existingTask.isPresent()) {
            MaintenanceTask updatedTask = existingTask.get();
            updatedTask.setDescription(taskDetails.getDescription());
            updatedTask.setTaskStatus(taskDetails.getTaskStatus());
            updatedTask.setPriority(taskDetails.getPriority());
            updatedTask.setComments(taskDetails.getComments());
            updatedTask.setAssignedTaskId(taskDetails.getAssignedTaskId()); // Mise à jour de la propriété

            MaintenanceTask savedTask = maintenanceTaskRepository.save(updatedTask);

            return ResponseEntity.ok(savedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenanceTask(@PathVariable String id) {
        Optional<MaintenanceTask> task = maintenanceTaskRepository.findById(id);
        if (task.isPresent()) {
            maintenanceTaskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
