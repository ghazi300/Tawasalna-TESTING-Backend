package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.payload.request.EquipmentMaintenanceLogRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipmentMaintenanceLogResponse;

import java.util.List;

public interface IEquipmentMaintenanceLogService {
    String save(EquipmentMaintenanceLogRequest request);

    List<EquipmentMaintenanceLogResponse> getAllMaintenanceLogs();

    List<EquipmentMaintenanceLogResponse> getMaintenanceLogsByEquipmentId(String equipmentId);

    void deleteLog(String id);
}
