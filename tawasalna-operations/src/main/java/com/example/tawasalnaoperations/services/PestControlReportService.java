package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.repositories.PestControlReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PestControlReportService {

    @Autowired
    private PestControlReportRepository repository;

    public PestControlReport createReport(PestControlReport report) {
        return repository.save(report);
    }

    public Optional<PestControlReport> getReportById(String id) {
        return repository.findById(id);
    }

    public PestControlReport updateReport(String id, PestControlReport updatedReport) {
        return repository.findById(id)
                .map(report -> {
                    report.setGardenId(updatedReport.getGardenId());
                    report.setReportDate(updatedReport.getReportDate());
                    report.setPestsIdentified(updatedReport.getPestsIdentified());
                    report.setActionsTaken(updatedReport.getActionsTaken());
                    report.setStatus(updatedReport.getStatus());
                    return repository.save(report);
                })
                .orElse(null); // Retourner null si le rapport n'est pas trouvé
    }

    public List<PestControlReport> getAllReports() {
        return repository.findAll();
    }

    public void deleteReport(String id) {
        repository.deleteById(id);
    }
}
