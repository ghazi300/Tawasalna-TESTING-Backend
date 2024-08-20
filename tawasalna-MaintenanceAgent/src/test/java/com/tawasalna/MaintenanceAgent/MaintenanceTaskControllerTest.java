package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.MaintenanceTaskController;
import com.tawasalna.MaintenanceAgent.models.MaintenanceTask;
import com.tawasalna.MaintenanceAgent.models.TaskStatus;
import com.tawasalna.MaintenanceAgent.repos.MaintenanceTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MaintenanceTaskControllerTest {

    @Mock
    private MaintenanceTaskRepository maintenanceTaskRepository;

    @InjectMocks
    private MaintenanceTaskController maintenanceTaskController;

    private MaintenanceTask task1;
    private MaintenanceTask task2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        task1 = new MaintenanceTask();
        task1.setId("1");
        task1.setDescription("Fix the AC");
        task1.setTaskStatus(TaskStatus.PENDING);

        task2 = new MaintenanceTask();
        task2.setId("2");
        task2.setDescription("Repair the elevator");
        task2.setTaskStatus(TaskStatus.IN_PROGRESS);
    }

    @Test
    void getAllMaintenanceTasks() {
        when(maintenanceTaskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        ResponseEntity<List<MaintenanceTask>> response = maintenanceTaskController.getAllMaintenanceTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(maintenanceTaskRepository, times(1)).findAll();
    }

    @Test
    void createMaintenanceTask() {
        when(maintenanceTaskRepository.save(any(MaintenanceTask.class))).thenReturn(task1);

        ResponseEntity<MaintenanceTask> response = maintenanceTaskController.createMaintenanceTask(task1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task1.getDescription(), response.getBody().getDescription());
        verify(maintenanceTaskRepository, times(1)).save(task1);
    }

    @Test
    void getMaintenanceTaskById() {
        when(maintenanceTaskRepository.findById("1")).thenReturn(Optional.of(task1));

        ResponseEntity<MaintenanceTask> response = maintenanceTaskController.getMaintenanceTaskById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task1.getDescription(), response.getBody().getDescription());
        verify(maintenanceTaskRepository, times(1)).findById("1");
    }

    @Test
    void updateMaintenanceTask() {
        when(maintenanceTaskRepository.findById("1")).thenReturn(Optional.of(task1));
        when(maintenanceTaskRepository.save(any(MaintenanceTask.class))).thenReturn(task1);

        task1.setDescription("Fix the AC - Updated");
        ResponseEntity<MaintenanceTask> response = maintenanceTaskController.updateMaintenanceTask("1", task1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fix the AC - Updated", response.getBody().getDescription());
        verify(maintenanceTaskRepository, times(1)).findById("1");
        verify(maintenanceTaskRepository, times(1)).save(task1);
    }

    @Test
    void deleteMaintenanceTask() {
        when(maintenanceTaskRepository.findById("1")).thenReturn(Optional.of(task1));

        ResponseEntity<Void> response = maintenanceTaskController.deleteMaintenanceTask("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(maintenanceTaskRepository, times(1)).findById("1");
        verify(maintenanceTaskRepository, times(1)).deleteById("1");
    }
}
