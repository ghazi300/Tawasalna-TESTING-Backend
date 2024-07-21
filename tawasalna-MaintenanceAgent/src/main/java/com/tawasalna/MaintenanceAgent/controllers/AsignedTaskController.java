package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.AsignedTask;
import com.tawasalna.MaintenanceAgent.repos.AsignedTaskRepository;
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
}
