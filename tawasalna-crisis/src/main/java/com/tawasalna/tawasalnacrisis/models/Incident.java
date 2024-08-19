package com.tawasalna.tawasalnacrisis.models;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
@Document(collection = "incidents")
@Tag(name = "incidents")
public class Incident implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    String title;
    @NotBlank(message = "Title is mandatory")
    Type type;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;

    @NotBlank(message = "Location is mandatory")
    @Size(max = 200, message = "Location cannot exceed 200 characters")
    String location;

    @NotNull(message = "Date is mandatory")
    LocalDateTime date;

    @CreatedDate
    LocalDateTime createdDate;

    @NotNull(message = "Severity is mandatory")
    Gravite gravite;

    @NotNull(message = "Status is mandatory")
    Status status = Status.EN_COURS;
    @DBRef
    List<Resource> resources;
    List<String> images;
}
