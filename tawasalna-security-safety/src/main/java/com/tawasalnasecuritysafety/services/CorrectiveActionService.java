package com.tawasalnasecuritysafety.services;

import com.tawasalnasecuritysafety.models.CorrectiveAction;
import com.tawasalnasecuritysafety.repos.CorrectiveActionRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CorrectiveActionService {
    @Autowired
    private CorrectiveActionRepository correctiveActionRepository;

    public List<CorrectiveAction> getAllCorrectiveActions() {
        return correctiveActionRepository.findAll();
    }

    public Optional<CorrectiveAction> getCorrectiveActionById(String id) {
        return correctiveActionRepository.findById(id);
    }

    public CorrectiveAction createCorrectiveAction(CorrectiveAction correctiveAction) {
        return correctiveActionRepository.save(correctiveAction);
    }

    public CorrectiveAction updateCorrectiveAction(String id, CorrectiveAction correctiveActionDetails) {
        CorrectiveAction correctiveAction = correctiveActionRepository.findById(id).orElseThrow(() -> new RuntimeException("Corrective Action not found"));
        correctiveAction.setDescription(correctiveActionDetails.getDescription());
        correctiveAction.setResponsibleParty(correctiveActionDetails.getResponsibleParty());
        correctiveAction.setDeadline(String.valueOf(correctiveActionDetails.getDeadline()));
        correctiveAction.setStatus(correctiveActionDetails.getStatus());
        return correctiveActionRepository.save(correctiveAction);
    }

    public void deleteCorrectiveAction(String id) {
        correctiveActionRepository.deleteById(id);
    }
}
