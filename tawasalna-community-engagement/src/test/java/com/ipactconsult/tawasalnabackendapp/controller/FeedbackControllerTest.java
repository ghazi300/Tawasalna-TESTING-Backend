package com.ipactconsult.tawasalnabackendapp.controller;
import com.ipactconsult.tawasalnabackendapp.controllers.FeedbackController;
import com.ipactconsult.tawasalnabackendapp.models.ParticipantFeedback;
import com.ipactconsult.tawasalnabackendapp.models.Reply;
import com.ipactconsult.tawasalnabackendapp.payload.request.FeedBackRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.ReplyRequest;
import com.ipactconsult.tawasalnabackendapp.service.IFeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
public class FeedbackControllerTest {
    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private IFeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFeedback() {
        // Arrange
        FeedBackRequest request = new FeedBackRequest();
        String eventId = "event123";
        String participantId = "participant456";
        String expectedId = "feedback789";
        when(feedbackService.save(request, eventId, participantId)).thenReturn(expectedId);

        // Act
        ResponseEntity<String> response = feedbackController.createFeedback(request, eventId, participantId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(feedbackService, times(1)).save(request, eventId, participantId);
    }

    @Test
    void testGetFeedbacksByEvent() {
        // Arrange
        String eventId = "event123";
        ParticipantFeedback feedback = new ParticipantFeedback();
        List<ParticipantFeedback> expectedFeedbacks = Collections.singletonList(feedback);
        when(feedbackService.getFeedbacksByEvent(eventId)).thenReturn(expectedFeedbacks);

        // Act
        ResponseEntity<List<ParticipantFeedback>> response = feedbackController.getFeedbacksByEvent(eventId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedFeedbacks, response.getBody());
        verify(feedbackService, times(1)).getFeedbacksByEvent(eventId);
    }

    @Test
    void testLikeFeedback() {
        // Arrange
        String feedbackId = "feedback789";
        String userId = "user456";
        when(feedbackService.likeFeedback(feedbackId, userId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = feedbackController.likeFeedback(feedbackId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Like successful", response.getBody());
        verify(feedbackService, times(1)).likeFeedback(feedbackId, userId);
    }

    @Test
    void testLikeFeedbackFailure() {
        // Arrange
        String feedbackId = "feedback789";
        String userId = "user456";
        when(feedbackService.likeFeedback(feedbackId, userId)).thenReturn(false);

        // Act
        ResponseEntity<String> response = feedbackController.likeFeedback(feedbackId, userId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Like failed or user already liked", response.getBody());
        verify(feedbackService, times(1)).likeFeedback(feedbackId, userId);
    }

    @Test
    void testUnlikeFeedback() {
        // Arrange
        String feedbackId = "feedback789";
        String userId = "user456";
        when(feedbackService.unlikeFeedback(feedbackId, userId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = feedbackController.unlikeFeedback(feedbackId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Unlike successful", response.getBody());
        verify(feedbackService, times(1)).unlikeFeedback(feedbackId, userId);
    }

    @Test
    void testUnlikeFeedbackFailure() {
        // Arrange
        String feedbackId = "feedback789";
        String userId = "user456";
        when(feedbackService.unlikeFeedback(feedbackId, userId)).thenReturn(false);

        // Act
        ResponseEntity<String> response = feedbackController.unlikeFeedback(feedbackId, userId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unlike failed or user hasn't liked yet", response.getBody());
        verify(feedbackService, times(1)).unlikeFeedback(feedbackId, userId);
    }

    @Test
    void testAddReply() {
        // Arrange
        String feedbackId = "feedback789";
        ReplyRequest replyRequest = new ReplyRequest();
        String expectedReplyId = "reply123";
        when(feedbackService.addReply(anyString(), any(ReplyRequest.class))).thenReturn(expectedReplyId);

        // Act
        ResponseEntity<String> response = feedbackController.addReply(feedbackId, replyRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReplyId, response.getBody());
        verify(feedbackService, times(1)).addReply(feedbackId, replyRequest);
    }

    @Test
    void testGetRepliesByFeedbackId() {
        // Arrange
        String feedbackId = "feedback789";
        Reply reply = new Reply();
        List<Reply> expectedReplies = Collections.singletonList(reply);
        when(feedbackService.getRepliesByFeedbackId(feedbackId)).thenReturn(expectedReplies);

        // Act
        List<Reply> responses = feedbackController.getRepliesByFeedbackId(feedbackId);

        // Assert
        assertEquals(expectedReplies, responses);
        verify(feedbackService, times(1)).getRepliesByFeedbackId(feedbackId);
    }
}
