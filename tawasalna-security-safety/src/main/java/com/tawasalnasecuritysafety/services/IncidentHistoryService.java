package com.tawasalnasecuritysafety.services;

import com.tawasalnasecuritysafety.models.IncidentHistory;
import com.tawasalnasecuritysafety.repos.IncidentHistoryRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IncidentHistoryService {
    @Autowired
    private IncidentHistoryRepository incidentHistoryRepository;

    public List<IncidentHistory> getAllIncidentHistories() {
        return incidentHistoryRepository.findAll();
    }

    public Optional<IncidentHistory> getIncidentHistoryById(String id) {
        return incidentHistoryRepository.findById(id);
    }

    public IncidentHistory createIncidentHistory(IncidentHistory incidentHistory) {
        return incidentHistoryRepository.save(incidentHistory);
    }

    public IncidentHistory updateIncidentHistory(String id, IncidentHistory incidentHistoryDetails) {
        IncidentHistory incidentHistory = incidentHistoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Incident History not found"));
        incidentHistory.setDescription(incidentHistoryDetails.getDescription());
        incidentHistory.setLocation(incidentHistoryDetails.getLocation());
        incidentHistory.setTime(incidentHistoryDetails.getTime());
        incidentHistory.setCategory(incidentHistoryDetails.getCategory());
        return incidentHistoryRepository.save(incidentHistory);
    }

    public void deleteIncidentHistory(String id) {
        incidentHistoryRepository.deleteById(id);
    }
}
