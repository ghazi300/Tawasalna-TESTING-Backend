package com.tawasalna.business.payload.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UpdateServiceRequest {
    private String title;
    private String description;
    private double basePrice;
    private int deliveryTimeInHours;

    private List<FeatureRequest> featuresToAdd;
    private List<String> featuresToRemove;
    private Map<String, AvailabilityRequest> updatedAvailabilities;
}
