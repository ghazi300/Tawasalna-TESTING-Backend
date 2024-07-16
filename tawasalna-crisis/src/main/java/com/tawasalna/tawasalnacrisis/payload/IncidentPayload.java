package com.tawasalna.tawasalnacrisis.payload;

import com.tawasalna.tawasalnacrisis.models.Gravite;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IncidentPayload {
    String title;
    String description;
    String location;
    LocalDateTime date;
    Gravite gravite;
}
