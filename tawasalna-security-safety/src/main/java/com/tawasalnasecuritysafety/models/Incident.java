package com.tawasalnasecuritysafety.models;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Document(collection = "incident-tawasalna")
@Tag(name = "incident")
public class Incident implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Location is mandatory")
    @Size(max = 200, message = "Location cannot exceed 200 characters")
    private String location;

    @NotNull(message = "Date is mandatory")
    private LocalDateTime time;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotBlank(message = "Status is mandatory")
    private String status;


}
