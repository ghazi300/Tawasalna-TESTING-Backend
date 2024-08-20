package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.repositories.PestControlReportRepository;
import com.example.tawasalnaoperations.services.PestControlReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class PestControlReportServiceIntegrationTests {

    @InjectMocks
    private PestControlReportService pestControlReportService;

    @Mock
    private PestControlReportRepository pestControlReportRepository;

    @BeforeEach
    public void setUp() {
        pestControlReportRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    public void testCreateReport() {
        PestControlReport report = new PestControlReport();
        report.setReportId("1");
        report.setGardenId("Garden1");
        report.setReportDate(new Date());
        report.setPestsIdentified(Arrays.asList("Pest1", "Pest2"));
        report.setActionsTaken(Arrays.asList("Action1", "Action2"));
        report.setStatus("In Progress");

        when(pestControlReportRepository.save(report)).thenReturn(report);

        PestControlReport created = pestControlReportService.createReport(report);

        assertNotNull(created);
        assertEquals("1", created.getReportId());
        assertEquals("Garden1", created.getGardenId());
        verify(pestControlReportRepository, times(1)).save(report);
    }

    @Test
    public void testGetReportById() {
        PestControlReport report = new PestControlReport();
        report.setReportId("1");
        report.setGardenId("Garden1");
        report.setReportDate(new Date());
        report.setPestsIdentified(Arrays.asList("Pest1", "Pest2"));
        report.setActionsTaken(Arrays.asList("Action1", "Action2"));
        report.setStatus("In Progress");

        when(pestControlReportRepository.findById("1")).thenReturn(Optional.of(report));

        Optional<PestControlReport> found = pestControlReportService.getReportById("1");

        assertTrue(found.isPresent());
        assertEquals("Garden1", found.get().getGardenId());
        verify(pestControlReportRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateReport() {
        PestControlReport report = new PestControlReport();
        report.setReportId("1");
        report.setGardenId("Garden1");
        report.setReportDate(new Date());
        report.setPestsIdentified(Arrays.asList("Pest1", "Pest2"));
        report.setActionsTaken(Arrays.asList("Action1", "Action2"));
        report.setStatus("In Progress");

        when(pestControlReportRepository.findById("1")).thenReturn(Optional.of(report));
        when(pestControlReportRepository.save(report)).thenReturn(report);

        PestControlReport updatedReport = new PestControlReport();
        updatedReport.setGardenId("Garden2");
        updatedReport.setReportDate(new Date());
        updatedReport.setPestsIdentified(Arrays.asList("Pest3", "Pest4"));
        updatedReport.setActionsTaken(Arrays.asList("Action3", "Action4"));
        updatedReport.setStatus("Completed");

        PestControlReport updated = pestControlReportService.updateReport("1", updatedReport);

        assertNotNull(updated);
        assertEquals("Garden2", updated.getGardenId());
        assertEquals("Completed", updated.getStatus());
        verify(pestControlReportRepository, times(1)).findById("1");
        verify(pestControlReportRepository, times(1)).save(report);
    }

    @Test
    public void testGetAllReports() {
        PestControlReport report1 = new PestControlReport();
        report1.setReportId("1");
        report1.setGardenId("Garden1");
        report1.setReportDate(new Date());
        report1.setPestsIdentified(Arrays.asList("Pest1", "Pest2"));
        report1.setActionsTaken(Arrays.asList("Action1", "Action2"));
        report1.setStatus("In Progress");

        PestControlReport report2 = new PestControlReport();
        report2.setReportId("2");
        report2.setGardenId("Garden2");
        report2.setReportDate(new Date());
        report2.setPestsIdentified(Arrays.asList("Pest3", "Pest4"));
        report2.setActionsTaken(Arrays.asList("Action3", "Action4"));
        report2.setStatus("Completed");

        List<PestControlReport> reports = Arrays.asList(report1, report2);

        when(pestControlReportRepository.findAll()).thenReturn(reports);

        List<PestControlReport> found = pestControlReportService.getAllReports();

        assertEquals(2, found.size());
        assertEquals("Garden1", found.get(0).getGardenId());
        assertEquals("Garden2", found.get(1).getGardenId());
        verify(pestControlReportRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteReport() {
        String reportId = "1";

        doNothing().when(pestControlReportRepository).deleteById(reportId);

        pestControlReportService.deleteReport(reportId);

        verify(pestControlReportRepository, times(1)).deleteById(reportId);
    }
}
