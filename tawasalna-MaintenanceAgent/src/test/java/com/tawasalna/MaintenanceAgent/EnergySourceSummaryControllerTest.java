package com.tawasalna.MaintenanceAgent;
import com.tawasalna.MaintenanceAgent.controllers.EnergySourceSummaryController;
import com.tawasalna.MaintenanceAgent.models.EnergySourceSummary;
import com.tawasalna.MaintenanceAgent.repos.EnergieSourceSummaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnergySourceSummaryControllerTest {

    @Mock
    private EnergieSourceSummaryRepository energySourceSummaryRepository;

    @InjectMocks
    private EnergySourceSummaryController controller;

    private EnergySourceSummary summary1;
    private EnergySourceSummary summary2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        summary1 = new EnergySourceSummary();
        summary1.setId("1");
        summary1.setDate(new Date());
        summary1.setTotalAmount(100.0);

        summary2 = new EnergySourceSummary();
        summary2.setId("2");
        summary2.setDate(new Date());
        summary2.setTotalAmount(200.0);
    }

    @Test
    void getAllEnergySourceSummaries() {
        List<EnergySourceSummary> summaries = new ArrayList<>();
        summaries.add(summary1);
        summaries.add(summary2);

        when(energySourceSummaryRepository.findAll()).thenReturn(summaries);

        ResponseEntity<List<EnergySourceSummary>> response = controller.getAllEnergySourceSummaries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(energySourceSummaryRepository, times(1)).findAll();
    }

    @Test
    void createEnergySourceSummary() {
        when(energySourceSummaryRepository.save(any(EnergySourceSummary.class))).thenReturn(summary1);

        ResponseEntity<EnergySourceSummary> response = controller.createEnergySourceSummary(summary1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(summary1.getId(), response.getBody().getId());
        verify(energySourceSummaryRepository, times(1)).save(summary1);
    }

    @Test
    void getEnergySourceSummaryById() {
        when(energySourceSummaryRepository.findById("1")).thenReturn(Optional.of(summary1));

        ResponseEntity<EnergySourceSummary> response = controller.getEnergySourceSummaryById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(summary1.getId(), response.getBody().getId());
        verify(energySourceSummaryRepository, times(1)).findById("1");
    }

    @Test
    void updateEnergySourceSummary() {
        when(energySourceSummaryRepository.existsById("1")).thenReturn(true);
        when(energySourceSummaryRepository.save(any(EnergySourceSummary.class))).thenReturn(summary1);

        ResponseEntity<EnergySourceSummary> response = controller.updateEnergySourceSummary("1", summary1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(summary1.getId(), response.getBody().getId());
        verify(energySourceSummaryRepository, times(1)).save(summary1);
    }

    @Test
    void deleteEnergySourceSummary() {
        when(energySourceSummaryRepository.existsById("1")).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteEnergySourceSummary("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(energySourceSummaryRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteAllEnergySourceSummaries() {
        ResponseEntity<Void> response = controller.deleteAllEnergieConsumptionSummaries();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(energySourceSummaryRepository, times(1)).deleteAll();
    }
}
