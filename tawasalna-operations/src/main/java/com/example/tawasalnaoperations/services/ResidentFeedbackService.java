package com.example.tawasalnaoperations.services;


import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.entities.ResidentFeedback;
import com.example.tawasalnaoperations.repositories.ResidentFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentFeedbackService {

    @Autowired
    private ResidentFeedbackRepository repository;

    public ResidentFeedback createFeedback(ResidentFeedback feedback) {
        return repository.save(feedback);
    }

    public Optional<ResidentFeedback> getFeedbackById(String feedbackId) {
        return repository.findById(feedbackId);
    }
    public List<ResidentFeedback> getAllFeedbacks() {
        return repository.findAll();
    }


    public ResidentFeedback updateFeedback(ResidentFeedback feedback) {
        return repository.save(feedback);
    }

    public void deleteFeedback(String feedbackId) {
        repository.deleteById(feedbackId);
    }
}