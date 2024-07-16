package com.tawasalna.tawasalnacrisis.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResourceAllocationRequest {
    private String incidentId;
    private List<String> resourceIds;

    // Getters and Setters
}
