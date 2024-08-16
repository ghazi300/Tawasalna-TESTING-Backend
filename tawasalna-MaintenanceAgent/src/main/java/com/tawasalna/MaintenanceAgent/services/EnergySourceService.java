package com.tawasalna.MaintenanceAgent.services;

import com.tawasalna.MaintenanceAgent.models.ComponentType;
import com.tawasalna.MaintenanceAgent.models.EnergySource;
import com.tawasalna.MaintenanceAgent.models.EnergySourceSummary;
import com.tawasalna.MaintenanceAgent.repos.EnergieSourceSummaryRepository;
import com.tawasalna.MaintenanceAgent.repos.EnergySourceRepository;
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
public class EnergySourceService {
    private static final Logger logger = LoggerFactory.getLogger(EnergySourceService.class);

    @Autowired
    private EnergySourceRepository energySourceRepository;

    @Autowired
    private EnergieSourceSummaryRepository energySourceSummaryRepository;

    private static final Map<ComponentType, Double> INCREMENT_MAP = new HashMap<>();

    static {
        // Define increments for each energy source type
        INCREMENT_MAP.put(ComponentType.SOLAR, 10.0);
        INCREMENT_MAP.put(ComponentType.WIND, 15.0);
        INCREMENT_MAP.put(ComponentType.HYDRO, 20.0);
        INCREMENT_MAP.put(ComponentType.GEOTHERMAL, 25.0);
        INCREMENT_MAP.put(ComponentType.NUCLEAR, 30.0);
        INCREMENT_MAP.put(ComponentType.OTHER, 5.0);
    }

    @Scheduled(fixedRate = 600000) // 600,000 ms = 10 minutes
    public void updateEnergySources() {
        List<EnergySource> sources = energySourceRepository.findAll();
        double totalEnergyProduced = 0;

        for (EnergySource source : sources) {
            // Get the increment based on the source type
            Double increment = INCREMENT_MAP.getOrDefault(source.getType(), 0.0);
            double newEnergyProduced = source.getAmount() + increment;
            source.setAmount(newEnergyProduced);

            // Save updated entity
            energySourceRepository.save(source);

            // Calculate the total energy produced
            totalEnergyProduced += newEnergyProduced;
        }

        // Save the total energy produced to the summary collection
        EnergySourceSummary summary = new EnergySourceSummary();
        summary.setDate(new Date());
        summary.setTotalAmount(totalEnergyProduced);
        energySourceSummaryRepository.save(summary);

        logger.info("Updated energy sources and saved summary with total energy produced: " + totalEnergyProduced);
    }
}
