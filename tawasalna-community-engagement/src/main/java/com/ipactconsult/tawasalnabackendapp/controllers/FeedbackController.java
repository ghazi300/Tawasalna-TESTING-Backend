package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.models.ParticipantFeedback;
import com.ipactconsult.tawasalnabackendapp.models.Reply;
import com.ipactconsult.tawasalnabackendapp.payload.request.CulturalRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.FeedBackRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.ReplyRequest;
import com.ipactconsult.tawasalnabackendapp.service.IFeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback")
@CrossOrigin("*")

public class FeedbackController {
    private final IFeedbackService feedbackService;
    @PostMapping("/{eventId}/{participantId}")

    public ResponseEntity<String> createFeedback(@Valid @RequestBody FeedBackRequest feedBackRequest,@PathVariable String eventId,@PathVariable String participantId) {
        return ResponseEntity.ok(feedbackService.save(feedBackRequest,eventId,participantId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<ParticipantFeedback>> getFeedbacksByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByEvent(eventId));
    }
    @PostMapping("/{feedbackId}/like")
    public ResponseEntity<String> likeFeedback(@PathVariable String feedbackId, @RequestParam String userId) {
        boolean success = feedbackService.likeFeedback(feedbackId, userId);
        if (success) {
            return ResponseEntity.ok("Like successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Like failed or user already liked");
        }
    }

    @PostMapping("/{feedbackId}/unlike")
    public ResponseEntity<String> unlikeFeedback(@PathVariable String feedbackId, @RequestParam String userId) {
        boolean success = feedbackService.unlikeFeedback(feedbackId, userId);
        if (success) {
            return ResponseEntity.ok("Unlike successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unlike failed or user hasn't liked yet");
        }
    }
    @PostMapping("/{feedbackId}/reply")
    public ResponseEntity<String> addReply(@PathVariable String feedbackId,
                                        @RequestBody ReplyRequest reply) {
        return ResponseEntity.ok(feedbackService.addReply(feedbackId, reply));
    }
    @GetMapping("/{feedbackId}/replies")
    public List<Reply> getRepliesByFeedbackId(@PathVariable String feedbackId) {
        return feedbackService.getRepliesByFeedbackId(feedbackId);
    }
}
