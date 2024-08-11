package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.EquipmentMaintenance;
import com.example.tawasalnaoperations.repositories.EquipmentMaintenanceRepository;
import com.example.tawasalnaoperations.services.EquipmentMaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringJUnitConfig
public class EquipmentMaintenanceServiceIntegrationTest {

    @InjectMocks
    private EquipmentMaintenanceService equipmentMaintenanceService;

    @Autowired
    private EquipmentMaintenanceRepository equipmentMaintenanceRepository;

    @BeforeEach
    public void setUp() {
        equipmentMaintenanceRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    public void testCreateEquipment() {
        EquipmentMaintenance maintenance = new EquipmentMaintenance();
        maintenance.setMaintenanceId("1");
        maintenance.setEquipmentId("Drill");

        when(equipmentMaintenanceRepository.save(maintenance)).thenReturn(maintenance);

        EquipmentMaintenance created = equipmentMaintenanceService.createEquipment(maintenance);

        assertNotNull(created);
        assertEquals("1", created.getMaintenanceId());
        assertEquals("Drill", created.getEquipmentId());
        verify(equipmentMaintenanceRepository, times(1)).save(maintenance);
    }

    @Test
    public void testGetEquipmentById() {
        EquipmentMaintenance maintenance = new EquipmentMaintenance();
        maintenance.setMaintenanceId("1");
        maintenance.setEquipmentId("Drill");

        when(equipmentMaintenanceRepository.findById("1")).thenReturn(Optional.of(maintenance));

        Optional<EquipmentMaintenance> found = equipmentMaintenanceService.getEquipmentById("1");

        assertTrue(found.isPresent());
        assertEquals("Drill", found.get().getEquipmentId());
        verify(equipmentMaintenanceRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateEquipment() {
        EquipmentMaintenance maintenance = new EquipmentMaintenance();
        maintenance.setMaintenanceId("1");
        maintenance.setEquipmentId("Drill");

        when(equipmentMaintenanceRepository.existsById("1")).thenReturn(true);
        when(equipmentMaintenanceRepository.save(maintenance)).thenReturn(maintenance);

        EquipmentMaintenance updated = equipmentMaintenanceService.updateEquipment("1", maintenance);

        assertNotNull(updated);
        assertEquals("Drill", updated.getEquipmentId());
        verify(equipmentMaintenanceRepository, times(1)).existsById("1");
        verify(equipmentMaintenanceRepository, times(1)).save(maintenance);
    }

    @Test
    public void testGetAllEquipments() {
        EquipmentMaintenance maintenance1 = new EquipmentMaintenance();
        maintenance1.setMaintenanceId("1");
        maintenance1.setEquipmentId("Drill");

        EquipmentMaintenance maintenance2 = new EquipmentMaintenance();
        maintenance2.setMaintenanceId("2");
        maintenance2.setEquipmentId("Saw");

        List<EquipmentMaintenance> equipments = Arrays.asList(maintenance1, maintenance2);

        when(equipmentMaintenanceRepository.findAll()).thenReturn(equipments);

        List<EquipmentMaintenance> found = equipmentMaintenanceService.getAllEquipments();

        assertEquals(2, found.size());
        assertEquals("Drill", found.get(0).getEquipmentId());
        assertEquals("Saw", found.get(1).getEquipmentId());
        verify(equipmentMaintenanceRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteEquipment() {
        String maintenanceId = "1";

        doNothing().when(equipmentMaintenanceRepository).deleteById(maintenanceId);

        equipmentMaintenanceService.deleteEquipment(maintenanceId);

        verify(equipmentMaintenanceRepository, times(1)).deleteById(maintenanceId);
    }
}
