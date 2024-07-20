package com.example.managementcoordination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "surveys")
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    @Id
    private String id;
    private String residentName;
    private String cleanliness;
    private String security;
    private String amenities;
    private String maintenance;
    private String management;
}
