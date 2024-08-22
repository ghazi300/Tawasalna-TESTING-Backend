package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Simulation;
import com.tawasalna.tawasalnacrisis.services.SimulationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimulationControllerTest {

    @Mock
    private SimulationService simulationService;

    @InjectMocks
    private SimulationController simulationController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSimulations() {
        Simulation sim1 = new Simulation();
        Simulation sim2 = new Simulation();
        List<Simulation> simulations = new ArrayList<>();
        simulations.add(sim1);
        simulations.add(sim2);

        when(simulationService.getAllSimulations()).thenReturn(simulations);

        List<Simulation> response = simulationController.getAllSimulations();

        assertEquals(simulations, response);
        verify(simulationService, times(1)).getAllSimulations();
    }

    @Test
    public void testGetSimulationByIdFound() {
        String id = "1";
        Simulation simulation = new Simulation();
        when(simulationService.getSimulationById(id)).thenReturn(Optional.of(simulation));

        Optional<Simulation> response = simulationController.getSimulationById(id);

        assertEquals(Optional.of(simulation), response);
        verify(simulationService, times(1)).getSimulationById(id);
    }

    @Test
    public void testGetSimulationByIdNotFound() {
        String id = "1";
        when(simulationService.getSimulationById(id)).thenReturn(Optional.empty());

        Optional<Simulation> response = simulationController.getSimulationById(id);

        assertEquals(Optional.empty(), response);
        verify(simulationService, times(1)).getSimulationById(id);
    }

    @Test
    public void testCreateSimulation() {
        Simulation simulation = new Simulation();
        when(simulationService.createSimulation(simulation)).thenReturn(simulation);

        Simulation response = simulationController.createSimulation(simulation);

        assertEquals(simulation, response);
        verify(simulationService, times(1)).createSimulation(simulation);
    }

    @Test
    public void testUpdateSimulation() {
        String id = "1";
        Simulation simulation = new Simulation();
        when(simulationService.updateSimulation(id, simulation)).thenReturn(simulation);

        Simulation response = simulationController.updateSimulation(id, simulation);

        assertEquals(simulation, response);
        verify(simulationService, times(1)).updateSimulation(id, simulation);
    }

    @Test
    public void testDeleteSimulation() {
        String id = "1";

        simulationController.deleteSimulation(id);

        verify(simulationService, times(1)).deleteSimulation(id);
    }
}
