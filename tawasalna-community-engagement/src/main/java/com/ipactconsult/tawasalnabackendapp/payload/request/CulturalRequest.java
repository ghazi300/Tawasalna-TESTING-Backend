package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CulturalRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @NotNull(message = "Start date is mandatory")
    private Date startDate;

    @NotNull(message = "End date is mandatory")
    private Date endDate;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotBlank(message = "Coordinator is mandatory")
    private String coordinator;
}
