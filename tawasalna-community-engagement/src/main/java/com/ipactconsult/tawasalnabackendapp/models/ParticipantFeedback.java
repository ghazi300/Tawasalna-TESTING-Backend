package com.ipactconsult.tawasalnabackendapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "participant_feedback")
public class ParticipantFeedback {
    @Id
    private String id;
    private String eventId;
    private String participantId;
    private String feedback;
    @CreatedDate
    private LocalDateTime feedbackDate;
    private List<String> likes = new ArrayList<>();
    private int likeCount;
    private List<String> replyIds = new ArrayList<>();
}
