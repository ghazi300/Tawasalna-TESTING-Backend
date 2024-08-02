package com.tawasalnasecuritysafety.services;

import com.tawasalnasecuritysafety.models.SafetyChecklist;
import com.tawasalnasecuritysafety.repos.SafetyChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SafetyChecklistService {
    @Autowired
    private SafetyChecklistRepository safetyChecklistRepository;

    public List<SafetyChecklist> getAllSafetyChecklists() {
        return safetyChecklistRepository.findAll();
    }

    public Optional<SafetyChecklist> getSafetyChecklistById(String id) {
        return safetyChecklistRepository.findById(id);
    }

    public SafetyChecklist createSafetyChecklist(SafetyChecklist safetyChecklist) {
        return safetyChecklistRepository.save(safetyChecklist);
    }

    public SafetyChecklist updateSafetyChecklist(String id, SafetyChecklist safetyChecklistDetails) {
        SafetyChecklist safetyChecklist = safetyChecklistRepository.findById(id).orElseThrow(() -> new RuntimeException("Safety Checklist not found"));
        safetyChecklist.setTitle(safetyChecklistDetails.getTitle());
        safetyChecklist.setItems(safetyChecklistDetails.getItems());
        safetyChecklist.setComments(safetyChecklistDetails.getComments());
        return safetyChecklistRepository.save(safetyChecklist);
    }

    public void deleteSafetyChecklist(String id) {
        safetyChecklistRepository.deleteById(id);
    }
}
