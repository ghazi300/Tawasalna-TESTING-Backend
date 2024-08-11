package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.AsignedTask;
import com.tawasalna.MaintenanceAgent.models.MaintenanceTask;
import com.tawasalna.MaintenanceAgent.models.TaskStatus;
import com.tawasalna.MaintenanceAgent.repos.AsignedTaskRepository;
import com.tawasalna.MaintenanceAgent.repos.MaintenanceTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assigned-tasks")
@RequiredArgsConstructor
@Slf4j
public class AsignedTaskController {

    private final AsignedTaskRepository asignedTaskRepository;
    private final MaintenanceTaskRepository maintenanceTaskRepository;

    @GetMapping("/")
    public ResponseEntity<List<AsignedTask>> getAllAssignedTasks() {
        List<AsignedTask> assignedTasks = asignedTaskRepository.findAll();
        return ResponseEntity.ok().body(assignedTasks);
    }

    @PostMapping("/")
    public ResponseEntity<AsignedTask> createAssignedTask(@RequestBody AsignedTask assignedTask) {
        AsignedTask createdTask = asignedTaskRepository.save(assignedTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignedTask> getAssignedTaskById(@PathVariable String id) {
        Optional<AsignedTask> assignedTask = asignedTaskRepository.findById(id);
        return assignedTask.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignedTask> updateAssignedTask(@PathVariable String id, @RequestBody AsignedTask assignedTask) {
        if (!asignedTaskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        assignedTask.setId(id);

        AsignedTask updatedTask = asignedTaskRepository.save(assignedTask);

        // Vérifiez le statut de la tâche et mettez à jour MaintenanceTask si nécessaire
        if (assignedTask.getTaskStatus() == TaskStatus.COMPLETED) {
            // Met à jour le statut de MaintenanceTask directement
            updateMaintenanceTaskStatus(id, TaskStatus.COMPLETED);
        }

        return ResponseEntity.ok().body(updatedTask);
    }

    private void updateMaintenanceTaskStatus(String assignedTaskId, TaskStatus newStatus) {
        MaintenanceTask task = maintenanceTaskRepository.findByAssignedTaskId(assignedTaskId);
        if (task != null) {
            task.setTaskStatus(newStatus);
            maintenanceTaskRepository.save(task);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignedTask(@PathVariable String id) {
        Optional<AsignedTask> assignedTask = asignedTaskRepository.findById(id);
        if (assignedTask.isPresent()) {
            asignedTaskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAssignedTask() {
        asignedTaskRepository.deleteAll();
        return ResponseEntity.noContent().build();

    }
}
