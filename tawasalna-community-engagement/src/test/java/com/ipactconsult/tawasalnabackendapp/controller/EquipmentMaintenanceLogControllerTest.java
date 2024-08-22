package com.ipactconsult.tawasalnabackendapp.controller;
import com.ipactconsult.tawasalnabackendapp.controllers.EquipmentMaintenanceLogController;
import com.ipactconsult.tawasalnabackendapp.payload.request.EquipmentMaintenanceLogRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipmentMaintenanceLogResponse;
import com.ipactconsult.tawasalnabackendapp.service.IEquipmentMaintenanceLogService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
public class EquipmentMaintenanceLogControllerTest {
    @InjectMocks
    private EquipmentMaintenanceLogController controller;

    @Mock
    private IEquipmentMaintenanceLogService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMaintenanceLog() {
        // Arrange
        EquipmentMaintenanceLogRequest request = new EquipmentMaintenanceLogRequest();
        String expectedId = "123";
        when(service.save(any(EquipmentMaintenanceLogRequest.class))).thenReturn(expectedId);

        // Act
        ResponseEntity<String> response = controller.createMaintenanceLog(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(service, times(1)).save(any(EquipmentMaintenanceLogRequest.class));
    }

    @Test
    void testGetAllMaintenanceLogs() {
        // Arrange
        EquipmentMaintenanceLogResponse response = new EquipmentMaintenanceLogResponse();
        List<EquipmentMaintenanceLogResponse> expectedList = Collections.singletonList(response);
        when(service.getAllMaintenanceLogs()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<EquipmentMaintenanceLogResponse>> responseEntity = controller.getAllMaintenanceLogs();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
        verify(service, times(1)).getAllMaintenanceLogs();
    }

    @Test
    void testGetMaintenanceLogsByEquipmentId() {
        // Arrange
        String equipmentId = "123";
        EquipmentMaintenanceLogResponse response = new EquipmentMaintenanceLogResponse();
        List<EquipmentMaintenanceLogResponse> expectedList = Collections.singletonList(response);
        when(service.getMaintenanceLogsByEquipmentId(equipmentId)).thenReturn(expectedList);

        // Act
        ResponseEntity<List<EquipmentMaintenanceLogResponse>> responseEntity = controller.getMaintenanceLogsByEquipmentId(equipmentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
        verify(service, times(1)).getMaintenanceLogsByEquipmentId(anyString());
    }

    @Test
    void testDeleteLog() {
        // Arrange
        String id = "123";

        // Act
        ResponseEntity<Void> response = controller.deletelog(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteLog(id);
    }
}
