package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.ParticipantFeedback;
import com.ipactconsult.tawasalnabackendapp.payload.request.FeedBackRequest;
import org.springframework.stereotype.Service;

@Service

public class FeedBackMapper {
    public ParticipantFeedback toFeedBack(FeedBackRequest feedBackRequest) {
        return ParticipantFeedback.builder()
                .feedback(feedBackRequest.getFeedback())
                .build();
    }
}
