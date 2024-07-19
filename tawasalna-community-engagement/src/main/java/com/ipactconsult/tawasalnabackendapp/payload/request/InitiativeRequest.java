package com.ipactconsult.tawasalnabackendapp.payload.request;

import com.ipactconsult.tawasalnabackendapp.models.DiversityInitiativeStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitiativeRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @NotBlank(message = "Lead is mandatory")
    private String lead;

    @NotNull(message = "Start date is mandatory")
    private Date startDate;

    @NotNull(message = "End date is mandatory")
    private Date endDate;

    @NotNull(message = "Status is mandatory")
    private DiversityInitiativeStatus status;
}
