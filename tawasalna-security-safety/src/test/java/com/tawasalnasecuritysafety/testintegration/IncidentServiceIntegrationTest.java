package com.tawasalnasecuritysafety.testintegration;

import com.tawasalnasecuritysafety.models.Incident;
import com.tawasalnasecuritysafety.repos.IncidentRepository;
import com.tawasalnasecuritysafety.services.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class IncidentServiceIntegrationTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentService incidentService;

    private Incident mockIncident;

    @BeforeEach
    void setUp() {
        mockIncident = new Incident();
        mockIncident.setId("5"); // Set ID as String
        mockIncident.setDescription("Incident Test");
        mockIncident.setLocation("Location Test");
        mockIncident.setTime(LocalDateTime.parse("2024-08-09T10:00:00"));
        mockIncident.setCategory("Test Category");
        mockIncident.setStatus("Open");
    }

    @Test
    void testCreateIncident() {
        when(incidentRepository.save(any(Incident.class))).thenReturn(mockIncident);

        Incident createdIncident = incidentService.createIncident(mockIncident);

        assertNotNull(createdIncident);
        assertEquals(mockIncident.getId(), createdIncident.getId());
        assertEquals("Incident Test", createdIncident.getDescription());
        verify(incidentRepository).save(mockIncident);
    }

    @Test
    void testGetIncidentById() {
        when(incidentRepository.findById("5")).thenReturn(Optional.of(mockIncident));

        Optional<Incident> incident = incidentService.getIncidentById("5");

        assertTrue(incident.isPresent());
        assertEquals(mockIncident.getId(), incident.get().getId());
        verify(incidentRepository).findById("5");
    }

    @Test
    void testUpdateIncident() {
        when(incidentRepository.findById("5")).thenReturn(Optional.of(mockIncident));
        when(incidentRepository.save(any(Incident.class))).thenReturn(mockIncident);

        mockIncident.setDescription("Updated Incident");
        Incident updatedIncident = incidentService.updateIncident("5", mockIncident);

        assertNotNull(updatedIncident);
        assertEquals("Updated Incident", updatedIncident.getDescription());
        verify(incidentRepository).save(mockIncident);
    }

    @Test
    void testDeleteIncident() {
        when(incidentRepository.findById("5")).thenReturn(Optional.of(mockIncident));

        incidentService.deleteIncident("5");

        verify(incidentRepository).deleteById("5");
    }

    @Test
    void testGetAllIncidents() {
        List<Incident> incidents = new ArrayList<>();
        incidents.add(mockIncident);

        when(incidentRepository.findAll()).thenReturn(incidents);

        List<Incident> result = incidentService.getAllIncidents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockIncident.getId(), result.get(0).getId());
        verify(incidentRepository).findAll();
    }

    @Test
    void testGetIncidentById_NotFound() {
        when(incidentRepository.findById("5")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            incidentService.getIncidentById("5");
        });

        String expectedMessage = "Incident not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(incidentRepository).findById("5");
    }

    @Test
    void testUpdateIncident_NotFound() {
        when(incidentRepository.findById("5")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            incidentService.updateIncident("5", mockIncident);
        });

        String expectedMessage = "Incident not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(incidentRepository).findById("5");
    }

    @Test
    void testDeleteIncident_NotFound() {
        when(incidentRepository.findById("5")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            incidentService.deleteIncident("5");
        });

        String expectedMessage = "Incident not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(incidentRepository).findById("5");
    }
}
