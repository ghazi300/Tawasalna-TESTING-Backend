package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Simulation;
import com.tawasalna.tawasalnacrisis.services.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/simulations")
@CrossOrigin("*")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @GetMapping
    public List<Simulation> getAllSimulations() {
        return simulationService.getAllSimulations();
    }

    @GetMapping("/{id}")
    public Optional<Simulation> getSimulationById(@PathVariable String id) {
        return simulationService.getSimulationById(id);
    }

    @PostMapping
    public Simulation createSimulation(@RequestBody Simulation simulation) {
        return simulationService.createSimulation(simulation);
    }

    @PutMapping("/{id}")
    public Simulation updateSimulation(@PathVariable String id, @RequestBody Simulation simulation) {
        return simulationService.updateSimulation(id, simulation);
    }

    @DeleteMapping("/{id}")
    public void deleteSimulation(@PathVariable String id) {
        simulationService.deleteSimulation(id);
    }
}
