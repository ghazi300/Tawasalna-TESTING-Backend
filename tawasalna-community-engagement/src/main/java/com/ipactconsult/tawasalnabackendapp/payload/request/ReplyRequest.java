package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReplyRequest {
    @NotBlank(message = "Reply is required")
    String replyText;
}
