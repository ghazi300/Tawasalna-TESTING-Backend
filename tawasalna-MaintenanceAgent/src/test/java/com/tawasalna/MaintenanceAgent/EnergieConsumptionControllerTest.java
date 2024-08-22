package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.EnergieConsumptionController;
import com.tawasalna.MaintenanceAgent.models.EnergieConsumption;
import com.tawasalna.MaintenanceAgent.models.LocationTotal;
import com.tawasalna.MaintenanceAgent.repos.EnergieConsumptionRepository;
import com.tawasalna.MaintenanceAgent.services.EnergieConsumptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnergieConsumptionControllerTest {

    @Mock
    private EnergieConsumptionRepository energieConsumptionRepository;

    @Mock
    private EnergieConsumptionService service;

    @InjectMocks
    private EnergieConsumptionController controller;

    private EnergieConsumption consumption1;
    private EnergieConsumption consumption2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        consumption1 = new EnergieConsumption();
        consumption1.setId("1");
        consumption1.setLocation("Location 1");

        consumption2 = new EnergieConsumption();
        consumption2.setId("2");
        consumption2.setLocation("Location 2");
    }

    @Test
    void getAllEnergieConsumptions() {
        List<EnergieConsumption> consumptions = new ArrayList<>();
        consumptions.add(consumption1);
        consumptions.add(consumption2);

        when(energieConsumptionRepository.findAll()).thenReturn(consumptions);

        ResponseEntity<List<EnergieConsumption>> response = controller.getAllEnergieConsumptions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(energieConsumptionRepository, times(1)).findAll();
    }

    @Test
    void createEnergieConsumption() {
        when(energieConsumptionRepository.save(any(EnergieConsumption.class))).thenReturn(consumption1);

        ResponseEntity<EnergieConsumption> response = controller.createEnergieConsumption(consumption1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(consumption1.getId(), response.getBody().getId());
        verify(energieConsumptionRepository, times(1)).save(consumption1);
    }

    @Test
    void getEnergieConsumptionById() {
        when(energieConsumptionRepository.findById("1")).thenReturn(Optional.of(consumption1));

        ResponseEntity<EnergieConsumption> response = controller.getEnergieConsumptionById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consumption1.getId(), response.getBody().getId());
        verify(energieConsumptionRepository, times(1)).findById("1");
    }

    @Test
    void updateEnergieConsumption() {
        when(energieConsumptionRepository.existsById("1")).thenReturn(true);
        when(energieConsumptionRepository.save(any(EnergieConsumption.class))).thenReturn(consumption1);

        ResponseEntity<EnergieConsumption> response = controller.updateEnergieConsumption("1", consumption1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consumption1.getId(), response.getBody().getId());
        verify(energieConsumptionRepository, times(1)).save(consumption1);
    }

    @Test
    void deleteEnergieConsumptionById() {
        when(energieConsumptionRepository.existsById("1")).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteEnergieConsumptionById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(energieConsumptionRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteAllEnergieConsumptions() {
        ResponseEntity<Void> response = controller.deleteEnergieConsumption();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(energieConsumptionRepository, times(1)).deleteAll();
    }

    @Test
    void getByLocation() {
        List<EnergieConsumption> consumptions = new ArrayList<>();
        consumptions.add(consumption1);

        when(service.findByLocation("Location 1")).thenReturn(consumptions);

        ResponseEntity<List<EnergieConsumption>> response = controller.getByLocation("Location 1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).findByLocation("Location 1");
    }

    @Test
    void groupByLocation() {
        // Assuming your service method returns a valid list
        List<Map<String, Object>> groupedData = new ArrayList<>();
        when(service.groupAndSortByLocation()).thenReturn(groupedData);

        ResponseEntity<List<Map<String, Object>>> response = controller.groupByLocation();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).groupAndSortByLocation();
    }

    @Test
    void getTotalAmountByLocation() {
        // Assuming your service method returns a valid list
        List<LocationTotal> totalAmounts = new ArrayList<>();
        when(service.getTotalAmountByLocation()).thenReturn(totalAmounts);

        List<LocationTotal> response = controller.getTotalAmountByLocation();

        assertEquals(0, response.size());
        verify(service, times(1)).getTotalAmountByLocation();
    }
}
