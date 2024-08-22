package com.ipactconsult.tawasalnabackendapp.controller;
import com.ipactconsult.tawasalnabackendapp.controllers.DiversityInitiativeController;
import com.ipactconsult.tawasalnabackendapp.payload.request.InitiativeRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.InitiativeResponse;
import com.ipactconsult.tawasalnabackendapp.service.IDiversityInitiativeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class DiversityInitiativeControllerTest {
    @InjectMocks
    private DiversityInitiativeController controller;

    @Mock
    private IDiversityInitiativeService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateInitiative() {
        // Arrange
        InitiativeRequest request = new InitiativeRequest();
        String expectedId = "123";
        when(service.save(any(InitiativeRequest.class))).thenReturn(expectedId);

        // Act
        ResponseEntity<String> response = controller.createInitiative(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(service, times(1)).save(any(InitiativeRequest.class));
    }

    @Test
    void testGetAllInitiatives() {
        // Arrange
        InitiativeResponse response = new InitiativeResponse();
        List<InitiativeResponse> expectedList = Collections.singletonList(response);
        when(service.getAllInitiatives()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<InitiativeResponse>> responseEntity = controller.getAllInitiatives();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
        verify(service, times(1)).getAllInitiatives();
    }

    @Test
    void testDeleteInitiative() {
        // Arrange
        String id = "123";

        // Act
        ResponseEntity<Void> response = controller.deleteInitiative(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteInitiative(id);
    }
}
