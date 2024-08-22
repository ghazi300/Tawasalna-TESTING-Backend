package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.entities.EquipmentMaintenance;
import com.example.tawasalnaoperations.repositories.EquipmentMaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentMaintenanceService {

    @Autowired
    private EquipmentMaintenanceRepository equipmentMaintenanceRepository;

    public EquipmentMaintenance createEquipment(EquipmentMaintenance maintenance) {
        return equipmentMaintenanceRepository.save(maintenance);
    }

    public Optional<EquipmentMaintenance> getEquipmentById(String maintenanceId) {
        return equipmentMaintenanceRepository.findById(maintenanceId);
    }

    public EquipmentMaintenance updateEquipment(String maintenanceId, EquipmentMaintenance maintenance) {
        if (equipmentMaintenanceRepository.existsById(maintenanceId)) {
            maintenance.setMaintenanceId(maintenanceId);
            return equipmentMaintenanceRepository.save(maintenance);
        } else {
            return null; // Or throw an exception
        }
    }
    public List<EquipmentMaintenance> getAllEquipments() {
        return equipmentMaintenanceRepository.findAll();
    }


    public void deleteEquipment(String maintenanceId) {
        equipmentMaintenanceRepository.deleteById(maintenanceId);
    }
}
