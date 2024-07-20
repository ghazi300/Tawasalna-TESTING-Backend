package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.payload.IncidentDto;
import com.tawasalna.tawasalnacrisis.payload.IncidentPayload;
import com.tawasalna.tawasalnacrisis.payload.RecentIncidentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IncidentService {
    Optional<Incident> createIncident(IncidentPayload incidentPayload);
    List<IncidentDto> getAllIncidents();
    Optional<IncidentDto> getIncidentById(String id);
    List<RecentIncidentDto> getRecentIncidents();
    Incident allocateResources(String incidentId, List<String> resourceIds);
}
