package com.tawasalna.tawasalnacrisis.payload;

import com.tawasalna.tawasalnacrisis.models.Gravite;
import com.tawasalna.tawasalnacrisis.models.Resource;
import com.tawasalna.tawasalnacrisis.models.Status;
import com.tawasalna.tawasalnacrisis.models.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IncidentDto {
    private String id;
    private String title;
    private String description;
    private Type type;
    private Gravite gravite;
    private Status status;
    private String location;
    private LocalDateTime date;
    private List<Resource> resources;
    private List<String> images;
}
