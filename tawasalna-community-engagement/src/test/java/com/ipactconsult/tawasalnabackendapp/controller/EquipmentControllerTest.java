package com.ipactconsult.tawasalnabackendapp.controller;

import com.ipactconsult.tawasalnabackendapp.controllers.EquipmentController;
import com.ipactconsult.tawasalnabackendapp.models.StatusEquipement;
import com.ipactconsult.tawasalnabackendapp.payload.request.EquipementRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.StatusUpdateRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipementResponse;
import com.ipactconsult.tawasalnabackendapp.service.IEquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EquipmentControllerTest {

    @Mock
    private IEquipmentService service;

    @InjectMocks
    private EquipmentController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEquipment() {
        EquipementRequest request = new EquipementRequest();
        when(service.save(any(EquipementRequest.class))).thenReturn("id");

        ResponseEntity<String> response = controller.createEquipment(request);

        verify(service, times(1)).save(any(EquipementRequest.class));
        assertEquals("id", response.getBody());
    }

    @Test
    public void testGetAllEquipment() {
        EquipementResponse response = new EquipementResponse();
        List<EquipementResponse> responses = Collections.singletonList(response);
        when(service.getAllEquipment()).thenReturn(responses);

        ResponseEntity<List<EquipementResponse>> responseEntity = controller.getAllEquipment();

        verify(service, times(1)).getAllEquipment();
        assertEquals(responses, responseEntity.getBody());
    }

    @Test
    public void testGetEquipmentById() {
        EquipementResponse response = new EquipementResponse();
        when(service.getEquipmentById(anyString())).thenReturn(response);

        ResponseEntity<EquipementResponse> responseEntity = controller.getEquipmentById("1");

        verify(service, times(1)).getEquipmentById("1");
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    public void testDeleteEquipment() {
        doNothing().when(service).deleteEquipment(anyString());

        ResponseEntity<Void> response = controller.deleteEquipment("1");

        verify(service, times(1)).deleteEquipment("1");
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateEquipmentStatus() {
        StatusUpdateRequest statusUpdateRequest = new StatusUpdateRequest(StatusEquipement.EN_SERVICE);
        doNothing().when(service).updateEquipmentStatus(anyString(), any(StatusEquipement.class));

        ResponseEntity<Void> response = controller.updateEquipmentStatus("1", statusUpdateRequest);

        verify(service, times(1)).updateEquipmentStatus("1", StatusEquipement.EN_SERVICE);
        assertEquals(204, response.getStatusCodeValue());
    }
}