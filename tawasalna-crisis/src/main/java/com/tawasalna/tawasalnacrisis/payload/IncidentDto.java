package com.tawasalna.tawasalnacrisis.payload;

import com.tawasalna.tawasalnacrisis.models.Gravite;
import com.tawasalna.tawasalnacrisis.models.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IncidentDto {
    private String id;  // Ajout de l'identifiant
    private String title;
    private String description;
    private Gravite gravite;
    private Status status;
    private String location;
    private LocalDateTime date;
}
