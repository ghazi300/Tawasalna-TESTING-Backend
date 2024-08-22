package com.example.tawasalnaoperations.controllers;


import com.example.tawasalnaoperations.entities.ResidentFeedback;
import com.example.tawasalnaoperations.services.ResidentFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/residentFeedbacks")
public class ResidentFeedbackController {

    @Autowired
    private ResidentFeedbackService service;

    @PostMapping
    public ResponseEntity<ResidentFeedback> createFeedback(@RequestBody ResidentFeedback feedback) {
        return ResponseEntity.ok(service.createFeedback(feedback));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentFeedback> getFeedbackById(@PathVariable String id) {
        Optional<ResidentFeedback> feedback = service.getFeedbackById(id);
        return feedback.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<ResidentFeedback>> getAllFeedbacks() {
        List<ResidentFeedback> reports = service.getAllFeedbacks();
        return ResponseEntity.ok(reports);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentFeedback> updateFeedback(@PathVariable String id, @RequestBody ResidentFeedback feedback) {
        feedback.setFeedbackId(id);
        return ResponseEntity.ok(service.updateFeedback(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        service.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}