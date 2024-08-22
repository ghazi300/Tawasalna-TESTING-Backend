package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Risk;
import com.tawasalna.tawasalnacrisis.services.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/risks")
@CrossOrigin("*")
public class RiskController {

    @Autowired
    private RiskService riskService;

    @GetMapping
    public List<Risk> getAllRisks() {
        return riskService.getAllRisks();
    }

    @GetMapping("/{id}")
    public Optional<Risk> getRiskById(@PathVariable String id) {
        return riskService.getRiskById(id);
    }

    @PostMapping
    public Risk createRisk(@RequestBody Risk risk) {
        return riskService.createRisk(risk);
    }

    @PutMapping("/{id}")
    public Risk updateRisk(@PathVariable String id, @RequestBody Risk riskDetails) {
        return riskService.updateRisk(id, riskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteRisk(@PathVariable String id) {
        riskService.deleteRisk(id);
    }
}