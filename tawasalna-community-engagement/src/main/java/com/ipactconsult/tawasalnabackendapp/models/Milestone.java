package com.ipactconsult.tawasalnabackendapp.models;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "milestone")
@Tag(name = "milestone")

public class Milestone {

    private String milestoneId;


    private String name;


    private String description;


    private LocalDateTime dueDate;


    private boolean completed;
}
