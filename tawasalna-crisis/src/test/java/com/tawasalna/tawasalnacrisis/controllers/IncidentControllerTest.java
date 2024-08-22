package com.tawasalna.tawasalnacrisis.controllers;


import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.models.Notification;
import com.tawasalna.tawasalnacrisis.payload.IncidentDto;
import com.tawasalna.tawasalnacrisis.payload.IncidentPayload;
import com.tawasalna.tawasalnacrisis.payload.RecentIncidentDto;
import com.tawasalna.tawasalnacrisis.payload.ResourceAllocationRequest;
import com.tawasalna.tawasalnacrisis.services.IncidentService;
import com.tawasalna.tawasalnacrisis.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncidentControllerTest {

    @Mock
    private IncidentService incidentService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private IncidentController incidentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createIncident_Success() {
        IncidentPayload payload = new IncidentPayload();
        Incident incident = new Incident();
        Notification notification = new Notification();

        when(incidentService.createIncident(payload)).thenReturn(Optional.of(incident));
        when(notificationService.createNotification(incident)).thenReturn(notification);

        ResponseEntity<Incident> response = incidentController.createIncident(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(messagingTemplate, times(1)).convertAndSend("/topic/notifications", notification);
    }

    @Test
    void createIncident_Failure() {
        IncidentPayload payload = new IncidentPayload();

        when(incidentService.createIncident(payload)).thenReturn(Optional.empty());

        ResponseEntity<Incident> response = incidentController.createIncident(payload);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verifyNoInteractions(notificationService);
        verifyNoInteractions(messagingTemplate);
    }

    @Test
    void getIncidents_Success() {
        List<IncidentDto> incidents = Arrays.asList(new IncidentDto());

        when(incidentService.getAllIncidents()).thenReturn(incidents);

        ResponseEntity<List<IncidentDto>> response = incidentController.getIncidents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(incidents, response.getBody());
    }

    @Test
    void getIncidentById_Found() {
        String id = "1";
        IncidentDto incidentDto = new IncidentDto();

        when(incidentService.getIncidentById(id)).thenReturn(Optional.of(incidentDto));

        ResponseEntity<IncidentDto> response = incidentController.getIncidentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(incidentDto, response.getBody());
    }

    @Test
    void getIncidentById_NotFound() {
        String id = "1";

        when(incidentService.getIncidentById(id)).thenReturn(Optional.empty());

        ResponseEntity<IncidentDto> response = incidentController.getIncidentById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getRecentIncidents_Success() {
        List<RecentIncidentDto> recentIncidents = Arrays.asList(new RecentIncidentDto());

        when(incidentService.getRecentIncidents()).thenReturn(recentIncidents);

        ResponseEntity<List<RecentIncidentDto>> response = incidentController.getRecentIncidents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recentIncidents, response.getBody());
    }

    @Test
    void allocateResources_Success() {
        ResourceAllocationRequest request = new ResourceAllocationRequest();
        Incident incident = new Incident();

        when(incidentService.allocateResources(request.getIncidentId(), request.getResourceIds())).thenReturn(incident);

        ResponseEntity<Incident> response = incidentController.allocateResources(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(incident, response.getBody());
    }

    @Test
    void allocateResources_NotFound() {
        ResourceAllocationRequest request = new ResourceAllocationRequest();

        when(incidentService.allocateResources(request.getIncidentId(), request.getResourceIds())).thenReturn(null);

        ResponseEntity<Incident> response = incidentController.allocateResources(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
