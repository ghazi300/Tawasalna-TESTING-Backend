package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.ResidentFeedback;
import com.example.tawasalnaoperations.repositories.ResidentFeedbackRepository;
import com.example.tawasalnaoperations.services.ResidentFeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class ResidentFeedbackServiceIntegrationTests {

    @Autowired
    private ResidentFeedbackService service;

    @Autowired
    private ResidentFeedbackRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll(); // Nettoyer la base de données avant chaque test
    }

    @Test
    public void testCreateAndRetrieveFeedback() {
        ResidentFeedback feedback = new ResidentFeedback();
        feedback.setFeedbackId("1");
        feedback.setResidentId("John Doe");
        feedback.setComments("Excellent service!");

        ResidentFeedback createdFeedback = service.createFeedback(feedback);

        assertNotNull(createdFeedback);
        assertEquals("1", createdFeedback.getFeedbackId());
        assertEquals("John Doe", createdFeedback.getResidentId());

        Optional<ResidentFeedback> retrievedFeedback = service.getFeedbackById(createdFeedback.getFeedbackId());
        assertTrue(retrievedFeedback.isPresent());
        assertEquals("Excellent service!", retrievedFeedback.get().getComments());
    }

    @Test
    public void testUpdateFeedback() {
        ResidentFeedback feedback = new ResidentFeedback();
        feedback.setFeedbackId("1");
        feedback.setResidentId("John Doe");
        feedback.setComments("Excellent service!");

        ResidentFeedback createdFeedback = service.createFeedback(feedback);

        createdFeedback.setComments("Updated feedback!");
        ResidentFeedback updatedFeedback = service.updateFeedback(createdFeedback);

        assertNotNull(updatedFeedback);
        assertEquals("Updated feedback!", updatedFeedback.getComments());
    }

    @Test
    public void testDeleteFeedback() {
        ResidentFeedback feedback = new ResidentFeedback();
        feedback.setFeedbackId("1");
        feedback.setComments("John Doe");
        feedback.setComments("Excellent service!");

        ResidentFeedback createdFeedback = service.createFeedback(feedback);

        service.deleteFeedback(createdFeedback.getFeedbackId());

        Optional<ResidentFeedback> deletedFeedback = service.getFeedbackById(createdFeedback.getFeedbackId());
        assertFalse(deletedFeedback.isPresent());
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

        service.createFeedback(feedback1);
        service.createFeedback(feedback2);

        List<ResidentFeedback> allFeedbacks = service.getAllFeedbacks();

        assertNotNull(allFeedbacks);
        assertEquals(2, allFeedbacks.size());
    }
}
