package com.ipactconsult.tawasalnabackendapp.controller;

import com.ipactconsult.tawasalnabackendapp.controllers.EventController;
import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.service.IEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class EventControllerTest {
    @InjectMocks
    private EventController controller;

    @Mock
    private IEventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateEvent() {
        // Arrange
        EventRequest request = new EventRequest();
        String expectedId = "123";
        when(eventService.save(any(EventRequest.class))).thenReturn(expectedId);

        // Act
        ResponseEntity<String> response = controller.createEvent(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(eventService, times(1)).save(any(EventRequest.class));
    }

    @Test
    void testGetAllEvents() {
        // Arrange
        EventResponse response = new EventResponse();
        List<EventResponse> expectedList = Collections.singletonList(response);
        when(eventService.getAllEvents()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<EventResponse>> responseEntity = controller.getAllEvents();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void testGetEventById() {
        // Arrange
        String eventId = "123";
        EventResponse response = new EventResponse();
        when(eventService.getEventById(eventId)).thenReturn(response);

        // Act
        ResponseEntity<EventResponse> responseEntity = controller.getEventById(eventId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
        verify(eventService, times(1)).getEventById(eventId);
    }

    @Test
    void testGetEventByIdNotFound() {
        // Arrange
        String eventId = "123";
        when(eventService.getEventById(eventId)).thenReturn(null);

        // Act
        ResponseEntity<EventResponse> responseEntity = controller.getEventById(eventId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(eventService, times(1)).getEventById(eventId);
    }

    @Test
    void testGetAllEventPlanned() {
        // Arrange
        EventResponse response = new EventResponse();
        List<EventResponse> expectedList = Collections.singletonList(response);
        when(eventService.getAllEventPlanned()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<EventResponse>> responseEntity = controller.getAllEventPlanned();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
        verify(eventService, times(1)).getAllEventPlanned();
    }
    @Test
    void testDeleteEvent() {
        // Arrange
        String eventId = "123";

        // Act
        ResponseEntity<Void> response = controller.deleteEvent(eventId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(eventService, times(1)).deleteEvent(eventId);
    }
    @Test
    void testParticipateInEvent() {
        // Arrange
        String eventId = "123";
        String userId = "456";
        when(eventService.participateInEvent(eventId, userId)).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = controller.participateInEvent(eventId, userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Participation successful", responseEntity.getBody());
        verify(eventService, times(1)).participateInEvent(eventId, userId);
    }

    @Test
    void testUnparticipateInEvent() {
        // Arrange
        String eventId = "123";
        String userId = "456";
        when(eventService.unparticipateInEvent(eventId, userId)).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = controller.unparticipateInEvent(eventId, userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Unparticipation successful", responseEntity.getBody());
        verify(eventService, times(1)).unparticipateInEvent(eventId, userId);
    }

    @Test
    void testLikeEvent() {
        // Arrange
        String eventId = "123";
        String userId = "456";
        when(eventService.likeEvent(eventId, userId)).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = controller.likeEvent(eventId, userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Like successful", responseEntity.getBody());
        verify(eventService, times(1)).likeEvent(eventId, userId);
    }

    @Test
    void testUnlikeEvent() {
        // Arrange
        String eventId = "123";
        String userId = "456";
        when(eventService.unlikeEvent(eventId, userId)).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = controller.unlikeEvent(eventId, userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Unlike successful", responseEntity.getBody());
        verify(eventService, times(1)).unlikeEvent(eventId, userId);
    }
}
