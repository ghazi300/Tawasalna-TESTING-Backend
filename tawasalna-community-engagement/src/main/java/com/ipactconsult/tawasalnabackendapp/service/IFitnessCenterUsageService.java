package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.FitnessCenterUsage;
import com.ipactconsult.tawasalnabackendapp.payload.request.FitnessCenterUsageRequest;

import com.ipactconsult.tawasalnabackendapp.payload.response.DailyUsageStats;
import com.ipactconsult.tawasalnabackendapp.payload.response.FitnessCenterUsageResponse;

import java.util.List;
import java.util.Map;

public interface IFitnessCenterUsageService {
    String save(FitnessCenterUsageRequest usageRequest);

    List<FitnessCenterUsageResponse> getAllUsages();


    List<DailyUsageStats> getAllEquipmentUsageStats();
}
