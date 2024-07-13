package com.example.residentsupportservices.Services;


import com.example.residentsupportservices.Entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    List<Feedback> getAllFeedbacks();
    Feedback getFeedbackById(Long id);
    Feedback createFeedback(Feedback feedback);
    Feedback updateFeedback(Long id, Feedback feedback);
    void deleteFeedback(Long id);
}
