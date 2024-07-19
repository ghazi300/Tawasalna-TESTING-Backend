package com.example.managementcoordination.Services;

import com.example.managementcoordination.Survey;
import com.example.managementcoordination.SurveyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
@AllArgsConstructor
public class SurveyService {
    private SurveyRepository surveyRepository;




    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> getSurveyById(String id) {
        return surveyRepository.findById(id);
    }

    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public void deleteSurveyById(String id) {
        surveyRepository.deleteById(id);
    }


    public Map<String, Double> calculateSurveyPercentages() {
        List<Survey> surveys = surveyRepository.findAll();

        int totalSurveys = surveys.size();

        long excellentCleanlinessCount = surveys.stream()
                .filter(survey -> survey.getCleanliness() != null && survey.getCleanliness().equalsIgnoreCase("Excellent"))
                .count();

        long excellentSecurityCount = surveys.stream()
                .filter(survey -> survey.getSecurity() != null && survey.getSecurity().equalsIgnoreCase("Excellent"))
                .count();

        long excellentAmenitiesCount = surveys.stream()
                .filter(survey -> survey.getAmenities() != null && survey.getAmenities().equalsIgnoreCase("Excellent"))
                .count();

        long excellentMaintenanceCount = surveys.stream()
                .filter(survey -> survey.getMaintenance() != null && survey.getMaintenance().equalsIgnoreCase("Excellent"))
                .count();

        long excellentManagementCount = surveys.stream()
                .filter(survey -> survey.getManagement() != null && survey.getManagement().equalsIgnoreCase("Excellent"))
                .count();
        double cleanlinessPercentage = (double) excellentCleanlinessCount / totalSurveys * 100;
        double securityPercentage = (double) excellentSecurityCount / totalSurveys * 100;
        double amenitiesPercentage = (double) excellentAmenitiesCount / totalSurveys * 100;
        double maintenancePercentage = (double) excellentMaintenanceCount / totalSurveys * 100;
        double managementPercentage = (double) excellentManagementCount / totalSurveys * 100;


        Map<String, Double> percentages = new HashMap<>();
        percentages.put("cleanliness", cleanlinessPercentage);
        percentages.put("security", securityPercentage);
        percentages.put("amenities", amenitiesPercentage);
        percentages.put("maintenance", maintenancePercentage);
        percentages.put("management", managementPercentage);
        return percentages;
    }
}
