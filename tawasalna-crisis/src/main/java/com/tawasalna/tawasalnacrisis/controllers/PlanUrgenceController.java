package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.PlanUrgence;
import com.tawasalna.tawasalnacrisis.services.PlanUrgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans-urgence")
@CrossOrigin("*")
public class PlanUrgenceController {

    @Autowired
    private PlanUrgenceService planUrgenceService;

    @GetMapping
    public List<PlanUrgence> getAllPlans() {
        return planUrgenceService.getAllPlans();
    }

    @GetMapping("/{id}")
    public Optional<PlanUrgence> getPlanById(@PathVariable String id) {
        return planUrgenceService.getPlanById(id);
    }

    @PostMapping
    public PlanUrgence createPlan(@RequestBody PlanUrgence plan) {
        return planUrgenceService.createPlan(plan);
    }

    @PutMapping("/{id}")
    public PlanUrgence updatePlan(@PathVariable String id, @RequestBody PlanUrgence plan) {
        return planUrgenceService.updatePlan(id, plan);
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable String id) {
        planUrgenceService.deletePlan(id);
    }
}

