package com.tawasalna.business.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document(collection = "category")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCategory {
    @Id
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private int serviceCount;
    private Boolean isActive;
}
