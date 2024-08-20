package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.AsignedTaskController;
import com.tawasalna.MaintenanceAgent.models.AsignedTask;
import com.tawasalna.MaintenanceAgent.models.TaskStatus;
import com.tawasalna.MaintenanceAgent.repos.AsignedTaskRepository;
import com.tawasalna.MaintenanceAgent.repos.MaintenanceTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AsignedTaskControllerTest {

    @InjectMocks
    private AsignedTaskController asignedTaskController;

    @Mock
    private AsignedTaskRepository asignedTaskRepository;

    @Mock
    private MaintenanceTaskRepository maintenanceTaskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAssignedTasks() {
        List<AsignedTask> assignedTasks = new ArrayList<>();
        assignedTasks.add(new AsignedTask());
        when(asignedTaskRepository.findAll()).thenReturn(assignedTasks);

        ResponseEntity<List<AsignedTask>> response = asignedTaskController.getAllAssignedTasks();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(assignedTasks, response.getBody());
    }

    @Test
    void testCreateAssignedTask() {
        AsignedTask assignedTask = new AsignedTask();
        when(asignedTaskRepository.save(any(AsignedTask.class))).thenReturn(assignedTask);

        ResponseEntity<AsignedTask> response = asignedTaskController.createAssignedTask(assignedTask);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(assignedTask, response.getBody());
    }

    @Test
    void testGetAssignedTaskById() {
        String id = "1";
        AsignedTask assignedTask = new AsignedTask(id, "maintenanceTaskId", List.of("technicienId1"), TaskStatus.PENDING, new Date(), new Date(), List.of());
        when(asignedTaskRepository.findById(id)).thenReturn(Optional.of(assignedTask));

        ResponseEntity<AsignedTask> response = asignedTaskController.getAssignedTaskById(id);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(assignedTask, response.getBody());
    }

    @Test
    void testGetAssignedTaskByIdNotFound() {
        String id = "1";
        when(asignedTaskRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<AsignedTask> response = asignedTaskController.getAssignedTaskById(id);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateAssignedTask() {
        String id = "1";
        AsignedTask existingTask = new AsignedTask(id, "maintenanceTaskId", List.of("technicienId1"), TaskStatus.PENDING, new Date(), new Date(), List.of());
        AsignedTask updatedTask = new AsignedTask(id, "maintenanceTaskId", List.of("technicienId1"), TaskStatus.COMPLETED, new Date(), new Date(), List.of());

        when(asignedTaskRepository.existsById(id)).thenReturn(true);
        when(asignedTaskRepository.save(any(AsignedTask.class))).thenReturn(updatedTask);

        ResponseEntity<AsignedTask> response = asignedTaskController.updateAssignedTask(id, updatedTask);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedTask, response.getBody());
    }

    @Test
    void testUpdateAssignedTaskNotFound() {
        String id = "1";
        AsignedTask updatedTask = new AsignedTask(id, "maintenanceTaskId", List.of("technicienId1"), TaskStatus.COMPLETED, new Date(), new Date(), List.of());

        when(asignedTaskRepository.existsById(id)).thenReturn(false);

        ResponseEntity<AsignedTask> response = asignedTaskController.updateAssignedTask(id, updatedTask);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteAssignedTask() {
        String id = "1";
        when(asignedTaskRepository.findById(id)).thenReturn(Optional.of(new AsignedTask()));

        ResponseEntity<Void> response = asignedTaskController.deleteAssignedTask(id);
        assertEquals(204, response.getStatusCodeValue());
        verify(asignedTaskRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAssignedTaskNotFound() {
        String id = "1";
        when(asignedTaskRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = asignedTaskController.deleteAssignedTask(id);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteAllAssignedTasks() {
        ResponseEntity<Void> response = asignedTaskController.deleteAssignedTask();
        assertEquals(204, response.getStatusCodeValue());
        verify(asignedTaskRepository, times(1)).deleteAll();
    }
}
