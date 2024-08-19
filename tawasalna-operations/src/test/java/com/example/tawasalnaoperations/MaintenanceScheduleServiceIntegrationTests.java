package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import com.example.tawasalnaoperations.repositories.MaintenanceScheduleRepository;
import com.example.tawasalnaoperations.services.MaintenanceScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class MaintenanceScheduleServiceIntegrationTests {

    @InjectMocks
    private MaintenanceScheduleService maintenanceScheduleService;

    @Mock
    private MaintenanceScheduleRepository maintenanceScheduleRepository;

    @BeforeEach
    public void setUp() {
        maintenanceScheduleRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    public void testCreateSchedule() {
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setScheduleId("1");
        schedule.setGardenId("Garden1");
        schedule.setDateDebut(new Date());
        schedule.setDateFin(new Date());
        schedule.setTasks(Arrays.asList("Task1", "Task2"));

        when(maintenanceScheduleRepository.save(schedule)).thenReturn(schedule);

        MaintenanceSchedule created = maintenanceScheduleService.createSchedule(schedule);

        assertNotNull(created);
        assertEquals("1", created.getScheduleId());
        assertEquals("Garden1", created.getGardenId());
        verify(maintenanceScheduleRepository, times(1)).save(schedule);
    }

    @Test
    public void testGetScheduleById() {
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setScheduleId("1");
        schedule.setGardenId("Garden1");
        schedule.setDateDebut(new Date());
        schedule.setDateFin(new Date());
        schedule.setTasks(Arrays.asList("Task1", "Task2"));

        when(maintenanceScheduleRepository.findById("1")).thenReturn(Optional.of(schedule));

        Optional<MaintenanceSchedule> found = maintenanceScheduleService.getScheduleById("1");

        assertTrue(found.isPresent());
        assertEquals("Garden1", found.get().getGardenId());
        verify(maintenanceScheduleRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateSchedule() {
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setScheduleId("1");
        schedule.setGardenId("Garden1");
        schedule.setDateDebut(new Date());
        schedule.setDateFin(new Date());
        schedule.setTasks(Arrays.asList("Task1", "Task2"));

        when(maintenanceScheduleRepository.findById("1")).thenReturn(Optional.of(schedule));
        when(maintenanceScheduleRepository.save(schedule)).thenReturn(schedule);

        MaintenanceSchedule updatedSchedule = new MaintenanceSchedule();
        updatedSchedule.setGardenId("Garden2");
        updatedSchedule.setDateDebut(new Date());
        updatedSchedule.setDateFin(new Date());
        updatedSchedule.setTasks(Arrays.asList("Task3", "Task4"));

        Optional<MaintenanceSchedule> updated = maintenanceScheduleService.updateSchedule("1", updatedSchedule);

        assertTrue(updated.isPresent());
        assertEquals("Garden2", updated.get().getGardenId());
        verify(maintenanceScheduleRepository, times(1)).findById("1");
        verify(maintenanceScheduleRepository, times(1)).save(schedule);
    }

    @Test
    public void testGetAllMaintenances() {
        MaintenanceSchedule schedule1 = new MaintenanceSchedule();
        schedule1.setScheduleId("1");
        schedule1.setGardenId("Garden1");
        schedule1.setDateDebut(new Date());
        schedule1.setDateFin(new Date());
        schedule1.setTasks(Arrays.asList("Task1", "Task2"));

        MaintenanceSchedule schedule2 = new MaintenanceSchedule();
        schedule2.setScheduleId("2");
        schedule2.setGardenId("Garden2");
        schedule2.setDateDebut(new Date());
        schedule2.setDateFin(new Date());
        schedule2.setTasks(Arrays.asList("Task3", "Task4"));

        List<MaintenanceSchedule> schedules = Arrays.asList(schedule1, schedule2);

        when(maintenanceScheduleRepository.findAll()).thenReturn(schedules);

        List<MaintenanceSchedule> found = maintenanceScheduleService.getAllMaintenances();

        assertEquals(2, found.size());
        assertEquals("Garden1", found.get(0).getGardenId());
        assertEquals("Garden2", found.get(1).getGardenId());
        verify(maintenanceScheduleRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteMaintenance() {
        String scheduleId = "1";

        doNothing().when(maintenanceScheduleRepository).deleteById(scheduleId);

        maintenanceScheduleService.deleteMaintenance(scheduleId);

        verify(maintenanceScheduleRepository, times(1)).deleteById(scheduleId);
    }
}
