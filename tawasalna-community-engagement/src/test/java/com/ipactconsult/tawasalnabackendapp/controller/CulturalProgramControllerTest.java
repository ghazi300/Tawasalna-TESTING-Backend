package com.ipactconsult.tawasalnabackendapp.controller;

import com.ipactconsult.tawasalnabackendapp.controllers.CulturalProgramController;
import com.ipactconsult.tawasalnabackendapp.payload.request.CulturalRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.CulturalResponse;
import com.ipactconsult.tawasalnabackendapp.service.ICulturalProgramService;
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

class CulturalProgramControllerTest {

    @InjectMocks
    private CulturalProgramController controller;

    @Mock
    private ICulturalProgramService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProgram() {
        // Arrange
        CulturalRequest request = new CulturalRequest();
        String expectedId = "123";
        when(service.save(any(CulturalRequest.class))).thenReturn(expectedId);

        // Act
        ResponseEntity<String> response = controller.createProgram(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(service, times(1)).save(any(CulturalRequest.class));
    }

    @Test
    void testGetAllPrograms() {
        // Arrange
        CulturalResponse response = new CulturalResponse();
        List<CulturalResponse> expectedList = Collections.singletonList(response);
        when(service.getAllPrograms()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<CulturalResponse>> responseEntity = controller.getAllPrograms();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
        verify(service, times(1)).getAllPrograms();
    }

    @Test
    void testDeleteProgram() {
        // Arrange
        String id = "123";

        // Act
        ResponseEntity<Void> response = controller.deleteProgram(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteProgram(id);
    }
}
