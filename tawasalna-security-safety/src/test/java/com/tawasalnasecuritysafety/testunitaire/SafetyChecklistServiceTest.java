package com.tawasalnasecuritysafety.testunitaire;

import com.tawasalnasecuritysafety.models.SafetyChecklist;
import com.tawasalnasecuritysafety.repos.SafetyChecklistRepository;
import com.tawasalnasecuritysafety.services.SafetyChecklistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SafetyChecklistServiceTest {

    @Mock
    private SafetyChecklistRepository safetyChecklistRepository;

    @InjectMocks
    private SafetyChecklistService safetyChecklistService;

    private SafetyChecklist mockSafetyChecklist;

    @BeforeEach
    void setUp() {
        mockSafetyChecklist = new SafetyChecklist();
        mockSafetyChecklist.setId("7"); // Set ID as String
        mockSafetyChecklist.setName("Checklist Test");
        mockSafetyChecklist.setStatus("Incomplete");
    }

    @Test
    void testCreateSafetyChecklist() {
        when(safetyChecklistRepository.save(any(SafetyChecklist.class))).thenReturn(mockSafetyChecklist);

        SafetyChecklist createdChecklist = safetyChecklistService.createSafetyChecklist(mockSafetyChecklist);

        assertNotNull(createdChecklist);
        assertEquals(mockSafetyChecklist.getId(), createdChecklist.getId());
        assertEquals("Checklist Test", createdChecklist.getName());
        verify(safetyChecklistRepository).save(mockSafetyChecklist);
    }

    @Test
    void testGetSafetyChecklistById() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.of(mockSafetyChecklist));

        Optional<SafetyChecklist> checklist = safetyChecklistService.getSafetyChecklistById("7");

        assertTrue(checklist.isPresent());
        assertEquals(mockSafetyChecklist.getId(), checklist.get().getId());
        verify(safetyChecklistRepository).findById("7");
    }

    @Test
    void testUpdateSafetyChecklist() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.of(mockSafetyChecklist));
        when(safetyChecklistRepository.save(any(SafetyChecklist.class))).thenReturn(mockSafetyChecklist);

        mockSafetyChecklist.setStatus("Complete");
        SafetyChecklist updatedChecklist = safetyChecklistService.updateSafetyChecklist("7", mockSafetyChecklist);

        assertNotNull(updatedChecklist);
        assertEquals("Complete", updatedChecklist.getStatus());
        verify(safetyChecklistRepository).save(mockSafetyChecklist);
    }

    @Test
    void testDeleteSafetyChecklist() {
        when(safetyChecklistRepository.findById("7")).thenReturn(Optional.of(mockSafetyChecklist));

        safetyChecklistService.deleteSafetyChecklist("7");

        verify(safetyChecklistRepository).deleteById("7");
    }

    @Test
    void testGetAllSafetyChecklists() {
        List<SafetyChecklist> checklists = new ArrayList<>();
        checklists.add(mockSafetyChecklist);

        when(safetyChecklistRepository.findAll()).thenReturn(checklists);

        List<SafetyChecklist> result = safetyChecklistService.getAllSafetyChecklists();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockSafetyChecklist.getId(), result.get(0).getId());
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
            safetyChecklistService.updateSafetyChecklist("7", mockSafetyChecklist);
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
