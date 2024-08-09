package com.tawasalnasecuritysafety.testintegration;

import com.tawasalnasecuritysafety.models.Compliance;
import com.tawasalnasecuritysafety.repos.ComplianceRepository;
import com.tawasalnasecuritysafety.services.ComplianceService;
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
public class ComplianceServiceIntegrationTest {

    @Mock
    private ComplianceRepository complianceRepository;

    @InjectMocks
    private ComplianceService complianceService;

    private Compliance mockCompliance;

    @BeforeEach
    void setUp() {
        mockCompliance = new Compliance();
        mockCompliance.setId("3");
        mockCompliance.setStatus("Compliant");
        mockCompliance.setComments("Test compliance");
        mockCompliance.setDeadline("2024-08-15");
    }

    @Test
    void testCreateCompliance() {
        when(complianceRepository.save(any(Compliance.class))).thenReturn(mockCompliance);

        Compliance createdCompliance = complianceService.createCompliance(mockCompliance);

        assertNotNull(createdCompliance);
        assertEquals(mockCompliance.getId(), createdCompliance.getId());
        verify(complianceRepository).save(mockCompliance);
    }

    @Test
    void testGetComplianceById() {
        when(complianceRepository.findById("3")).thenReturn(Optional.of(mockCompliance));

        Optional<Compliance> compliance = complianceService.getComplianceById("3");

        assertTrue(compliance.isPresent());
        assertEquals(mockCompliance.getId(), compliance.get().getId());
        verify(complianceRepository).findById("3");
    }

    @Test
    void testUpdateCompliance() {
        when(complianceRepository.findById("3")).thenReturn(Optional.of(mockCompliance));
        when(complianceRepository.save(any(Compliance.class))).thenReturn(mockCompliance);

        mockCompliance.setStatus("Non-Compliant");
        Compliance updatedCompliance = complianceService.updateCompliance("3", mockCompliance);

        assertNotNull(updatedCompliance);
        assertEquals("Non-Compliant", updatedCompliance.getStatus());
        verify(complianceRepository).save(mockCompliance);
    }

    @Test
    void testDeleteCompliance() {
        when(complianceRepository.findById("3")).thenReturn(Optional.of(mockCompliance));

        complianceService.deleteCompliance("3");

        verify(complianceRepository).deleteById("3");
    }

    @Test
    void testGetAllCompliances() {
        List<Compliance> compliances = new ArrayList<>();
        compliances.add(mockCompliance);

        when(complianceRepository.findAll()).thenReturn(compliances);

        List<Compliance> result = complianceService.getAllCompliances();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockCompliance.getId(), result.get(0).getId());
        verify(complianceRepository).findAll();
    }

    @Test
    void testGetComplianceById_NotFound() {
        when(complianceRepository.findById("3")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            complianceService.getComplianceById("3");
        });

        String expectedMessage = "Compliance not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(complianceRepository).findById("3");
    }

    @Test
    void testUpdateCompliance_NotFound() {
        when(complianceRepository.findById("3")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            complianceService.updateCompliance("3", mockCompliance);
        });

        String expectedMessage = "Compliance not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(complianceRepository).findById("3");
    }

    @Test
    void testDeleteCompliance_NotFound() {
        when(complianceRepository.findById("3")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            complianceService.deleteCompliance("3");
        });

        String expectedMessage = "Compliance not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(complianceRepository).findById("3");
    }
}
