package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.payload.request.EquipmentMaintenanceLogRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipmentMaintenanceLogResponse;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.service.IEquipmentMaintenanceLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("equipmentmaintenancelog")

public class EquipmentMaintenanceLogController {
    private final IEquipmentMaintenanceLogService service;
    @PostMapping
    public ResponseEntity<String> createMaintenanceLog(@Valid @RequestBody EquipmentMaintenanceLogRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentMaintenanceLogResponse>> getAllMaintenanceLogs() {
        List<EquipmentMaintenanceLogResponse> logs = service.getAllMaintenanceLogs();
        return ResponseEntity.ok(logs);

    }
}
