package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.EquipmentMaintenanceLog;
import com.ipactconsult.tawasalnabackendapp.payload.request.EquipmentMaintenanceLogRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipmentMaintenanceLogResponse;
import org.springframework.stereotype.Service;

@Service
public class EquipmentMaintenanceLogMapper {
    public EquipmentMaintenanceLog toEquipementMaintenance(EquipmentMaintenanceLogRequest request) {
        return EquipmentMaintenanceLog.builder()
                .equipmentId(request.getEquipmentId())
                .technicianId(request.getTechnicianId())
                .nextScheduledDate(request.getNextScheduledDate())
                .description(request.getDescription())


                .build();
    }

    public EquipmentMaintenanceLogResponse mapToResponse(EquipmentMaintenanceLog maintenanceLog) {
        return EquipmentMaintenanceLogResponse.builder()
                .id(maintenanceLog.getId())
                .equipmentId(maintenanceLog.getEquipmentId())
                .technicianId(maintenanceLog.getTechnicianId())
                .nextScheduledDate(maintenanceLog.getNextScheduledDate())
                .maintenanceDate(maintenanceLog.getMaintenanceDate())
                .description(maintenanceLog.getDescription())
                .build();
    }
}
