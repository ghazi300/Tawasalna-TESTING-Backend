package com.tawasalna.tawasalnacrisis.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "notificationss")
public class Notification {
    @Id
    private String id;

    @DBRef
    private Incident incident;

    @CreatedDate
    private LocalDateTime dateCreation;
    private Boolean read;
}