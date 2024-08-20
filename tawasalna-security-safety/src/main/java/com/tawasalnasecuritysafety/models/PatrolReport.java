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
@Document(collection = "patrol_reports-tawasalna")
@Tag(name = "patrol_reports")
public class PatrolReport implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Route ID is mandatory")
    private String routeId;

    @NotBlank(message = "Observations are mandatory")
    private String observations;

    @NotNull(message = "Time is mandatory")
    private LocalDateTime time;
}
