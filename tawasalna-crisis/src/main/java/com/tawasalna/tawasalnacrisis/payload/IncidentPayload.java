package com.tawasalna.tawasalnacrisis.payload;

import com.tawasalna.tawasalnacrisis.models.Gravite;
import com.tawasalna.tawasalnacrisis.models.Resource;
import com.tawasalna.tawasalnacrisis.models.Status;
import com.tawasalna.tawasalnacrisis.models.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @NotBlank(message = "Title is mandatory")
    String title;
    @NotNull(message = "Type is mandatory")
    Type type;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;

    @GeoSpatialIndexed
    private double[] location;

    @NotNull(message = "Date is mandatory")
    LocalDateTime date;

    @NotNull(message = "Severity is mandatory")
    Gravite gravite;
    List<String> images;
}
