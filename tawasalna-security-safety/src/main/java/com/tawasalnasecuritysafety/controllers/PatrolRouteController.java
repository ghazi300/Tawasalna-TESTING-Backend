package com.tawasalnasecuritysafety.controllers;


import com.tawasalnasecuritysafety.models.PatrolRoute;
import com.tawasalnasecuritysafety.services.PatrolRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patrol-routes")
@CrossOrigin("*")

public class PatrolRouteController {
    @Autowired
    private PatrolRouteService patrolRouteService;

    @PostMapping
    public PatrolRoute createPatrolRoute(@RequestBody PatrolRoute route) {
        return patrolRouteService.createPatrolRoute(route);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatrolRoute> getPatrolRouteById(@PathVariable String id) {
        Optional<PatrolRoute> route = patrolRouteService.getPatrolRouteById(id);
        return route.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PatrolRoute> getAllPatrolRoutes() {
        return patrolRouteService.getAllPatrolRoutes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatrolRoute> updatePatrolRoute(@PathVariable String id, @RequestBody PatrolRoute route) {
        PatrolRoute updatedRoute = patrolRouteService.updatePatrolRoute(id, route);
        return updatedRoute != null ? ResponseEntity.ok(updatedRoute) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatrolRoute(@PathVariable String id) {
        patrolRouteService.deletePatrolRoute(id);
        return ResponseEntity.noContent().build();
    }
}
