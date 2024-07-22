package com.ipactconsult.tawasalnabackendapp.service;


import com.ipactconsult.tawasalnabackendapp.models.CulturalProgram;
import com.ipactconsult.tawasalnabackendapp.payload.request.CulturalRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.CulturalResponse;
import org.springframework.stereotype.Service;

@Service

public class CulturalMapper {
    public CulturalProgram toCultural(CulturalRequest culturalRequest) {
        return CulturalProgram.builder()
                .name(culturalRequest.getName())
                .description(culturalRequest.getDescription())
                .location(culturalRequest.getLocation())
                .coordinator(culturalRequest.getCoordinator())
                .startDate(culturalRequest.getStartDate())
                .endDate(culturalRequest.getEndDate())


                .build();
    }

    public CulturalResponse toCulturalResponse(CulturalProgram culturalProgram) {
        return CulturalResponse.builder()
                .id(culturalProgram.getId())
                .name(culturalProgram.getName())
                .description(culturalProgram.getDescription())
                .location(culturalProgram.getLocation())
                .coordinator(culturalProgram.getCoordinator())
                .startDate(culturalProgram.getStartDate())
                .endDate(culturalProgram.getEndDate())
                .build();
    }
}
