package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.exceptions.EquipmentNotFoundException;
import com.ipactconsult.tawasalnabackendapp.models.Equipment;
import com.ipactconsult.tawasalnabackendapp.models.FitnessCenterUsage;
import com.ipactconsult.tawasalnabackendapp.payload.request.FitnessCenterUsageRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.DailyUsageStats;
import com.ipactconsult.tawasalnabackendapp.payload.response.FitnessCenterUsageResponse;
import com.ipactconsult.tawasalnabackendapp.repository.EquipmentRepository;
import com.ipactconsult.tawasalnabackendapp.repository.FitnessCenterUsageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FitnessCenterUsageService implements IFitnessCenterUsageService{
    private final FitnessCenterUsageRepository fitnessCenterUsageRepository;
    private final FitnessCenterUsageMapper fitnessCenterUsageMapper;
    private final EquipmentRepository equipmentRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public String save(FitnessCenterUsageRequest usageRequest) {
        Optional<Equipment> equipmentOpt = equipmentRepository.findById(usageRequest.getEquipmentId());
        if (!equipmentOpt.isPresent()) {
            throw new EquipmentNotFoundException("Equipment with ID " + usageRequest.getEquipmentId() + " not found");
        }
        FitnessCenterUsage fitnessCenterUsage=fitnessCenterUsageMapper.toFitnessCentreUsage(usageRequest);
        fitnessCenterUsage.setDuration(usageRequest.getEndTime()- usageRequest.getStartTime());
        return fitnessCenterUsageRepository.save(fitnessCenterUsage).getId();
    }

    @Override
    public List<FitnessCenterUsageResponse> getAllUsages() {
        List<FitnessCenterUsage> usages = fitnessCenterUsageRepository.findAll();
        return usages.stream()
                .map(usage -> {
                    Equipment equipment = equipmentRepository.findById(usage.getEquipmentId())
                            .orElse(null);
                    return fitnessCenterUsageMapper.toFitnessCenterUsageResponse(usage, equipment);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyUsageStats> getAllEquipmentUsageStats() {
        List<FitnessCenterUsage> allUsage = fitnessCenterUsageRepository.findAll();
        Map<String, Equipment> equipmentMap = equipmentRepository.findAll().stream()
                .collect(Collectors.toMap(Equipment::getId, equipment -> equipment));

        long startOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long startOfWeek = LocalDateTime.now().with(ChronoField.DAY_OF_WEEK, 1)
                .truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long startOfMonth = LocalDateTime.now().with(ChronoField.DAY_OF_MONTH, 1)
                .truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Map<String, List<FitnessCenterUsage>> usageGroupedByEquipment = allUsage.stream()
                .collect(Collectors.groupingBy(FitnessCenterUsage::getEquipmentId));

        return usageGroupedByEquipment.entrySet().stream().map(entry -> {
            String equipmentId = entry.getKey();
            List<FitnessCenterUsage> usageList = entry.getValue();
            String equipmentName = equipmentMap.get(equipmentId).getName();

            long totalUsage = usageList.stream().mapToLong(FitnessCenterUsage::getDuration).sum();
            long dailyUsage = usageList.stream().filter(usage -> usage.getStartTime() >= startOfDay).mapToLong(FitnessCenterUsage::getDuration).sum();
            long weeklyUsage = usageList.stream().filter(usage -> usage.getStartTime() >= startOfWeek).mapToLong(FitnessCenterUsage::getDuration).sum();
            long monthlyUsage = usageList.stream().filter(usage -> usage.getStartTime() >= startOfMonth).mapToLong(FitnessCenterUsage::getDuration).sum();

            return new DailyUsageStats(equipmentId, equipmentName, totalUsage, dailyUsage, weeklyUsage, monthlyUsage);
        }).collect(Collectors.toList());
    }


}
