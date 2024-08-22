package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "replies")


public class Reply {
    @Id
    private String id;
    private String feedbackId; // Reference to ParticipantFeedback
    private String participantId;
    private String replyText;
    @CreatedDate
    private LocalDateTime replyDate;
}
