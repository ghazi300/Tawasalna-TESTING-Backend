package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.models.Status;
import com.tawasalna.tawasalnacrisis.payload.IncidentDto;
import com.tawasalna.tawasalnacrisis.payload.IncidentPayload;
import com.tawasalna.tawasalnacrisis.payload.RecentIncidentDto;
import com.tawasalna.tawasalnacrisis.repositories.IncidentRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tawasalna.shared.api.service.IFileUploaderAPI;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IncidentServiceImpl implements IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;

    public Optional<Incident> createIncident(IncidentPayload incidentPayload) {
            Incident incident = new Incident();
            incident.setTitle(incidentPayload.getTitle());
            incident.setDescription(incidentPayload.getDescription());
            incident.setLocation(incidentPayload.getLocation());
            incident.setDate(incidentPayload.getDate());
            incident.setGravite(incidentPayload.getGravite());
            incident.setStatus(Status.EN_COURS);
            incident.setCreatedDate(LocalDateTime.now());
            Incident savedIncident = incidentRepository.save(incident);
            return Optional.of(savedIncident);

    }
    public List<IncidentDto> getAllIncidents() {
        return incidentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Optional<IncidentDto> getIncidentById(String id) {
        return incidentRepository.findById(id)
                .map(this::convertToDTO);
    }
    public List<RecentIncidentDto> getRecentIncidents() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursAgo = now.minusHours(2);

        List<Incident> recentIncidents = incidentRepository.findByDateAfter(twentyFourHoursAgo);

        return recentIncidents.stream()
                .map(this::convertToRecentDTO)
                .collect(Collectors.toList());
    }
    private RecentIncidentDto convertToRecentDTO(Incident incident) {
        return new RecentIncidentDto(
                incident.getId(),
                incident.getTitle(),
                incident.getStatus()
        );
    }

    private IncidentDto convertToDTO(Incident incident) {
        return new IncidentDto(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getGravite(),
                incident.getStatus(),
                incident.getLocation(),
                incident.getDate()
        );
    }


    // Add method to update resource allocations
    public Incident allocateResources(String incidentId, List<String> resourceIds) {
        Optional<Incident> optionalIncident = incidentRepository.findById(incidentId);
        if (optionalIncident.isPresent()) {
            Incident incident = optionalIncident.get();
            incident.setResourceIds(resourceIds);
            return incidentRepository.save(incident);
        }
        return null;
    }

}