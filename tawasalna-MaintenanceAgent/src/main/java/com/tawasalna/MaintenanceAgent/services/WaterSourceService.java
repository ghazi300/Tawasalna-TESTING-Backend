package com.tawasalna.MaintenanceAgent.services;

import com.tawasalna.MaintenanceAgent.models.WaterSource;
import com.tawasalna.MaintenanceAgent.models.WaterSourceSummary;
import com.tawasalna.MaintenanceAgent.repos.WaterSourceRepository;
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

@Service
public class WaterSourceService {
    private static final Logger logger = LoggerFactory.getLogger(WaterSourceService.class);

    @Autowired
    private WaterSourceRepository waterSourceRepository;

    @Autowired
    private WaterSourceSummaryRepository waterSourceSummaryRepository;

    private static final Map<String, Double> INCREMENT_MAP = new HashMap<>();

    static {
        // Define increments for each water source type
        INCREMENT_MAP.put("SOURCE", 100.0); // Example values
        INCREMENT_MAP.put("MER", 200.0);
        INCREMENT_MAP.put("RESERVOIR", 500.0);
        INCREMENT_MAP.put("GLACIERE", 150.0);
        INCREMENT_MAP.put("RECYCLEE", 300.0);
        INCREMENT_MAP.put("DESSALINISATION", 50.0);
        INCREMENT_MAP.put("PLUIE", 400.0);
        INCREMENT_MAP.put("SOUTERRAINE", 30.0);
        INCREMENT_MAP.put("SURFACE", 100.0);
        INCREMENT_MAP.put("OTHER", 10.0);
    }

    @Scheduled(fixedRate = 6000000) // 600,000 ms = 10 minutes
    public void updateWaterSources() {
        List<WaterSource> sources = waterSourceRepository.findAll();
        double totalWaterProduced = 0;

        for (WaterSource source : sources) {
            // Get the increment based on the source type
            Double increment = INCREMENT_MAP.getOrDefault(source.getType().toString(), 0.0);
            double newWaterProduced = source.getVolume() + increment;
            source.setVolume(newWaterProduced);

            // Save updated entity
            waterSourceRepository.save(source);

            // Calculate the total water produced
            totalWaterProduced += newWaterProduced;
        }

        // Save the total water produced to the summary collection
        WaterSourceSummary summary = new WaterSourceSummary();
        summary.setDate(new Date());
        summary.setTotalVolume(totalWaterProduced);
        waterSourceSummaryRepository.save(summary);

        logger.info("Updated water sources and saved summary with total water produced: " + totalWaterProduced);
    }
}
