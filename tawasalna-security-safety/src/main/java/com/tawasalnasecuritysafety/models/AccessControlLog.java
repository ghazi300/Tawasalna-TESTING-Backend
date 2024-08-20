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
@Document(collection = "access_control_logs-tawasalna")
@Tag(name = "access_control_logs")
public class AccessControlLog implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Entry type is mandatory")
    private String entryType;

    @NotBlank(message = "Person name is mandatory")
    private String personName;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotNull(message = "Time is mandatory")
    private LocalDateTime time;

    public void setName(String name) {
        this.personName = name;
    }

    public void setType(String type) {
        this.entryType = type;
    }

    public String getName() {
        return this.personName;
    }

}
