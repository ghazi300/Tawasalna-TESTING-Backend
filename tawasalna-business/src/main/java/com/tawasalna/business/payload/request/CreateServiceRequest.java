package com.tawasalna.business.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateServiceRequest {
    private String title;
    private String description;
    private Double basePrice;
    private int deliveryTimeInHours;

    private List<FeatureRequest> features;
    private List<AvailabilityRequest> availability;
    private String categoryId;
    private String ownerId;
}