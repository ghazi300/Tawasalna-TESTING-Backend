package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.repositories.CleaningSupplyRepository;
import com.example.tawasalnaoperations.services.CleaningSupplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CleaningSupplyServiceTest {

    @Mock
    private CleaningSupplyRepository cleaningSupplyRepository;

    @InjectMocks
    private CleaningSupplyService cleaningSupplyService;

    private CleaningSupply supply;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        supply = new CleaningSupply();
        supply.setSupplyId("1");
        supply.setItemName("Soap");
    }

    @Test
    void createSupply() {
        when(cleaningSupplyRepository.save(supply)).thenReturn(supply);
        CleaningSupply created = cleaningSupplyService.createSupply(supply);
        assertNotNull(created);
        assertEquals(supply.getSupplyId(), created.getSupplyId());
    }

    @Test
    void getSupplyById() {
        when(cleaningSupplyRepository.findById("1")).thenReturn(Optional.of(supply));
        Optional<CleaningSupply> found = cleaningSupplyService.getSupplyById("1");
        assertTrue(found.isPresent());
        assertEquals("Soap", found.get().getItemName());
    }

    @Test
    void updateSupply() {
        when(cleaningSupplyRepository.existsById("1")).thenReturn(true);
        when(cleaningSupplyRepository.save(supply)).thenReturn(supply);
        CleaningSupply updated = cleaningSupplyService.updateSupply("1", supply);
        assertNotNull(updated);
        assertEquals("1", updated.getSupplyId());
    }

    @Test
    void deleteSupply() {
        doNothing().when(cleaningSupplyRepository).deleteById("1");
        cleaningSupplyService.deleteSupply("1");
        verify(cleaningSupplyRepository, times(1)).deleteById("1");
    }
}

