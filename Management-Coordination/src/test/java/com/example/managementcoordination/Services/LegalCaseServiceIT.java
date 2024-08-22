package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.LegalCase;
import com.example.managementcoordination.repositories.LegalCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class LegalCaseServiceIT {

    @Mock
    private LegalCaseRepository legalCaseRepository;

    @InjectMocks
    private LegalCaseService legalCaseService;

    private LegalCase testLegalCase;

    @BeforeEach
    void setUp() {
        testLegalCase = new LegalCase("1", "Contract Dispute", LegalCase.CaseType.CONTRACT, "Contract breach by tenant", LegalCase.CaseStatus.OPEN, "Seek resolution");
    }

    @Test
    void getAllCases() {
        when(legalCaseRepository.findAll()).thenReturn(Arrays.asList(testLegalCase));

        List<LegalCase> cases = legalCaseService.getAllCases();

        assertFalse(cases.isEmpty(), "The case list should not be empty.");
        assertEquals(1, cases.size(), "There should be one legal case in the database.");
        assertEquals(testLegalCase, cases.get(0), "The case retrieved should match the test case.");
        verify(legalCaseRepository, times(1)).findAll();
    }

    @Test
    void getCaseById() {
        when(legalCaseRepository.findById(anyString())).thenReturn(Optional.of(testLegalCase));

        Optional<LegalCase> caseOptional = legalCaseService.getCaseById(testLegalCase.getId());

        assertTrue(caseOptional.isPresent(), "The case should be present.");
        assertEquals(testLegalCase, caseOptional.get(), "The case retrieved should match the test case.");
        verify(legalCaseRepository, times(1)).findById(testLegalCase.getId());
    }

    @Test
    void saveCase() {
        when(legalCaseRepository.save(any(LegalCase.class))).thenReturn(testLegalCase);

        LegalCase savedCase = legalCaseService.saveCase(testLegalCase);

        assertNotNull(savedCase.getId(), "Saved case should have an ID.");
        assertEquals(testLegalCase.getCaseTitle(), savedCase.getCaseTitle(), "The case title should match.");
        assertEquals(testLegalCase.getCaseType(), savedCase.getCaseType(), "The case type should match.");
        assertEquals(testLegalCase.getCaseStatus(), savedCase.getCaseStatus(), "The case status should match.");
        assertEquals(testLegalCase.getDescription(), savedCase.getDescription(), "The description should match.");
        assertEquals(testLegalCase.getAdvice(), savedCase.getAdvice(), "The advice should match.");
        verify(legalCaseRepository, times(1)).save(testLegalCase);
    }

    @Test
    void deleteCase() {
        when(legalCaseRepository.findById(anyString())).thenReturn(Optional.of(testLegalCase));

        legalCaseService.deleteCase(testLegalCase.getId());

        verify(legalCaseRepository, times(1)).deleteById(testLegalCase.getId());
    }
}
