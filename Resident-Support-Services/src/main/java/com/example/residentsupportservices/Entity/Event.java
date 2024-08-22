package com.example.residentsupportservices.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "eventt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;

    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;

    @DBRef
    private List<com.example.residentsupportservices.entity.Participant> participants;

    private String location;
    private String description;
    private String category;
    private String imageUrl;
    private Integer maxParticipants;
    private String notes;

    public Event(String id) {
        this.id = id;
        this.participants = new ArrayList<>();
    }
}
