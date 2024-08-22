package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.PlanUrgence;
import com.tawasalna.tawasalnacrisis.services.PlanUrgenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlanUrgenceControllerTest {

    @Mock
    private PlanUrgenceService planUrgenceService;

    @InjectMocks
    private PlanUrgenceController planUrgenceController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPlans() {
        PlanUrgence plan1 = new PlanUrgence();
        PlanUrgence plan2 = new PlanUrgence();
        List<PlanUrgence> plans = new ArrayList<>();
        plans.add(plan1);
        plans.add(plan2);

        when(planUrgenceService.getAllPlans()).thenReturn(plans);

        List<PlanUrgence> response = planUrgenceController.getAllPlans();

        assertEquals(plans, response);
        verify(planUrgenceService, times(1)).getAllPlans();
    }

    @Test
    public void testGetPlanByIdFound() {
        String id = "1";
        PlanUrgence plan = new PlanUrgence();
        when(planUrgenceService.getPlanById(id)).thenReturn(Optional.of(plan));

        Optional<PlanUrgence> response = planUrgenceController.getPlanById(id);

        assertEquals(Optional.of(plan), response);
        verify(planUrgenceService, times(1)).getPlanById(id);
    }

    @Test
    public void testGetPlanByIdNotFound() {
        String id = "1";
        when(planUrgenceService.getPlanById(id)).thenReturn(Optional.empty());

        Optional<PlanUrgence> response = planUrgenceController.getPlanById(id);

        assertEquals(Optional.empty(), response);
        verify(planUrgenceService, times(1)).getPlanById(id);
    }

    @Test
    public void testCreatePlan() {
        PlanUrgence plan = new PlanUrgence();
        when(planUrgenceService.createPlan(plan)).thenReturn(plan);

        PlanUrgence response = planUrgenceController.createPlan(plan);

        assertEquals(plan, response);
        verify(planUrgenceService, times(1)).createPlan(plan);
    }

    @Test
    public void testUpdatePlan() {
        String id = "1";
        PlanUrgence plan = new PlanUrgence();
        when(planUrgenceService.updatePlan(id, plan)).thenReturn(plan);

        PlanUrgence response = planUrgenceController.updatePlan(id, plan);

        assertEquals(plan, response);
        verify(planUrgenceService, times(1)).updatePlan(id, plan);
    }

    @Test
    public void testDeletePlan() {
        String id = "1";

        planUrgenceController.deletePlan(id);

        verify(planUrgenceService, times(1)).deletePlan(id);
    }
}
