package com.ipactconsult.tawasalnabackendapp.payload.request;

import com.ipactconsult.tawasalnabackendapp.models.StatusEquipement;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class StatusUpdateRequest {
    private StatusEquipement status;
}
