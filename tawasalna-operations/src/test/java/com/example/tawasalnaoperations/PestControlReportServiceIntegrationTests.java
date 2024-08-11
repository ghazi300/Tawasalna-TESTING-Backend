package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.repositories.PestControlReportRepository;
import com.example.tawasalnaoperations.services.PestControlReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class PestControlReportServiceIntegrationTests {

    @Autowired
    private PestControlReportService service;

    @Autowired
    private PestControlReportRepository repository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() {
        repository.deleteAll(); // Nettoyer la base de données avant chaque test
    }

    private Date parseDate(String date) throws ParseException {
        return sdf.parse(date);
    }

    @Test
    public void testCreateAndRetrieveReport() throws ParseException {
        PestControlReport report = new PestControlReport();
        report.setGardenId("1");
        report.setReportDate(parseDate("2024-08-01"));
        report.setPestsIdentified(List.of("Aphids"));
        report.setActionsTaken(List.of("Insecticide application"));
        report.setStatus("Completed");

        PestControlReport createdReport = service.createReport(report);

        assertNotNull(createdReport);
        assertEquals("1", createdReport.getGardenId());
        assertEquals(parseDate("2024-08-01"), createdReport.getReportDate());

        Optional<PestControlReport> retrievedReport = service.getReportById(createdReport.getReportId());
        assertTrue(retrievedReport.isPresent());
        assertEquals(List.of("Aphids"), retrievedReport.get().getPestsIdentified());
    }

    @Test
    public void testUpdateReport() throws ParseException {
        PestControlReport report = new PestControlReport();
        report.setGardenId("1");
        report.setReportDate(parseDate("2024-08-01"));
        report.setPestsIdentified(List.of("Aphids"));
        report.setActionsTaken(List.of("Insecticide application"));
        report.setStatus("Pending");

        PestControlReport createdReport = service.createReport(report);

        PestControlReport updatedReport = new PestControlReport();
        updatedReport.setGardenId("1");
        updatedReport.setReportDate(parseDate("2024-08-02"));
        updatedReport.setPestsIdentified(List.of("Spider mites"));
        updatedReport.setActionsTaken(List.of("Organic spray"));
        updatedReport.setStatus("Completed");

        PestControlReport result = service.updateReport(createdReport.getReportId(), updatedReport);

        assertNotNull(result);
        assertEquals(parseDate("2024-08-02"), result.getReportDate());
        assertEquals("Spider mites", result.getPestsIdentified().get(0));
    }

    @Test
    public void testDeleteReport() throws ParseException {
        PestControlReport report = new PestControlReport();
        report.setGardenId("1");
        report.setReportDate(parseDate("2024-08-01"));
        report.setPestsIdentified(List.of("Aphids"));
        report.setActionsTaken(List.of("Insecticide application"));
        report.setStatus("Completed");

        PestControlReport createdReport = service.createReport(report);

        service.deleteReport(createdReport.getReportId());

        Optional<PestControlReport> deletedReport = service.getReportById(createdReport.getReportId());
        assertFalse(deletedReport.isPresent());
    }
}

