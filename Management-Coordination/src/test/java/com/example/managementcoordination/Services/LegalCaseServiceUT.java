package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.LegalCase;
import com.example.managementcoordination.repositories.LegalCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LegalCaseServiceUT {

    @Mock
    private LegalCaseRepository legalCaseRepository;

    @InjectMocks
    private LegalCaseService legalCaseService;

    private LegalCase legalCase1;
    private LegalCase legalCase2;

    @BeforeEach
    void setUp() {
        legalCase1 = new LegalCase("1", "Case A", LegalCase.CaseType.LITIGATION, "Description A", LegalCase.CaseStatus.OPEN, "Advice A");
        legalCase2 = new LegalCase("2", "Case B", LegalCase.CaseType.COMPLIANCE, "Description B", LegalCase.CaseStatus.CLOSED, "Advice B");
    }

    @Test
    void getAllCases() {
        // Given
        List<LegalCase> cases = Arrays.asList(legalCase1, legalCase2);
        when(legalCaseRepository.findAll()).thenReturn(cases);

        // When
        List<LegalCase> result = legalCaseService.getAllCases();

        // Then
        assertEquals(2, result.size());
        assertEquals(legalCase1, result.get(0));
        assertEquals(legalCase2, result.get(1));
    }

    @Test
    void getCaseById() {
        // Given
        when(legalCaseRepository.findById("1")).thenReturn(Optional.of(legalCase1));

        // When
        Optional<LegalCase> result = legalCaseService.getCaseById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals(legalCase1, result.get());
    }

    @Test
    void saveCase() {
        // Given
        when(legalCaseRepository.save(legalCase1)).thenReturn(legalCase1);

        // When
        LegalCase result = legalCaseService.saveCase(legalCase1);

        // Then
        assertEquals(legalCase1, result);
        verify(legalCaseRepository, times(1)).save(legalCase1);
    }

    @Test
    void deleteCase() {
        // When
        legalCaseService.deleteCase("1");

        // Then
        verify(legalCaseRepository, times(1)).deleteById("1");
    }
}
