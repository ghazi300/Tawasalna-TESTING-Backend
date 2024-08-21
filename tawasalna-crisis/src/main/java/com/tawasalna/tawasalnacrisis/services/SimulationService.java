package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Simulation;

import java.util.List;
import java.util.Optional;

public interface SimulationService {
    List<Simulation> getAllSimulations();
    Optional<Simulation> getSimulationById(String id);
    Simulation createSimulation(Simulation simulation);
    Simulation updateSimulation(String id, Simulation simulation);
    void deleteSimulation(String id);
}
