package com.example.tawasalnaoperations.controllers;



import com.example.tawasalnaoperations.entities.RouteOptimizationRequest;
import com.example.tawasalnaoperations.entities.RouteOptimizationResponse;
import com.example.tawasalnaoperations.services.RouteOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
@CrossOrigin(origins = "http://localhost:4200")

public class RouteOptimizationController {

    @Autowired
    private RouteOptimizationService routeOptimizationService;

    @PostMapping("/optimize")
    public RouteOptimizationResponse optimizeRoutes(@RequestBody RouteOptimizationRequest request) {
        return routeOptimizationService.optimizeRoutes(request.getStartLocation(), request.getEndLocation());
    }
}
