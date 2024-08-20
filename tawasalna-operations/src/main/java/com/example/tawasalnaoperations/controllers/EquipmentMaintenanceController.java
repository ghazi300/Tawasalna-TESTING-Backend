package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.entities.EquipmentMaintenance;
import com.example.tawasalnaoperations.services.EquipmentMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipment")
@CrossOrigin(origins = "http://localhost:4200")

public class EquipmentMaintenanceController {

    @Autowired
    private EquipmentMaintenanceService equipmentMaintenanceService;

    @PostMapping
    public ResponseEntity<EquipmentMaintenance> createEquipment(@RequestBody EquipmentMaintenance maintenance) {
        EquipmentMaintenance createdMaintenance = equipmentMaintenanceService.createEquipment(maintenance);
        return ResponseEntity.ok(createdMaintenance);
    }
    @GetMapping
    public ResponseEntity<List<EquipmentMaintenance>> getAllEquipments() {
        List<EquipmentMaintenance> supply = equipmentMaintenanceService.getAllEquipments();
        return ResponseEntity.ok(supply);
    }

    @GetMapping("/{maintenanceId}")
    public ResponseEntity<EquipmentMaintenance> getEquipmentById(@PathVariable String maintenanceId) {
        Optional<EquipmentMaintenance> maintenance = equipmentMaintenanceService.getEquipmentById(maintenanceId);
        return maintenance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{maintenanceId}")
    public ResponseEntity<EquipmentMaintenance> updateEquipment(@PathVariable String maintenanceId, @RequestBody EquipmentMaintenance maintenance) {
        EquipmentMaintenance updatedMaintenance = equipmentMaintenanceService.updateEquipment(maintenanceId, maintenance);
        return updatedMaintenance != null ? ResponseEntity.ok(updatedMaintenance) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{maintenanceId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable String maintenanceId) {
        equipmentMaintenanceService.deleteEquipment(maintenanceId);
        return ResponseEntity.noContent().build();
    }
}
