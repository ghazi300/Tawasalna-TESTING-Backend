package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "resource_usage")

public class ResourceUsage {
    @Id
    private String id;
    private String resourceName; // Name of the resource
    private long usageAmount; // Amount of resource used (e.g., in kWh for electricity)
    private long timestamp; // Timestamp of the usage record (in milliseconds)
}
