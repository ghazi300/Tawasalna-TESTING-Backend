package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.ResidentFeedback;
import com.example.tawasalnaoperations.repositories.ResidentFeedbackRepository;
import com.example.tawasalnaoperations.services.ResidentFeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class ResidentFeedbackServiceIntegrationTests {

    @InjectMocks
    private ResidentFeedbackService residentFeedbackService;

    @Mock
    private ResidentFeedbackRepository residentFeedbackRepository;

    @BeforeEach
    public void setUp() {
        residentFeedbackRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    public void testCreateFeedback() {
        ResidentFeedback feedback = new ResidentFeedback();
        feedback.setFeedbackId("1");
        feedback.setResidentId("Resident1");
        feedback.setFeedbackDate(new Date());
        feedback.setComments("Great service!");

        when(residentFeedbackRepository.save(feedback)).thenReturn(feedback);

        ResidentFeedback created = residentFeedbackService.createFeedback(feedback);

        assertNotNull(created);
        assertEquals("1", created.getFeedbackId());
        assertEquals("Resident1", created.getResidentId());
        assertEquals("Great service!", created.getComments());
        verify(residentFeedbackRepository, times(1)).save(feedback);
    }

    @Test
    public void testGetFeedbackById() {
        ResidentFeedback feedback = new ResidentFeedback();
        feedback.setFeedbackId("1");
        feedback.setResidentId("Resident1");
        feedback.setFeedbackDate(new Date());
        feedback.setComments("Great service!");

        when(residentFeedbackRepository.findById("1")).thenReturn(Optional.of(feedback));

        Optional<ResidentFeedback> found = residentFeedbackService.getFeedbackById("1");

        assertTrue(found.isPresent());
        assertEquals("Resident1", found.get().getResidentId());
        assertEquals("Great service!", found.get().getComments());
        verify(residentFeedbackRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateFeedback() {
        ResidentFeedback existingFeedback = new ResidentFeedback();
        existingFeedback.setFeedbackId("1");
        existingFeedback.setResidentId("Resident1");
        existingFeedback.setFeedbackDate(new Date());
        existingFeedback.setComments("Great service!");

        ResidentFeedback updatedFeedback = new ResidentFeedback();
        updatedFeedback.setFeedbackId("1");
        updatedFeedback.setResidentId("Resident1");
        updatedFeedback.setFeedbackDate(new Date());
        updatedFeedback.setComments("Excellent service!");

        when(residentFeedbackRepository.findById("1")).thenReturn(Optional.of(existingFeedback));
        when(residentFeedbackRepository.save(any(ResidentFeedback.class))).thenReturn(updatedFeedback);

        ResidentFeedback updated = residentFeedbackService.updateFeedback(updatedFeedback);

        assertNotNull(updated);
        assertEquals("Excellent service!", updated.getComments());
        verify(residentFeedbackRepository, times(1)).save(updatedFeedback);
    }


    @Test
    public void testGetAllFeedbacks() {
        ResidentFeedback feedback1 = new ResidentFeedback();
        feedback1.setFeedbackId("1");
        feedback1.setResidentId("Resident1");
        feedback1.setFeedbackDate(new Date());
        feedback1.setComments("Great service!");

        ResidentFeedback feedback2 = new ResidentFeedback();
        feedback2.setFeedbackId("2");
        feedback2.setResidentId("Resident2");
        feedback2.setFeedbackDate(new Date());
        feedback2.setComments("Not satisfied.");

        List<ResidentFeedback> feedbacks = List.of(feedback1, feedback2);

        when(residentFeedbackRepository.findAll()).thenReturn(feedbacks);

        List<ResidentFeedback> found = residentFeedbackService.getAllFeedbacks();

        assertEquals(2, found.size());
        assertEquals("Great service!", found.get(0).getComments());
        assertEquals("Not satisfied.", found.get(1).getComments());
        verify(residentFeedbackRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteFeedback() {
        String feedbackId = "1";

        doNothing().when(residentFeedbackRepository).deleteById(feedbackId);

        residentFeedbackService.deleteFeedback(feedbackId);

        verify(residentFeedbackRepository, times(1)).deleteById(feedbackId);
    }
}
