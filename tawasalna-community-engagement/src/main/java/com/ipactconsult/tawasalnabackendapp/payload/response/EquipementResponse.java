package com.ipactconsult.tawasalnabackendapp.payload.response;

import com.ipactconsult.tawasalnabackendapp.models.StatusEquipement;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EquipementResponse {
    private String id;
    private String name;
    private String description;
    private String location;
    private StatusEquipement status;
    private LocalDateTime installationDate;
}
