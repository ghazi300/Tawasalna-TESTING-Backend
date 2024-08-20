package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.ResidentFeedback;
import com.example.tawasalnaoperations.repositories.ResidentFeedbackRepository;
import com.example.tawasalnaoperations.services.ResidentFeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResidentFeedbackServiceUnitTests {

    @Mock
    private ResidentFeedbackRepository repository;

    @InjectMocks
    private ResidentFeedbackService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateFeedback() {
        ResidentFeedback feedback = new ResidentFeedback();
        feedback.setFeedbackId("1");
        feedback.setResidentId("John Doe");
        feedback.setComments("Excellent service!");

        when(repository.save(any(ResidentFeedback.class))).thenReturn(feedback);

        ResidentFeedback createdFeedback = service.createFeedback(feedback);

        assertNotNull(createdFeedback);
        assertEquals("1", createdFeedback.getFeedbackId());
        assertEquals("John Doe", createdFeedback.getResidentId());
        assertEquals("Excellent service!", createdFeedback.getComments());
        verify(repository, times(1)).save(feedback);
    }

    @Test
    public void testGetFeedbackById() {
        ResidentFeedback feedback = new ResidentFeedback();
        feedback.setFeedbackId("1");
        feedback.setResidentId("John Doe");
        feedback.setComments("Excellent service!");

        when(repository.findById("1")).thenReturn(Optional.of(feedback));

        Optional<ResidentFeedback> foundFeedback = service.getFeedbackById("1");

        assertTrue(foundFeedback.isPresent());
        assertEquals("1", foundFeedback.get().getFeedbackId());
        verify(repository, times(1)).findById("1");
    }

    @Test
    public void testGetAllFeedbacks() {
        ResidentFeedback feedback1 = new ResidentFeedback();
        feedback1.setFeedbackId("1");
        feedback1.setResidentId("John Doe");
        feedback1.setComments("Excellent service!");

        ResidentFeedback feedback2 = new ResidentFeedback();
        feedback2.setFeedbackId("2");
        feedback2.setResidentId("Jane Smith");
        feedback2.setComments("Good service!");

        when(repository.findAll()).thenReturn(List.of(feedback1, feedback2));

        List<ResidentFeedback> allFeedbacks = service.getAllFeedbacks();

        assertNotNull(allFeedbacks);
        assertEquals(2, allFeedbacks.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testUpdateFeedback() {
        ResidentFeedback existingFeedback = new ResidentFeedback();
        existingFeedback.setFeedbackId("1");
        existingFeedback.setResidentId("John Doe");
        existingFeedback.setComments("Excellent service!");

        ResidentFeedback updatedFeedback = new ResidentFeedback();
        updatedFeedback.setFeedbackId("1");
        updatedFeedback.setResidentId("John Doe");
        updatedFeedback.setComments("Updated feedback!");

        when(repository.findById("1")).thenReturn(Optional.of(existingFeedback));
        when(repository.save(any(ResidentFeedback.class))).thenReturn(updatedFeedback);

        ResidentFeedback result = service.updateFeedback(updatedFeedback);

        assertNotNull(result);
        assertEquals("Updated feedback!", result.getComments());
        verify(repository, times(1)).save(updatedFeedback);
    }

    @Test
    public void testDeleteFeedback() {
        doNothing().when(repository).deleteById("1");

        service.deleteFeedback("1");

        verify(repository, times(1)).deleteById("1");
    }
}
