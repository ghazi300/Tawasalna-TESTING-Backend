package com.ipactconsult.tawasalnabackendapp.payload.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class CulturalResponse {
    private String id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private String coordinator;
}
