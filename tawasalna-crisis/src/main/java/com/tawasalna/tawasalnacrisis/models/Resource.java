package com.tawasalna.tawasalnacrisis.models;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Document(collection = "resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    @Id
    private String id;
    private String name;
    private Type type;
    private Availability availability;
    private String location;
    private List<String> images;

}
