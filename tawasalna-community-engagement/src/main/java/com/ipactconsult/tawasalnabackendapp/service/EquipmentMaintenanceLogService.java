package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.exceptions.EquipmentNotFoundException;
import com.ipactconsult.tawasalnabackendapp.models.EquipmentMaintenanceLog;
import com.ipactconsult.tawasalnabackendapp.payload.request.EquipmentMaintenanceLogRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipmentMaintenanceLogResponse;
import com.ipactconsult.tawasalnabackendapp.repository.EquipmentMaintenanceLogRepository;
import com.ipactconsult.tawasalnabackendapp.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class EquipmentMaintenanceLogService implements IEquipmentMaintenanceLogService{
    private final EquipmentMaintenanceLogRepository equipmentMaintenanceLogRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentMaintenanceLogMapper equipmentMaintenanceLogMapper;

    @Override
    public String save(EquipmentMaintenanceLogRequest request) {
        equipmentRepository.findById(request.getEquipmentId())
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment with ID " +request.getEquipmentId() + " not found"));
        EquipmentMaintenanceLog maintenanceLog=equipmentMaintenanceLogMapper.toEquipementMaintenance(request);
        return equipmentMaintenanceLogRepository.save(maintenanceLog).getId();
    }

    @Override
    public List<EquipmentMaintenanceLogResponse> getAllMaintenanceLogs() {
        List<EquipmentMaintenanceLog> maintenanceLogs = equipmentMaintenanceLogRepository.findAll();
        return maintenanceLogs.stream()
                .map(equipmentMaintenanceLogMapper::mapToResponse)
                .collect(Collectors.toList());
    }
}
