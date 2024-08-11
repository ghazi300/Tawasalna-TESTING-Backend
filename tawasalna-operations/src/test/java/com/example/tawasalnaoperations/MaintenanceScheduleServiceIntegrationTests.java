package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import com.example.tawasalnaoperations.repositories.MaintenanceScheduleRepository;
import com.example.tawasalnaoperations.services.MaintenanceScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class MaintenanceScheduleServiceIntegrationTests {

    @Autowired
    private MaintenanceScheduleService service;

    @Autowired
    private MaintenanceScheduleRepository repository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() {
        repository.deleteAll(); // Nettoyer la base de données avant chaque test
    }

    private Date parseDate(String date) throws ParseException {
        return sdf.parse(date);
    }

    @Test
    public void testCreateAndRetrieveSchedule() throws ParseException {
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setGardenId("1");
        schedule.setDateDebut(parseDate("2024-08-01"));
        schedule.setDateFin(parseDate("2024-08-10"));
        schedule.setTasks(List.of("Mowing and trimming"));

        MaintenanceSchedule createdSchedule = service.createSchedule(schedule);

        assertNotNull(createdSchedule);
        assertEquals("1", createdSchedule.getGardenId());
        assertEquals(parseDate("2024-08-01"), createdSchedule.getDateDebut());

        Optional<MaintenanceSchedule> retrievedSchedule = service.getScheduleById(createdSchedule.getScheduleId());
        assertTrue(retrievedSchedule.isPresent());
        assertEquals(List.of("Mowing and trimming"), retrievedSchedule.get().getTasks());
    }

    @Test
    public void testUpdateSchedule() throws ParseException {
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setGardenId("1");
        schedule.setDateDebut(parseDate("2024-08-01"));
        schedule.setDateFin(parseDate("2024-08-10"));
        schedule.setTasks(List.of("Mowing"));

        MaintenanceSchedule createdSchedule = service.createSchedule(schedule);

        MaintenanceSchedule updatedSchedule = new MaintenanceSchedule();
        updatedSchedule.setGardenId("1");
        updatedSchedule.setDateDebut(parseDate("2024-08-02"));
        updatedSchedule.setDateFin(parseDate("2024-08-11"));
        updatedSchedule.setTasks(List.of("Mowing and trimming"));

        Optional<MaintenanceSchedule> result = service.updateSchedule(createdSchedule.getScheduleId(), updatedSchedule);

        assertTrue(result.isPresent());
        assertEquals(parseDate("2024-08-02"), result.get().getDateDebut());
        assertEquals(List.of("Mowing and trimming"), result.get().getTasks());
    }

    @Test
    public void testDeleteSchedule() {
        MaintenanceSchedule schedule;
        try {
            schedule = new MaintenanceSchedule();
            schedule.setGardenId("1");
            schedule.setDateDebut(parseDate("2024-08-01"));
            schedule.setDateFin(parseDate("2024-08-10"));
            schedule.setTasks(List.of("Mowing"));

            MaintenanceSchedule createdSchedule = service.createSchedule(schedule);

            service.deleteMaintenance(createdSchedule.getScheduleId());

            Optional<MaintenanceSchedule> deletedSchedule = service.getScheduleById(createdSchedule.getScheduleId());
            assertFalse(deletedSchedule.isPresent());
        } catch (ParseException e) {
            fail("Date parsing failed: " + e.getMessage());
        }
    }

}
