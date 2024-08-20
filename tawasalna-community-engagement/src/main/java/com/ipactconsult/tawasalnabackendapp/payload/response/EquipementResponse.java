package com.ipactconsult.tawasalnabackendapp.payload.response;

import lombok.*;

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
}
