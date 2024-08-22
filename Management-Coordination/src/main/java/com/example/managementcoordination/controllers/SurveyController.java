package com.example.managementcoordination.controllers;
import com.example.managementcoordination.Services.EmailService;
import com.example.managementcoordination.Services.SurveyService;
import com.example.managementcoordination.entities.Survey;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/surveys")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SurveyController {

    private SurveyService surveyService;



    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        return ResponseEntity.ok(surveys);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable String id) {
        Optional<Survey> survey = surveyService.getSurveyById(id);
        return survey.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey savedSurvey = surveyService.saveSurvey(survey);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSurvey);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable String id, @RequestBody Survey survey) {
        Optional<Survey> existingSurvey = surveyService.getSurveyById(id);
        if (existingSurvey.isPresent()) {
            survey.setId(id);
            Survey updatedSurvey = surveyService.saveSurvey(survey);
            return ResponseEntity.ok(updatedSurvey);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurveyById(@PathVariable String id) {
        surveyService.deleteSurveyById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/percentages")
    public Map<String, Double> getSurveyPercentages() {
        return surveyService.calculateSurveyPercentages();
    }



}