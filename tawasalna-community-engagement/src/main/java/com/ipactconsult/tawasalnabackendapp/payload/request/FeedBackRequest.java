package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class FeedBackRequest {
    @NotBlank(message = "Feedback is required")
    String feedback;
}
