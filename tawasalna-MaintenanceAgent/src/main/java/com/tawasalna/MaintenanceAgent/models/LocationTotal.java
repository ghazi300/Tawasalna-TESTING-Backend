package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@Document(collection = "LocationTotal")
public class LocationTotal {
    private String location;
    private double totalAmount;

    public LocationTotal(String location, double totalAmount) {
        this.location = location;
        this.totalAmount = totalAmount;
    }

    // getters and setters
}