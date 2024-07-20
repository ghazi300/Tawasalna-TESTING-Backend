package com.tawasalnasecuritysafety.services;

import com.tawasalnasecuritysafety.models.Incident;
import com.tawasalnasecuritysafety.repos.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;

    public Incident createIncident(Incident incident) {
        System.out.println("Received incident: " + incident); // Add logging
        Incident savedIncident = incidentRepository.save(incident);
        System.out.println("Saved incident: " + savedIncident); // Add logging
        return savedIncident;
    }
    public Optional<Incident> getIncidentById(String id) {
        return incidentRepository.findById(id);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident updateIncident(String id, Incident incident) {
        if (incidentRepository.existsById(id)) {
            incident.setId(id);
            return incidentRepository.save(incident);
        }
        return null;
    }

    public void deleteIncident(String id) {
        incidentRepository.deleteById(id);
    }


}
