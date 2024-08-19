package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Equipment;
import com.ipactconsult.tawasalnabackendapp.models.FitnessCenterUsage;
import com.ipactconsult.tawasalnabackendapp.payload.request.FitnessCenterUsageRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.FitnessCenterUsageResponse;
import com.ipactconsult.tawasalnabackendapp.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FitnessCenterUsageMapper {

    public FitnessCenterUsage toFitnessCentreUsage(FitnessCenterUsageRequest usageRequest) {
        return FitnessCenterUsage.builder()
                .equipmentId(usageRequest.getEquipmentId())
                .userId(usageRequest.getEquipmentId())
                .startTime(usageRequest.getStartTime())
                .endTime(usageRequest.getEndTime())

                .build();
    }

    public FitnessCenterUsageResponse toFitnessCenterUsageResponse(FitnessCenterUsage usage,Equipment equipment) {
        String equipmentName = equipment != null ? equipment.getName() : "Unknown";
        String equipmentDescription = equipment != null ? equipment.getDescription() : "Unknown";

        return FitnessCenterUsageResponse.builder()
                .id(usage.getId())
                .equipmentId(usage.getEquipmentId())
                .userId(usage.getUserId())
                .startTime(usage.getStartTime())
                .endTime(usage.getEndTime())
                .equipmentName(equipmentName)
                .equipmentDescription(equipmentDescription)
                .build();
    }
}
