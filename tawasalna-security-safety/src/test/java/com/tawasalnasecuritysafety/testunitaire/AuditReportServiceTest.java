package com.tawasalnasecuritysafety.testunitaire;

import com.tawasalnasecuritysafety.models.AuditReport;
import com.tawasalnasecuritysafety.repos.AuditReportRepository;
import com.tawasalnasecuritysafety.services.AuditReportService;
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
public class AuditReportServiceTest {

    @Mock
    private AuditReportRepository auditReportRepository;

    @InjectMocks
    private AuditReportService auditReportService;

    private AuditReport mockAuditReport;

    @BeforeEach
    void setUp() {
        mockAuditReport = new AuditReport();
        mockAuditReport.setId("2");
        mockAuditReport.setSummary("Audit Summary");
        mockAuditReport.setFindings("Findings of the audit");
        mockAuditReport.setRecommendations("Recommendations for improvements");
    }

    @Test
    void testCreateAuditReport() {
        when(auditReportRepository.save(any(AuditReport.class))).thenReturn(mockAuditReport);

        AuditReport createdReport = auditReportService.createAuditReport(mockAuditReport);

        assertNotNull(createdReport);
        assertEquals(mockAuditReport.getId(), createdReport.getId());
        assertEquals("Audit Summary", createdReport.getSummary());
        verify(auditReportRepository).save(mockAuditReport);
    }

    @Test
    void testGetAuditReportById() {
        when(auditReportRepository.findById("2")).thenReturn(Optional.of(mockAuditReport));

        Optional<AuditReport> report = auditReportService.getAuditReportById("2");

        assertTrue(report.isPresent());
        assertEquals(mockAuditReport.getId(), report.get().getId());
        verify(auditReportRepository).findById("2");
    }

    @Test
    void testUpdateAuditReport() {
        when(auditReportRepository.findById("2")).thenReturn(Optional.of(mockAuditReport));
        when(auditReportRepository.save(any(AuditReport.class))).thenReturn(mockAuditReport);

        mockAuditReport.setSummary("Updated Summary");
        AuditReport updatedReport = auditReportService.updateAuditReport("2", mockAuditReport);

        assertNotNull(updatedReport);
        assertEquals("Updated Summary", updatedReport.getSummary());
        verify(auditReportRepository).save(mockAuditReport);
    }

    @Test
    void testDeleteAuditReport() {
        when(auditReportRepository.findById("2")).thenReturn(Optional.of(mockAuditReport));

        auditReportService.deleteAuditReport("2");

        verify(auditReportRepository).deleteById("2");
    }

    @Test
    void testGetAllAuditReports() {
        List<AuditReport> reports = new ArrayList<>();
        reports.add(mockAuditReport);

        when(auditReportRepository.findAll()).thenReturn(reports);

        List<AuditReport> result = auditReportService.getAllAuditReports();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockAuditReport.getId(), result.get(0).getId());
        verify(auditReportRepository).findAll();
    }

    @Test
    void testGetAuditReportById_NotFound() {
        when(auditReportRepository.findById("2")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            auditReportService.getAuditReportById("2");
        });

        String expectedMessage = "AuditReport not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(auditReportRepository).findById("2");
    }

    @Test
    void testUpdateAuditReport_NotFound() {
        when(auditReportRepository.findById("2")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            auditReportService.updateAuditReport("2", mockAuditReport);
        });

        String expectedMessage = "AuditReport not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(auditReportRepository).findById("2");
    }

    @Test
    void testDeleteAuditReport_NotFound() {
        when(auditReportRepository.findById("2")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            auditReportService.deleteAuditReport("2");
        });

        String expectedMessage = "AuditReport not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(auditReportRepository).findById("2");
    }
}
