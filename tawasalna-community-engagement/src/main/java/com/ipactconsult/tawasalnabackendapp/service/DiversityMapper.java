package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.DiversityInitiative;
import com.ipactconsult.tawasalnabackendapp.payload.request.InitiativeRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.InitiativeResponse;
import org.springframework.stereotype.Service;

@Service
public class DiversityMapper {
    public DiversityInitiative toDiversity(InitiativeRequest initiativeRequest) {
        return DiversityInitiative.builder()
                .title(initiativeRequest.getTitle())
                .description(initiativeRequest.getDescription())
                .lead(initiativeRequest.getLead())
                .startDate(initiativeRequest.getStartDate())
                .endDate(initiativeRequest.getEndDate())
                .status(initiativeRequest.getStatus())
                .build();
    }

    public InitiativeResponse toDiversityResponse(DiversityInitiative diversityInitiative) {
        return InitiativeResponse.builder()
                .id(diversityInitiative.getId())
                .title(diversityInitiative.getTitle())
                .description(diversityInitiative.getDescription())
                .lead(diversityInitiative.getLead())
                .startDate(diversityInitiative.getStartDate())
                .endDate(diversityInitiative.getEndDate())
                .status(diversityInitiative.getStatus())
                .build();
    }
}
