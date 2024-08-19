package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.EquipmentMaintenance;
import com.example.tawasalnaoperations.repositories.EquipmentMaintenanceRepository;
import com.example.tawasalnaoperations.services.EquipmentMaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipmentMaintenanceServiceTest {

    @Mock
    private EquipmentMaintenanceRepository equipmentMaintenanceRepository;

    @InjectMocks
    private EquipmentMaintenanceService equipmentMaintenanceService;

    private EquipmentMaintenance maintenance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        maintenance = new EquipmentMaintenance();
        maintenance.setMaintenanceId("1");
        maintenance.setEquipmentId("Lawnmower");
    }

    @Test
    void createEquipment() {
        when(equipmentMaintenanceRepository.save(maintenance)).thenReturn(maintenance);
        EquipmentMaintenance created = equipmentMaintenanceService.createEquipment(maintenance);
        assertNotNull(created);
        assertEquals(maintenance.getMaintenanceId(), created.getMaintenanceId());
    }

    @Test
    void getEquipmentById() {
        when(equipmentMaintenanceRepository.findById("1")).thenReturn(Optional.of(maintenance));
        Optional<EquipmentMaintenance> found = equipmentMaintenanceService.getEquipmentById("1");
        assertTrue(found.isPresent());
        assertEquals("Lawnmower", found.get().getEquipmentId());
    }

    @Test
    void updateEquipment() {
        when(equipmentMaintenanceRepository.existsById("1")).thenReturn(true);
        when(equipmentMaintenanceRepository.save(maintenance)).thenReturn(maintenance);
        EquipmentMaintenance updated = equipmentMaintenanceService.updateEquipment("1", maintenance);
        assertNotNull(updated);
        assertEquals("1", updated.getMaintenanceId());
    }

    @Test
    void deleteEquipment() {
        doNothing().when(equipmentMaintenanceRepository).deleteById("1");
        equipmentMaintenanceService.deleteEquipment("1");
        verify(equipmentMaintenanceRepository, times(1)).deleteById("1");
    }
}
