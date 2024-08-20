package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.repositories.PestControlReportRepository;
import com.example.tawasalnaoperations.services.PestControlReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PestControlReportServiceUnitTests {

    @Mock
    private PestControlReportRepository repository;

    @InjectMocks
    private PestControlReportService service;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Date parseDate(String date) throws ParseException {
        return sdf.parse(date);
    }

    @Test
    public void testCreateReport() throws ParseException {
        PestControlReport report = new PestControlReport();
        report.setGardenId("1");
        report.setReportDate(parseDate("2024-08-01"));
        report.setPestsIdentified(List.of("Aphids"));
        report.setActionsTaken(List.of("Insecticide application"));
        report.setStatus("Completed");

        when(repository.save(any(PestControlReport.class))).thenReturn(report);

        PestControlReport createdReport = service.createReport(report);

        assertNotNull(createdReport);
        assertEquals("1", createdReport.getGardenId());
        assertEquals(parseDate("2024-08-01"), createdReport.getReportDate());
        verify(repository, times(1)).save(report);
    }

    @Test
    public void testGetReportById() throws ParseException {
        PestControlReport report = new PestControlReport();
        report.setGardenId("1");
        report.setReportDate(parseDate("2024-08-01"));
        report.setPestsIdentified(List.of("Aphids"));
        report.setActionsTaken(List.of("Insecticide application"));
        report.setStatus("Completed");

        when(repository.findById("1")).thenReturn(Optional.of(report));

        Optional<PestControlReport> foundReport = service.getReportById("1");

        assertTrue(foundReport.isPresent());
        assertEquals("1", foundReport.get().getGardenId());
        verify(repository, times(1)).findById("1");
    }


    @Test
    public void testUpdateReport() throws ParseException {
        PestControlReport existingReport = new PestControlReport();
        existingReport.setGardenId("1");
        existingReport.setReportDate(parseDate("2024-08-01"));
        existingReport.setPestsIdentified(List.of("Aphids"));
        existingReport.setActionsTaken(List.of("Insecticide application"));
        existingReport.setStatus("Pending");

        PestControlReport updatedReport = new PestControlReport();
        updatedReport.setGardenId("1");
        updatedReport.setReportDate(parseDate("2024-08-02"));
        updatedReport.setPestsIdentified(List.of("Spider mites"));
        updatedReport.setActionsTaken(List.of("Organic spray"));
        updatedReport.setStatus("Completed");

        when(repository.findById("1")).thenReturn(Optional.of(existingReport));
        when(repository.save(any(PestControlReport.class))).thenAnswer(invocation -> {
            // Simule la sauvegarde en retournant l'objet passé
            return invocation.getArgument(0);
        });

        PestControlReport result = service.updateReport("1", updatedReport);

        // Utilisation d'ArgumentCaptor pour vérifier l'argument passé à save()
        ArgumentCaptor<PestControlReport> captor = ArgumentCaptor.forClass(PestControlReport.class);
        verify(repository, times(1)).save(captor.capture());
        PestControlReport savedReport = captor.getValue();

        assertNotNull(result);
        assertEquals(parseDate("2024-08-02"), result.getReportDate());
        assertEquals("Spider mites", result.getPestsIdentified().get(0));
        assertEquals("Organic spray", result.getActionsTaken().get(0));
        assertEquals("Completed", result.getStatus());
        assertEquals("1", savedReport.getGardenId());
        assertEquals(parseDate("2024-08-02"), savedReport.getReportDate());
        assertEquals(List.of("Spider mites"), savedReport.getPestsIdentified());
        assertEquals(List.of("Organic spray"), savedReport.getActionsTaken());
        assertEquals("Completed", savedReport.getStatus());
    }

    @Test
    public void testDeleteReport() {
        doNothing().when(repository).deleteById("1");

        service.deleteReport("1");

        verify(repository, times(1)).deleteById("1");
    }
}
