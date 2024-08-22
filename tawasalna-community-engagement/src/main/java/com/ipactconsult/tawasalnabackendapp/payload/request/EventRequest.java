package com.ipactconsult.tawasalnabackendapp.payload.request;

import com.ipactconsult.tawasalnabackendapp.models.CategoryEvent;
import com.ipactconsult.tawasalnabackendapp.models.StatusEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EventRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    String title;
    @NotBlank(message = "Description is required")
    String description;
    @NotBlank(message = "Start date is required")
    String startDate;
    @NotBlank(message = "End date is required")
    String endDate;
    @NotBlank(message = "Location is required")
    String location;
    @NotNull(message = "Category is required")
    CategoryEvent category;
    @NotNull(message = "Status is required")
    StatusEvent status ;

    String imageId;


}
