package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Survey;
import com.example.managementcoordination.repositories.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyServiceUT {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;

    private Survey sampleSurvey;

    @BeforeEach
    void setUp() {
        // Set up a sample Survey object for testing
        sampleSurvey = new Survey(
                "1",
                "John Doe",
                "Excellent",
                "Good",
                "Medium",
                "Excellent",
                "Good"
        );
    }

    @Test
    void saveSurvey() {
        // Given
        when(surveyRepository.save(any(Survey.class))).thenReturn(sampleSurvey);

        // When
        Survey savedSurvey = surveyService.saveSurvey(sampleSurvey);

        // Then
        assertEquals(sampleSurvey.getId(), savedSurvey.getId());
        verify(surveyRepository, times(1)).save(sampleSurvey);
    }

    @Test
    void calculateSurveyPercentages() {

    }
}
