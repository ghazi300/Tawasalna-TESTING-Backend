package com.tawasalna.business.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tawasalna.business.models.enums.QuotePhase;
import com.tawasalna.business.models.enums.QuoteStatus;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quote {

    @Id
    private String id;

    private String message;

    private Float priceTag;

    private boolean businessConfirmed;

    private boolean clientConfirmed;

    private QuoteStatus status;

    private QuotePhase phase;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float additionalCost;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reasonForAdditionalCost;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private List<ReservationPeriod> reservationPeriods;

    @DocumentReference
    private Users businessId;

    @DocumentReference
    private Users clientId;

    @DocumentReference
    private BusinessService serviceId;

    @DocumentReference
    private List<ServiceFeature> chosenFeatures;
}
