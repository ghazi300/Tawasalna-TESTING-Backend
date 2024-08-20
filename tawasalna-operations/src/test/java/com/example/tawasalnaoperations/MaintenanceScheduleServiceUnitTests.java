package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import com.example.tawasalnaoperations.repositories.MaintenanceScheduleRepository;
import com.example.tawasalnaoperations.services.MaintenanceScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MaintenanceScheduleServiceUnitTests {

    @Mock
    private MaintenanceScheduleRepository repository;

    @InjectMocks
    private MaintenanceScheduleService service;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public MaintenanceScheduleServiceUnitTests() {
        MockitoAnnotations.openMocks(this);
    }

    private Date parseDate(String date) throws ParseException {
        return sdf.parse(date);
    }

    @Test
    public void testCreateSchedule() throws ParseException {
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setGardenId("1");
        schedule.setDateDebut(parseDate("2024-08-01"));
        schedule.setDateFin(parseDate("2024-08-10"));
        schedule.setTasks(List.of("Mowing and trimming"));

        when(repository.save(any(MaintenanceSchedule.class))).thenReturn(schedule);

        MaintenanceSchedule createdSchedule = service.createSchedule(schedule);

        assertNotNull(createdSchedule);
        assertEquals("1", createdSchedule.getGardenId());
        assertEquals(parseDate("2024-08-01"), createdSchedule.getDateDebut());
        verify(repository, times(1)).save(schedule);
    }

    @Test
    public void testGetScheduleById() throws ParseException {
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setGardenId("1");
        schedule.setDateDebut(parseDate("2024-08-01"));
        schedule.setDateFin(parseDate("2024-08-10"));
        schedule.setTasks(List.of("Mowing and trimming"));

        when(repository.findById("1")).thenReturn(Optional.of(schedule));

        Optional<MaintenanceSchedule> foundSchedule = service.getScheduleById("1");

        assertTrue(foundSchedule.isPresent());
        assertEquals("1", foundSchedule.get().getGardenId());
        assertEquals(parseDate("2024-08-01"), foundSchedule.get().getDateDebut());
        verify(repository, times(1)).findById("1");
    }

    @Test
    public void testUpdateSchedule() throws ParseException {
        MaintenanceSchedule existingSchedule = new MaintenanceSchedule();
        existingSchedule.setGardenId("1");
        existingSchedule.setDateDebut(parseDate("2024-08-01"));
        existingSchedule.setDateFin(parseDate("2024-08-10"));
        existingSchedule.setTasks(List.of("Mowing"));

        MaintenanceSchedule updatedSchedule = new MaintenanceSchedule();
        updatedSchedule.setGardenId("1");
        updatedSchedule.setDateDebut(parseDate("2024-08-02"));
        updatedSchedule.setDateFin(parseDate("2024-08-11"));
        updatedSchedule.setTasks(List.of("Mowing and trimming"));

        when(repository.findById("1")).thenReturn(Optional.of(existingSchedule));
        when(repository.save(any(MaintenanceSchedule.class))).thenAnswer(invocation -> {
            // Simule la sauvegarde en retournant l'objet passé
            return invocation.getArgument(0);
        });

        Optional<MaintenanceSchedule> result = service.updateSchedule("1", updatedSchedule);

        // Utilisation d'ArgumentCaptor pour vérifier l'argument passé à save()
        ArgumentCaptor<MaintenanceSchedule> captor = ArgumentCaptor.forClass(MaintenanceSchedule.class);
        verify(repository, times(1)).save(captor.capture());
        MaintenanceSchedule savedSchedule = captor.getValue();

        assertTrue(result.isPresent());
        assertEquals(parseDate("2024-08-02"), result.get().getDateDebut());
        assertEquals(List.of("Mowing and trimming"), result.get().getTasks());
        assertEquals("1", savedSchedule.getGardenId());
        assertEquals(parseDate("2024-08-02"), savedSchedule.getDateDebut());
        assertEquals(parseDate("2024-08-11"), savedSchedule.getDateFin());
        assertEquals(List.of("Mowing and trimming"), savedSchedule.getTasks());
    }

    @Test
    public void testDeleteSchedule() {
        doNothing().when(repository).deleteById("1");

        service.deleteMaintenance("1");

        verify(repository, times(1)).deleteById("1");
    }
}
