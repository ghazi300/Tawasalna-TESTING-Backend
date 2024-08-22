package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Survey;
import com.example.managementcoordination.repositories.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SurveyServiceIT {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;

    private Survey testSurvey;

    @BeforeEach
    void setUp() {
        testSurvey = new Survey("1", "John Doe", "Excellent", "Good", "Fair", "Excellent", "Good");
    }

    @Test
    void getAllSurveys() {
        when(surveyRepository.findAll()).thenReturn(Arrays.asList(testSurvey));

        List<Survey> surveys = surveyService.getAllSurveys();

        assertFalse(surveys.isEmpty(), "The survey list should not be empty.");
        assertEquals(1, surveys.size(), "There should be one survey in the database.");
        assertEquals(testSurvey, surveys.get(0), "The survey retrieved should match the test survey.");
        verify(surveyRepository, times(1)).findAll();
    }

    @Test
    void saveSurvey() {
        when(surveyRepository.save(any(Survey.class))).thenReturn(testSurvey);

        Survey savedSurvey = surveyService.saveSurvey(testSurvey);

        assertNotNull(savedSurvey.getId(), "Saved survey should have an ID.");
        assertEquals(testSurvey.getResidentName(), savedSurvey.getResidentName(), "The resident name should match.");
        assertEquals(testSurvey.getCleanliness(), savedSurvey.getCleanliness(), "The cleanliness rating should match.");
        verify(surveyRepository, times(1)).save(testSurvey);
    }

    @Test
    void deleteSurveyById() {
        when(surveyRepository.findById(anyString())).thenReturn(Optional.of(testSurvey));

        surveyService.deleteSurveyById(testSurvey.getId());

        verify(surveyRepository, times(1)).deleteById(testSurvey.getId());
    }

    @Test
    void calculateSurveyPercentages() {
        Survey survey1 = new Survey("2", "Jane Doe", "Excellent", "Good", "Fair", "Good", "Excellent");
        Survey survey2 = new Survey("3", "Alice Smith", "Good", "Excellent", "Fair", "Good", "Excellent");

        when(surveyRepository.findAll()).thenReturn(Arrays.asList(testSurvey, survey1, survey2));

        Map<String, Double> percentages = surveyService.calculateSurveyPercentages();

        assertEquals(33.33, percentages.get("cleanliness"), 0.01, "Cleanliness percentage should be approximately 33.33%");
        assertEquals(33.33, percentages.get("security"), 0.01, "Security percentage should be approximately 33.33%");
        assertEquals(0.00, percentages.get("amenities"), 0.01, "Amenities percentage should be approximately 0.00%");
        assertEquals(66.67, percentages.get("maintenance"), 0.01, "Maintenance percentage should be approximately 66.67%");
        assertEquals(100.00, percentages.get("management"), 0.01, "Management percentage should be 100.00%");
    }
}
