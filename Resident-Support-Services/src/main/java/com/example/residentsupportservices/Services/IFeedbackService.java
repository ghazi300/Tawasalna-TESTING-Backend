package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    List<Feedback> getAllFeedbacks();
    Feedback getFeedbackById(String id);
    Feedback createFeedback(Feedback feedback);
    Feedback updateFeedback(String id, Feedback feedback);
    void deleteFeedback(String id);
}
