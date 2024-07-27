package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Availability;
import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.models.Resource;
import com.tawasalna.tawasalnacrisis.models.Status;
import com.tawasalna.tawasalnacrisis.payload.IncidentDto;
import com.tawasalna.tawasalnacrisis.payload.IncidentPayload;
import com.tawasalna.tawasalnacrisis.payload.RecentIncidentDto;
import com.tawasalna.tawasalnacrisis.repositories.IncidentRepository;
import com.tawasalna.tawasalnacrisis.repositories.ResourceRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private IncidentRepository incidentRepository;

    private ResourceRepository resourceRepository;

    public Optional<Incident> createIncident(IncidentPayload incidentPayload) {
            Incident incident = new Incident();
            incident.setTitle(incidentPayload.getTitle());
            incident.setDescription(incidentPayload.getDescription());
            incident.setType(incidentPayload.getType());
            incident.setLocation(incidentPayload.getLocation());
            incident.setDate(incidentPayload.getDate());
            incident.setGravite(incidentPayload.getGravite());
            incident.setStatus(Status.EN_COURS);
            incident.setCreatedDate(LocalDateTime.now());
            incident.setImages(incidentPayload.getImages());
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
        LocalDateTime twoHoursAgo = now.minusHours(24);

        List<Incident> recentIncidents = incidentRepository.findByDateAfter(twoHoursAgo);

        // Log statements for debugging
        System.out.println("Now: " + now);
        System.out.println("Two hours ago: " + twoHoursAgo);
        System.out.println("Recent Incidents: " + recentIncidents); // Check what is returned

        return recentIncidents.stream()
                .map(this::convertToRecentDTO)
                .collect(Collectors.toList());
    }

    private RecentIncidentDto convertToRecentDTO(Incident incident) {
        return new RecentIncidentDto(
                incident.getId(),
                incident.getTitle(),
                incident.getStatus(),
                incident.getType()
        );
    }

    private IncidentDto convertToDTO(Incident incident) {
        return new IncidentDto(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getType(),
                incident.getGravite(),
                incident.getStatus(),
                incident.getLocation(),
                incident.getDate(),
                incident.getResources(),
                incident.getImages()
        );
    }

    public Incident allocateResources(String incidentId, List<String> resourceIds) {
        Optional<Incident> optionalIncident = incidentRepository.findById(incidentId);
        if (optionalIncident.isPresent()) {
            Incident incident = optionalIncident.get();
            List<Resource> resources = resourceRepository.findAllById(resourceIds);
            for(Resource r:resources)
                r.setAvailability(Availability.UNAVAILABLE);
            resourceRepository.saveAll(resources);
            incident.setResources(resources);
            return incidentRepository.save(incident);
        }
        return null;
    }

}