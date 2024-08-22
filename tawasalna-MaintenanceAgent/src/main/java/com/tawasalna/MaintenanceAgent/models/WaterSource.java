package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "WaterSource")
public class WaterSource {

    @Id
    private String id;
    private WaterConsumptionType type; // Type of water usage (e.g., domestic, industrial)
    private String location; // Location of the water source
    private Date timestamp; // Timestamp of the recorded data
    private Double volume; // Volume of water used or produced
    private Double efficiencyRating; // Efficiency rating of the water usage or source

}
