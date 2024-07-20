package com.ipactconsult.tawasalnabackendapp.payload.response;

import com.ipactconsult.tawasalnabackendapp.models.DiversityInitiativeStatus;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InitiativeResponse {
    private String id;
    private String title;
    private String description;
    private String lead;
    private Date startDate;
    private Date endDate;
    private DiversityInitiativeStatus status;
}
