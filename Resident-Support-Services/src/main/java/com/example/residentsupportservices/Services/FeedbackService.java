package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Feedback;
import com.example.residentsupportservices.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback getFeedbackById(String id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.orElse(null); // Handle optional if necessary
    }

    @Override
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback(String id, Feedback feedback) {
        if (!feedbackRepository.existsById(id)) {
            // Handle not found scenario or throw exception
            return null;
        }
        feedback.setId(id); // Set the ID to ensure update on existing entity
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }
}
