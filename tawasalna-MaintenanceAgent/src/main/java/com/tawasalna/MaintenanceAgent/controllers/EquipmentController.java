package com.tawasalna.MaintenanceAgent.controllers;

import com.tawasalna.MaintenanceAgent.models.Equipment;
import com.tawasalna.MaintenanceAgent.repos.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable String id) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        return equipment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAll();
        return ResponseEntity.ok(equipments);
    }

    @PostMapping
    public Equipment createEquipment(@RequestBody Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable String id, @RequestBody Equipment equipmentDetails) {
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(id);

        if (equipmentOptional.isPresent()) {
            Equipment equipment = equipmentOptional.get();
            equipment.setEquipmentName(equipmentDetails.getEquipmentName());
            equipment.setCategory(equipmentDetails.getCategory());
            equipment.setQuantityAvailable(equipmentDetails.getQuantityAvailable());
            equipment.setMinimumRequired(equipmentDetails.getMinimumRequired());
            equipment.setSupplier(equipmentDetails.getSupplier());
            equipment.setPurchaseDate(equipmentDetails.getPurchaseDate());
            equipment.setLastReceivedDate(equipmentDetails.getLastReceivedDate());
            equipment.setUnitPrice(equipmentDetails.getUnitPrice());
            equipment.setLocation(equipmentDetails.getLocation());
            equipment.setStatus(equipmentDetails.getStatus());
            equipment.setSerialNumber(equipmentDetails.getSerialNumber());
            equipment.setComments(equipmentDetails.getComments());

            Equipment updatedEquipment = equipmentRepository.save(equipment);
            return ResponseEntity.ok(updatedEquipment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable String id) {
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(id);

        if (equipmentOptional.isPresent()) {
            equipmentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
