package com.tawasalnasecuritysafety.services;


import com.tawasalnasecuritysafety.models.PatrolRoute;
import com.tawasalnasecuritysafety.repos.PatrolRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatrolRouteService {
    @Autowired
    private PatrolRouteRepository patrolRouteRepository;

    public PatrolRoute createPatrolRoute(PatrolRoute route) {
        return patrolRouteRepository.save(route);
    }

    public Optional<PatrolRoute> getPatrolRouteById(String id) {
        return patrolRouteRepository.findById(id);
    }

    public List<PatrolRoute> getAllPatrolRoutes() {
        return patrolRouteRepository.findAll();
    }

    public PatrolRoute updatePatrolRoute(String id, PatrolRoute route) {
        if (patrolRouteRepository.existsById(id)) {
            route.setId(id);
            return patrolRouteRepository.save(route);
        }
        return null;
    }

    public void deletePatrolRoute(String id) {
        patrolRouteRepository.deleteById(id);
    }
}
