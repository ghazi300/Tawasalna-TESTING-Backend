package com.tawasalnasecuritysafety.models;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "patrol_routes-tawasalna")
@Tag(name = "patrol_routes")
public class PatrolRoute implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Officer name is mandatory")
    private String officerName;

    @NotBlank(message = "Route details are mandatory")
    private String routeDetails;

    @NotNull(message = "Start time is mandatory")
    private LocalDateTime startTime;

    @NotNull(message = "End time is mandatory")
    private LocalDateTime endTime;
}
