package com.tawasalnasecuritysafety.services;



import com.tawasalnasecuritysafety.models.AccessControlLog;
import com.tawasalnasecuritysafety.repos.AccessControlLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessControlLogService {
    @Autowired
    private AccessControlLogRepository accessControlLogRepository;

    public AccessControlLog createAccessControlLog(AccessControlLog log) {
        return accessControlLogRepository.save(log);
    }

    public Optional<AccessControlLog> getAccessControlLogById(String id) {
        return accessControlLogRepository.findById(id);
    }

    public List<AccessControlLog> getAllAccessControlLogs() {
        return accessControlLogRepository.findAll();
    }

    public AccessControlLog updateAccessControlLog(String id, AccessControlLog log) {
        if (accessControlLogRepository.existsById(id)) {
            log.setId(id);
            return accessControlLogRepository.save(log);
        }
        return null;
    }

    public void deleteAccessControlLog(String id) {
        accessControlLogRepository.deleteById(id);
    }
}
