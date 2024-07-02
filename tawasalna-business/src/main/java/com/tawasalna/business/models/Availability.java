package com.tawasalna.business.models;

import lombok.AllArgsConstructor;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "availability")
public class Availability {

    @Id
    private String id;
    private String dayOfWeek;
    private boolean available;
    private String startTime;
    private String endTime;

}