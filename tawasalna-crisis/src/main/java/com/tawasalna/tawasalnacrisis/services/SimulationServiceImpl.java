package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Simulation;
import com.tawasalna.tawasalnacrisis.repositories.SimulationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimulationServiceImpl implements  SimulationService{


    private SimulationRepository simulationRepository;

   @Override
    public List<Simulation> getAllSimulations() {
        return simulationRepository.findAll();
    }
    @Override
    public Optional<Simulation> getSimulationById(String id) {
        return simulationRepository.findById(id);
    }
    @Override
    public Simulation createSimulation(Simulation simulation) {
        return simulationRepository.save(simulation);
    }

    @Override
    public Simulation updateSimulation(String id, Simulation simulation) {
        if (simulationRepository.existsById(id)) {
            simulation.setId(id);
            return simulationRepository.save(simulation);
        } else {
            throw new RuntimeException("Simulation not found");
        }
    }

    @Override
    public void deleteSimulation(String id) {
        simulationRepository.deleteById(id);
    }
}
