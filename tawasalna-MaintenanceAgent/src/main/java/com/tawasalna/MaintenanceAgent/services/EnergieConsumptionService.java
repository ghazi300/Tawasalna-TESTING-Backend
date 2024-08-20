package com.tawasalna.MaintenanceAgent.services;

import com.tawasalna.MaintenanceAgent.models.ComponentType;
import com.tawasalna.MaintenanceAgent.models.EnergieConsumption;
import com.tawasalna.MaintenanceAgent.models.EnergieConsumptionSummary;
import com.tawasalna.MaintenanceAgent.models.LocationTotal;
import com.tawasalna.MaintenanceAgent.repos.EnergieConsumptionRepository;
import com.tawasalna.MaintenanceAgent.repos.EnergieConsumptionSummaryRepository;
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
public class EnergieConsumptionService {
    private static final Logger logger = LoggerFactory.getLogger(EnergieConsumptionService.class);

    @Autowired
    private EnergieConsumptionRepository energieConsumptionRepository;

    @Autowired
    private EnergieConsumptionSummaryRepository energieConsumptionSummaryRepository;

    private static final Map<ComponentType, Double> INCREMENT_MAP = new HashMap<>();

    static {
        // Define increments for each component type
        INCREMENT_MAP.put(ComponentType.OFFICE, 5.0);
        INCREMENT_MAP.put(ComponentType.HOUSE, 10.0);
        INCREMENT_MAP.put(ComponentType.EQUIPMENT, 7.0);
        INCREMENT_MAP.put(ComponentType.LIGHTING, 3.0);
        INCREMENT_MAP.put(ComponentType.HVAC, 15.0);
        INCREMENT_MAP.put(ComponentType.APPLIANCE, 6.0);
        INCREMENT_MAP.put(ComponentType.SYSTEM, 8.0);
        INCREMENT_MAP.put(ComponentType.COMPUTER, 4.0);
        INCREMENT_MAP.put(ComponentType.MACHINE, 12.0);
        INCREMENT_MAP.put(ComponentType.VEHICLE, 20.0);
        INCREMENT_MAP.put(ComponentType.REFRIGERATION, 9.0);
        INCREMENT_MAP.put(ComponentType.OTHER, 2.0);
    }

    @Scheduled(fixedRate = 60000000) // 60,000 ms = 1 minute
    public void updateAmounts() {
        List<EnergieConsumption> consumptions = energieConsumptionRepository.findAll();
        double totalAmount = 0;

        for (EnergieConsumption consumption : consumptions) {
            // Get the increment based on the component type
            Double increment = INCREMENT_MAP.getOrDefault(consumption.getType(), 0.0);
            double newAmount = consumption.getAmount() + increment;
            consumption.setAmount(newAmount);

            // Save updated entity
            energieConsumptionRepository.save(consumption);

            // Calculate the total amount
            totalAmount += newAmount;
        }

        // Save the total amount to the summary collection
        EnergieConsumptionSummary summary = new EnergieConsumptionSummary();
        summary.setDate(new Date());
        summary.setTotalAmount(totalAmount);
        energieConsumptionSummaryRepository.save(summary);

        logger.info("Updated amounts and saved summary with total amount: " + totalAmount);
    }

    public List<EnergieConsumption> findByLocation(String location) {
        return energieConsumptionRepository.findByLocation(location);
    }

    public List<Map<String, Object>> groupAndSortByLocation() {
        return energieConsumptionRepository.groupAndSortByLocation();
    }
    public List<LocationTotal> getTotalAmountByLocation() {
        List<EnergieConsumption> consumptions = energieConsumptionRepository.findAll();

        Map<String, Double> groupedData = consumptions.stream()
                .collect(Collectors.groupingBy(EnergieConsumption::getLocation, Collectors.summingDouble(EnergieConsumption::getAmount)));

        return groupedData.entrySet().stream()
                .map(entry -> new LocationTotal(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> Double.compare(b.getTotalAmount(), a.getTotalAmount()))
                .collect(Collectors.toList());
    }



}
