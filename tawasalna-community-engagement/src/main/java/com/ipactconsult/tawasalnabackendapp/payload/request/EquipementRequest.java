package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EquipementRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 5, max = 200, message = "Description must be between 5 and 200 characters")
    private String description;

    @NotBlank(message = "Location is mandatory")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;
}
