package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.repositories.CleaningSupplyRepository;
import com.example.tawasalnaoperations.services.CleaningSupplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class CleaningSupplyServiceIntegrationTest {

    @InjectMocks
    private CleaningSupplyService cleaningSupplyService;

    @Mock
    private CleaningSupplyRepository cleaningSupplyRepository;

    @BeforeEach
    public void setUp() {
        cleaningSupplyRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    public void testCreateSupply() {
        CleaningSupply supply = new CleaningSupply();
        supply.setSupplyId("1");
        supply.setItemName("Disinfectant");

        when(cleaningSupplyRepository.save(supply)).thenReturn(supply);

        CleaningSupply created = cleaningSupplyService.createSupply(supply);

        assertNotNull(created);
        assertEquals("1", created.getSupplyId());
        assertEquals("Disinfectant", created.getItemName());
        verify(cleaningSupplyRepository, times(1)).save(supply);
    }

    @Test
    public void testGetSupplyById() {
        CleaningSupply supply = new CleaningSupply();
        supply.setSupplyId("1");
        supply.setItemName("Disinfectant");

        when(cleaningSupplyRepository.findById("1")).thenReturn(Optional.of(supply));

        Optional<CleaningSupply> found = cleaningSupplyService.getSupplyById("1");

        assertTrue(found.isPresent());
        assertEquals("Disinfectant", found.get().getItemName());
        verify(cleaningSupplyRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateSupply() {
        CleaningSupply supply = new CleaningSupply();
        supply.setSupplyId("1");
        supply.setItemName("Disinfectant");

        when(cleaningSupplyRepository.existsById("1")).thenReturn(true);
        when(cleaningSupplyRepository.save(supply)).thenReturn(supply);

        CleaningSupply updated = cleaningSupplyService.updateSupply("1", supply);

        assertNotNull(updated);
        assertEquals("Disinfectant", updated.getItemName());
        verify(cleaningSupplyRepository, times(1)).existsById("1");
        verify(cleaningSupplyRepository, times(1)).save(supply);
    }

    @Test
    public void testGetAllSupply() {
        CleaningSupply supply1 = new CleaningSupply();
        supply1.setSupplyId("1");
        supply1.setItemName("Disinfectant");

        CleaningSupply supply2 = new CleaningSupply();
        supply2.setSupplyId("2");
        supply2.setItemName("Bleach");

        List<CleaningSupply> supplies = Arrays.asList(supply1, supply2);

        when(cleaningSupplyRepository.findAll()).thenReturn(supplies);

        List<CleaningSupply> found = cleaningSupplyService.getAllSupply();

        assertEquals(2, found.size());
        assertEquals("Disinfectant", found.get(0).getItemName());
        assertEquals("Bleach", found.get(1).getItemName());
        verify(cleaningSupplyRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteSupply() {
        String supplyId = "1";

        doNothing().when(cleaningSupplyRepository).deleteById(supplyId);

        cleaningSupplyService.deleteSupply(supplyId);

        verify(cleaningSupplyRepository, times(1)).deleteById(supplyId);
    }
}
