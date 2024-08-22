package com.tawasalna.MaintenanceAgent;
import com.tawasalna.MaintenanceAgent.controllers.EnergySourceController;
import com.tawasalna.MaintenanceAgent.models.EnergySource;
import com.tawasalna.MaintenanceAgent.repos.EnergySourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnergySourceControllerTest {

    @Mock
    private EnergySourceRepository energySourceRepository;

    @InjectMocks
    private EnergySourceController controller;

    private EnergySource energySource1;
    private EnergySource energySource2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        energySource1 = new EnergySource();
        energySource1.setId("1");
        energySource1.setLocation("Location 1");

        energySource2 = new EnergySource();
        energySource2.setId("2");
        energySource2.setLocation("Location 2");
    }

    @Test
    void getAllEnergySources() {
        List<EnergySource> energySources = new ArrayList<>();
        energySources.add(energySource1);
        energySources.add(energySource2);

        when(energySourceRepository.findAll()).thenReturn(energySources);

        ResponseEntity<List<EnergySource>> response = controller.getAllEnergySources();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(energySourceRepository, times(1)).findAll();
    }

    @Test
    void createEnergySource() {
        when(energySourceRepository.save(any(EnergySource.class))).thenReturn(energySource1);

        ResponseEntity<EnergySource> response = controller.createEnergySource(energySource1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(energySource1.getId(), response.getBody().getId());
        verify(energySourceRepository, times(1)).save(energySource1);
    }

    @Test
    void getEnergySourceById() {
        when(energySourceRepository.findById("1")).thenReturn(Optional.of(energySource1));

        ResponseEntity<EnergySource> response = controller.getEnergySourceById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(energySource1.getId(), response.getBody().getId());
        verify(energySourceRepository, times(1)).findById("1");
    }

    @Test
    void updateEnergySource() {
        when(energySourceRepository.existsById("1")).thenReturn(true);
        when(energySourceRepository.save(any(EnergySource.class))).thenReturn(energySource1);

        ResponseEntity<EnergySource> response = controller.updateEnergySource("1", energySource1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(energySource1.getId(), response.getBody().getId());
        verify(energySourceRepository, times(1)).save(energySource1);
    }

    @Test
    void deleteAllEnergySources() {
        ResponseEntity<Void> response = controller.deleteAllEnergySources();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(energySourceRepository, times(1)).deleteAll();
    }

    @Test
    void deleteEnergySourceById() {
        when(energySourceRepository.existsById("1")).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteEnergySourceById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(energySourceRepository, times(1)).deleteById("1");
    }
}