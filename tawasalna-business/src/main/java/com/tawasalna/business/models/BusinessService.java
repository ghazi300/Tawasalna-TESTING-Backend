package com.tawasalna.business.models;

import com.tawasalna.shared.userapi.model.Users;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
//@ToString
@Document(collection = "services")
@AllArgsConstructor
@NoArgsConstructor
public class BusinessService {

    @Id
    private String id;

    private String title;

    private String description;

    private double basePrice;

    private int deliveryTimeInHours;

    private String serviceImage;

    private Boolean isArchived;

    @DocumentReference
    private List<Availability> availability;

    @DocumentReference
    private ServiceCategory serviceCategory;

    @DocumentReference
    private List<ServiceFeature> additionalFeatures;

    @DocumentReference
    private Users owner;

    private Integer totalReviews;

    private Float averageStars;
}

