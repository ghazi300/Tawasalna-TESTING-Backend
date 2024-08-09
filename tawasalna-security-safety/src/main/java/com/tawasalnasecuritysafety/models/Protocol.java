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
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "protocols-tawasalna")
@Tag(name = "protocols")
public class Protocol implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Steps are mandatory")
    private List<String> steps;

    @NotNull(message = "Last updated time is mandatory")
    private LocalDateTime lastUpdated;

    @NotBlank(message = "Responsible person is mandatory")
    private String responsiblePerson;

    public void setLastUpdateTime(String s) {
    }

    public void setLastUpdate(String date) {
    }
}
