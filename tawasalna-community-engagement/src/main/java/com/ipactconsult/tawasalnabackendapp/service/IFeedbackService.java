package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.ParticipantFeedback;
import com.ipactconsult.tawasalnabackendapp.models.Reply;
import com.ipactconsult.tawasalnabackendapp.payload.request.FeedBackRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.ReplyRequest;

import java.util.List;

public interface IFeedbackService {

    String save(FeedBackRequest feedBackRequest, String eventId, String participantId);

    List<ParticipantFeedback> getFeedbacksByEvent(String eventId);

    boolean likeFeedback(String feedbackId, String userId);

    boolean unlikeFeedback(String feedbackId, String userId);


    String addReply(String feedbackId, ReplyRequest reply);

    List<Reply> getRepliesByFeedbackId(String feedbackId);
}
