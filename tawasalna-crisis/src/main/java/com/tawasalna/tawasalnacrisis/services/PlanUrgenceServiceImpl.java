package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.PlanUrgence;
import com.tawasalna.tawasalnacrisis.repositories.PlanUrgenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlanUrgenceServiceImpl implements PlanUrgenceService {


    private PlanUrgenceRepository planUrgenceRepository;


    @Override
    public List<PlanUrgence> getAllPlans() {
        return planUrgenceRepository.findAll();
    }
    @Override
    public Optional<PlanUrgence> getPlanById(String id) {
        return planUrgenceRepository.findById(id);
    }
    @Override
    public PlanUrgence createPlan(PlanUrgence plan) {
        return planUrgenceRepository.save(plan);
    }
    @Override
    public PlanUrgence updatePlan(String id, PlanUrgence plan) {
        plan.setId(id);
        return planUrgenceRepository.save(plan);
    }
    @Override
    public void deletePlan(String id) {
        planUrgenceRepository.deleteById(id);
    }
}
