package com.tawasalna.MaintenanceAgent.services;

import com.tawasalna.MaintenanceAgent.models.WaterConsumption;
import com.tawasalna.MaintenanceAgent.models.WaterConsumptionSummary;
import com.tawasalna.MaintenanceAgent.models.WaterSourceSummary;
import com.tawasalna.MaintenanceAgent.models.WaterConsumptionType;
import com.tawasalna.MaintenanceAgent.repos.WaterConsumptionRepository;
import com.tawasalna.MaintenanceAgent.repos.WaterConsumptionSummaryRepository;
import com.tawasalna.MaintenanceAgent.repos.WaterSourceSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WaterConsumptionService {

    private static final Logger logger = LoggerFactory.getLogger(WaterConsumptionService.class);

    @Autowired
    private WaterConsumptionRepository waterConsumptionRepository;

    @Autowired
    private WaterConsumptionSummaryRepository waterConsumptionSummaryRepository;

    private static final Map<WaterConsumptionType, Double> INCREMENT_MAP = new HashMap<>();

    static {
        // Define increments for each water consumption type
        INCREMENT_MAP.put(WaterConsumptionType.DOMESTIC, 100.0); // Example values
        INCREMENT_MAP.put(WaterConsumptionType.IRRIGATION, 200.0);
        INCREMENT_MAP.put(WaterConsumptionType.INDUSTRIAL, 500.0);
        INCREMENT_MAP.put(WaterConsumptionType.RECREATIONAL, 150.0);
        INCREMENT_MAP.put(WaterConsumptionType.COMMERCIAL, 300.0);
        INCREMENT_MAP.put(WaterConsumptionType.PUBLIC, 50.0);
        INCREMENT_MAP.put(WaterConsumptionType.COOLING, 400.0);
        INCREMENT_MAP.put(WaterConsumptionType.FIRE_SAFETY, 30.0);
        INCREMENT_MAP.put(WaterConsumptionType.WASTEWATER, 100.0);
        INCREMENT_MAP.put(WaterConsumptionType.OTHER, 10.0);
    }

    @Scheduled(fixedRate = 60000) // 60,000 ms = 1 minute
    public void updateAmounts() {
        List<WaterConsumption> consumptions = waterConsumptionRepository.findAll();
        double totalAmount = 0;

        for (WaterConsumption consumption : consumptions) {
            // Get the increment based on the water consumption type
            Double increment = INCREMENT_MAP.getOrDefault(consumption.getType(), 0.0);
            double newAmount = consumption.getVolume() + increment;
            consumption.setVolume(newAmount);

            // Save updated entity
            waterConsumptionRepository.save(consumption);

            // Calculate the total amount
            totalAmount += newAmount;
        }

        // Save the total amount to the summary collection
        WaterConsumptionSummary summary = new WaterConsumptionSummary();
        summary.setDate(new Date());
        summary.setTotalVolume(totalAmount);
        waterConsumptionSummaryRepository.save(summary);

        logger.info("Updated amounts and saved summary with total amount: " + totalAmount);
    }


}
