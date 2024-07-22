package com.tawasalnasecuritysafety.controllers;

import com.tawasalnasecuritysafety.models.AccessControlLog;
import com.tawasalnasecuritysafety.services.AccessControlLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/access-control-logs")
@CrossOrigin("*")

public class AccessControlLogController {
    @Autowired
    private AccessControlLogService accessControlLogService;

    @PostMapping
    public AccessControlLog createAccessControlLog(@RequestBody AccessControlLog log) {
        return accessControlLogService.createAccessControlLog(log);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessControlLog> getAccessControlLogById(@PathVariable String id) {
        Optional<AccessControlLog> log = accessControlLogService.getAccessControlLogById(id);
        return log.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AccessControlLog> getAllAccessControlLogs() {
        return accessControlLogService.getAllAccessControlLogs();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessControlLog> updateAccessControlLog(@PathVariable String id, @RequestBody AccessControlLog log) {
        AccessControlLog updatedLog = accessControlLogService.updateAccessControlLog(id, log);
        return updatedLog != null ? ResponseEntity.ok(updatedLog) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessControlLog(@PathVariable String id) {
        accessControlLogService.deleteAccessControlLog(id);
        return ResponseEntity.noContent().build();
    }
}
