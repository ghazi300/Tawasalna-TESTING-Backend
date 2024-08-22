package com.tawasalnasecuritysafety.testintegration;

import com.tawasalnasecuritysafety.models.SafetyChecklist;
import com.tawasalnasecuritysafety.repos.SafetyChecklistRepository;
import com.tawasalnasecuritysafety.services.SafetyChecklistService;
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
public class SafetyChecklistServiceIntegrationTest {

    @Mock
    private SafetyChecklistRepository safetyChecklistRepository;

    @InjectMocks
    private SafetyChecklistService safetyChecklistService;

    private SafetyChecklist mockChecklist;

    @BeforeEach
    void setUp() {
        mockChecklist = new SafetyChecklist();
        mockChecklist.setId("7");
        mockChecklist.setItem("Safety Check Test");
        mockChecklist.setStatus("Completed");
        mockChecklist.setDate("2024-08-09");
    }

    @Test
    void testCreateSafetyChecklist() {
        when(safetyChecklistRepository.save(any(SafetyChecklist.class))).thenReturn(mockChecklist);

        SafetyChecklist createdChecklist = safetyChecklistService.createSafetyChecklist(mockChecklist);

        assertNotNull(createdChecklist);
        assertEquals(mockChecklist.getId(), createdChecklist.getId());
        assertEquals("Safety Check Test", createdChecklist.getItem());
        verify(safetyChecklistRepository).save(mockChecklist);
    }

    @Test
    void testGetSafetyChecklistById() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.of(mockChecklist));

        Optional<SafetyChecklist> checklist = safetyChecklistService.getSafetyChecklistById("7");

        assertTrue(checklist.isPresent());
        assertEquals(mockChecklist.getId(), checklist.get().getId());
        verify(safetyChecklistRepository).findById("7");
    }

    @Test
    void testUpdateSafetyChecklist() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.of(mockChecklist));
        when(safetyChecklistRepository.save(any(SafetyChecklist.class))).thenReturn(mockChecklist);

        mockChecklist.setItem("Updated Safety Check");
        SafetyChecklist updatedChecklist = safetyChecklistService.updateSafetyChecklist("7", mockChecklist);

        assertNotNull(updatedChecklist);
        assertEquals("Updated Safety Check", updatedChecklist.getItem());
        verify(safetyChecklistRepository).save(mockChecklist);
    }

    @Test
    void testDeleteSafetyChecklist() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.of(mockChecklist));

        safetyChecklistService.deleteSafetyChecklist("7");

        verify(safetyChecklistRepository).deleteById("7");
    }

    @Test
    void testGetAllSafetyChecklists() {
        List<SafetyChecklist> checklists = new ArrayList<>();
        checklists.add(mockChecklist);

        when(safetyChecklistRepository.findAll()).thenReturn(checklists);

        List<SafetyChecklist> result = safetyChecklistService.getAllSafetyChecklists();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockChecklist.getId(), result.get(0).getId());
        verify(safetyChecklistRepository).findAll();
    }

    @Test
    void testGetSafetyChecklistById_NotFound() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            safetyChecklistService.getSafetyChecklistById("7");
        });

        String expectedMessage = "SafetyChecklist not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(safetyChecklistRepository).findById("7");
    }

    @Test
    void testUpdateSafetyChecklist_NotFound() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            safetyChecklistService.updateSafetyChecklist("7", mockChecklist);
        });

        String expectedMessage = "SafetyChecklist not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(safetyChecklistRepository).findById("7");
    }

    @Test
    void testDeleteSafetyChecklist_NotFound() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            safetyChecklistService.deleteSafetyChecklist("7");
        });

        String expectedMessage = "SafetyChecklist not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(safetyChecklistRepository).findById("7");
    }
}
