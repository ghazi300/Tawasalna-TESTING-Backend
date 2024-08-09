package com.tawasalnasecuritysafety.testintegration;

import com.tawasalnasecuritysafety.models.CorrectiveAction;
import com.tawasalnasecuritysafety.repos.CorrectiveActionRepository;
import com.tawasalnasecuritysafety.services.CorrectiveActionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CorrectiveActionServiceIntegrationTest {

    @Mock
    private CorrectiveActionRepository correctiveActionRepository;

    @InjectMocks
    private CorrectiveActionService correctiveActionService;

    private CorrectiveAction mockCorrectiveAction;

    @BeforeEach
    void setUp() {
        mockCorrectiveAction = new CorrectiveAction();
        mockCorrectiveAction.setId("4");
        mockCorrectiveAction.setActionDescription("Corrective Action Test");
        mockCorrectiveAction.setResponsibleParty("John Doe");
        mockCorrectiveAction.setDeadline("2024-08-15");
        mockCorrectiveAction.setStatus("Pending");
    }

    @Test
    void testCreateCorrectiveAction() {
        when(correctiveActionRepository.save(any(CorrectiveAction.class))).thenReturn(mockCorrectiveAction);

        CorrectiveAction createdAction = correctiveActionService.createCorrectiveAction(mockCorrectiveAction);

        assertNotNull(createdAction);
        assertEquals(mockCorrectiveAction.getId(), createdAction.getId());
        assertEquals("Corrective Action Test", createdAction.getActionDescription());
        verify(correctiveActionRepository).save(mockCorrectiveAction);
    }

    @Test
    void testGetCorrectiveActionById() {
        when(correctiveActionRepository.findById("4")).thenReturn(Optional.of(mockCorrectiveAction));

        Optional<CorrectiveAction> action = correctiveActionService.getCorrectiveActionById("4");

        assertTrue(action.isPresent());
        assertEquals(mockCorrectiveAction.getId(), action.get().getId());
        verify(correctiveActionRepository).findById("4");
    }

    @Test
    void testUpdateCorrectiveAction() {
        when(correctiveActionRepository.findById("4")).thenReturn(Optional.of(mockCorrectiveAction));
        when(correctiveActionRepository.save(any(CorrectiveAction.class))).thenReturn(mockCorrectiveAction);

        mockCorrectiveAction.setActionDescription("Updated Action");
        CorrectiveAction updatedAction = correctiveActionService.updateCorrectiveAction("4", mockCorrectiveAction);

        assertNotNull(updatedAction);
        assertEquals("Updated Action", updatedAction.getActionDescription());
        verify(correctiveActionRepository).save(mockCorrectiveAction);
    }

    @Test
    void testDeleteCorrectiveAction() {
        when(correctiveActionRepository.findById("4")).thenReturn(Optional.of(mockCorrectiveAction));

        correctiveActionService.deleteCorrectiveAction("4");

        verify(correctiveActionRepository).deleteById("4");
    }

    @Test
    void testGetAllCorrectiveActions() {
        List<CorrectiveAction> actions = new ArrayList<>();
        actions.add(mockCorrectiveAction);

        when(correctiveActionRepository.findAll()).thenReturn(actions);

        List<CorrectiveAction> result = correctiveActionService.getAllCorrectiveActions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockCorrectiveAction.getId(), result.get(0).getId());
        verify(correctiveActionRepository).findAll();
    }

    @Test
    void testGetCorrectiveActionById_NotFound() {
        when(correctiveActionRepository.findById("4")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            correctiveActionService.getCorrectiveActionById("4");
        });

        String expectedMessage = "CorrectiveAction not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(correctiveActionRepository).findById("4");
    }

    @Test
    void testUpdateCorrectiveAction_NotFound() {
        when(correctiveActionRepository.findById("4")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            correctiveActionService.updateCorrectiveAction("4", mockCorrectiveAction);
        });

        String expectedMessage = "CorrectiveAction not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(correctiveActionRepository).findById("4");
    }

    @Test
    void testDeleteCorrectiveAction_NotFound() {
        when(correctiveActionRepository.findById("4")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            correctiveActionService.deleteCorrectiveAction("4");
        });

        String expectedMessage = "CorrectiveAction not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(correctiveActionRepository).findById("4");
    }
}
