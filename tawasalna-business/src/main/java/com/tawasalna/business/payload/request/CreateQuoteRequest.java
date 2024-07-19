package com.tawasalna.business.payload.request;

import com.tawasalna.business.models.ReservationPeriod;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuoteRequest {
    private String message;
    private Float priceTag;
    private List<ReservationPeriod> reservationPeriods;
    private String ownerId;
    private List<String> chosenFeaturesIds;
    private String clientId;
    private String serviceId;
    private Float additionalCost;
    private String reasonForAdditionalCost;
}
