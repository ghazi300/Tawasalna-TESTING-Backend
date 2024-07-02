package com.tawasalna.business.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document(collection = "servicefeature")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFeature {
    @Id
    private String id;
    private String title;
    private String description;
    private double price;
    private boolean isBase;
    private boolean isArchived;
}
