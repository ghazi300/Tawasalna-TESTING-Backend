package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Event;
import com.ipactconsult.tawasalnabackendapp.models.ParticipantFeedback;
import com.ipactconsult.tawasalnabackendapp.models.Reply;
import com.ipactconsult.tawasalnabackendapp.payload.request.FeedBackRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.ReplyRequest;
import com.ipactconsult.tawasalnabackendapp.repository.ParticipantFeedbackRepository;
import com.ipactconsult.tawasalnabackendapp.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class FeedbackService implements IFeedbackService{
    private final ParticipantFeedbackRepository repository;
    private final FeedBackMapper feedBackMapper;
    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;


    @Override
    public String save(FeedBackRequest feedBackRequest, String eventId, String participantId) {
        ParticipantFeedback feedback=feedBackMapper.toFeedBack(feedBackRequest);
        feedback.setEventId(eventId);
        feedback.setParticipantId(participantId);
        return repository.save(feedback).getId();
    }

    @Override
    public List<ParticipantFeedback> getFeedbacksByEvent(String eventId) {
        List<ParticipantFeedback> feedbackList=repository.findByEventId(eventId);
        return feedbackList;
    }

    @Override
    public boolean likeFeedback(String feedbackId, String userId) {
        Optional<ParticipantFeedback> feedbackOptional = repository.findById(feedbackId);
        if (feedbackOptional.isPresent()) {
            ParticipantFeedback feedback = feedbackOptional.get();
            if (!feedback.getLikes().contains(userId)) {
                feedback.getLikes().add(userId);
                feedback.setLikeCount(feedback.getLikeCount() + 1);
                repository.save(feedback);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unlikeFeedback(String feedbackId, String userId) {
        Optional<ParticipantFeedback> feedbackOptional = repository.findById(feedbackId);
        if (feedbackOptional.isPresent()) {
            ParticipantFeedback feedback = feedbackOptional.get();
            if (feedback.getLikes().contains(userId)) {
                feedback.getLikes().remove(userId);
                feedback.setLikeCount(feedback.getLikeCount() - 1);
                repository.save(feedback);
                return true;
            }
        }
        return false;
    }

    @Override
    public String addReply(String feedbackId, ReplyRequest replyRequest) {
        Optional<ParticipantFeedback>feedbackOptional=repository.findById(feedbackId);
        if(feedbackOptional.isPresent())
        {
            ParticipantFeedback feedback=feedbackOptional.get();
            Reply reply=replyMapper.toReply(replyRequest);
            reply.setFeedbackId(feedbackId);
            Reply savedReplay=replyRepository.save(reply);
            feedback.getReplyIds().add(savedReplay.getId());
            repository.save(feedback);
            return savedReplay.getId();
        }
        else {

            throw new RuntimeException("Feedback with ID " + feedbackId + " not found");
        }

    }

    @Override
    public List<Reply> getRepliesByFeedbackId(String feedbackId) {
        return replyRepository.findByFeedbackId(feedbackId);
    }
}
