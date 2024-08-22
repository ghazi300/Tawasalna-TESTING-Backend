package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.HazardousWasteLog;
import com.example.tawasalnaoperations.entities.MaterialType;
import com.example.tawasalnaoperations.entities.RecyclingMetric;
import com.example.tawasalnaoperations.repositories.HazardousWasteLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HazardousWasteLogService {

    @Autowired
    private HazardousWasteLogRepository repository;

    public List<HazardousWasteLog> getAllLogs() {
        return repository.findAll();
    }

    public Optional<HazardousWasteLog> getLogById(String id) {
        return repository.findById(id);
    }

    public HazardousWasteLog createLog(HazardousWasteLog log) {
        return repository.save(log);
    }

    public HazardousWasteLog updateLog(String id, HazardousWasteLog updatedLog) {
        return repository.findById(id).map(log -> {
            log.setWasteType(updatedLog.getWasteType());
            log.setDisposalMethod(updatedLog.getDisposalMethod());
            log.setQuantity(updatedLog.getQuantity());
            log.setDisposalDate(updatedLog.getDisposalDate());
            log.setCompliantStatus(updatedLog.getCompliantStatus());
            return repository.save(log);
        }).orElseThrow(() -> new RuntimeException("Log not found"));
    }

    public void deleteLog(String id) {
        repository.deleteById(id);
    }

    public Map<String, Long> getWasteStatistics() {
        List<HazardousWasteLog> wasteLogs = repository.findAll();

        return wasteLogs.stream()
                .collect(Collectors.groupingBy(
                        HazardousWasteLog::getWasteType,
                        Collectors.summingLong(log -> Long.parseLong(log.getQuantity()))
                ));
    }
}