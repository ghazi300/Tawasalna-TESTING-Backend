package com.tawasalna.facilitiesmanagement.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "TrafficFlow")
public class TrafficFlow {
    @Id
    private String analyticsId ;
    private String location;
    private LocalDateTime timestamp;
    private int vehicleCount;
    private String congestionLevel;

}
