package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.EquipmentController;
import com.tawasalna.MaintenanceAgent.models.Equipment;
import com.tawasalna.MaintenanceAgent.repos.EquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class EquipmentControllerTest {

    @InjectMocks
    private EquipmentController equipmentController;

    @Mock
    private EquipmentRepository equipmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEquipmentById() {
        String id = "1";
        Equipment equipment = new Equipment(id, "Drill", "Tools", 10, 5, "Supplier", LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(100), "Location", "Active", "12345", "No comments");
        when(equipmentRepository.findById(id)).thenReturn(Optional.of(equipment));

        ResponseEntity<Equipment> response = equipmentController.getEquipmentById(id);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(equipment, response.getBody());
    }

    @Test
    void testGetEquipmentByIdNotFound() {
        String id = "1";
        when(equipmentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Equipment> response = equipmentController.getEquipmentById(id);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllEquipments() {
        Equipment equipment1 = new Equipment("1", "Drill", "Tools", 10, 5, "Supplier", LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(100), "Location", "Active", "12345", "No comments");
        Equipment equipment2 = new Equipment("2", "Hammer", "Tools", 15, 10, "Supplier", LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(50), "Location", "Active", "67890", "No comments");
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment1);
        equipmentList.add(equipment2);

        when(equipmentRepository.findAll()).thenReturn(equipmentList);

        ResponseEntity<List<Equipment>> response = equipmentController.getAllEquipments();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(equipmentList, response.getBody());
    }

    @Test
    void testCreateEquipment() {
        Equipment equipment = new Equipment("1", "Drill", "Tools", 10, 5, "Supplier", LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(100), "Location", "Active", "12345", "No comments");
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        Equipment createdEquipment = equipmentController.createEquipment(equipment);
        assertEquals(equipment, createdEquipment);
        verify(equipmentRepository, times(1)).save(equipment);
    }

    @Test
    void testUpdateEquipment() {
        String id = "1";
        Equipment existingEquipment = new Equipment(id, "Drill", "Tools", 10, 5, "Supplier", LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(100), "Location", "Active", "12345", "No comments");
        Equipment equipmentDetails = new Equipment(id, "Drill Pro", "Tools", 15, 5, "Supplier", LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(120), "New Location", "Active", "12345", "Updated comments");

        when(equipmentRepository.findById(id)).thenReturn(Optional.of(existingEquipment));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipmentDetails);

        ResponseEntity<Equipment> response = equipmentController.updateEquipment(id, equipmentDetails);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(equipmentDetails, response.getBody());
    }

    @Test
    void testUpdateEquipmentNotFound() {
        String id = "1";
        Equipment equipmentDetails = new Equipment(id, "Drill Pro", "Tools", 15, 5, "Supplier", LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(120), "New Location", "Active", "12345", "Updated comments");

        when(equipmentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Equipment> response = equipmentController.updateEquipment(id, equipmentDetails);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteEquipment() {
        String id = "1";
        when(equipmentRepository.findById(id)).thenReturn(Optional.of(new Equipment()));

        ResponseEntity<Void> response = equipmentController.deleteEquipment(id);
        assertEquals(200, response.getStatusCodeValue());
        verify(equipmentRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteEquipmentNotFound() {
        String id = "1";
        when(equipmentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = equipmentController.deleteEquipment(id);
        assertEquals(404, response.getStatusCodeValue());
    }
}
