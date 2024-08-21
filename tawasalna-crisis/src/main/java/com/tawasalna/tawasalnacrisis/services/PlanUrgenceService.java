package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.PlanUrgence;

import java.util.List;
import java.util.Optional;

public interface PlanUrgenceService {
     Optional<PlanUrgence> getPlanById(String id);
    PlanUrgence createPlan(PlanUrgence plan);
    PlanUrgence updatePlan(String id, PlanUrgence plan);
    void deletePlan(String id);
    List<PlanUrgence> getAllPlans();
}
