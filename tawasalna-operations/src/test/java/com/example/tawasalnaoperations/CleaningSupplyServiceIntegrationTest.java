package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.repositories.CleaningSupplyRepository;
import com.example.tawasalnaoperations.services.CleaningSupplyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CleaningSupplyServiceIntegrationTest {

    @Autowired
    private CleaningSupplyService cleaningSupplyService;

    @Autowired
    private CleaningSupplyRepository cleaningSupplyRepository;

    @AfterEach
    void tearDown() {
        cleaningSupplyRepository.deleteAll();
    }

    @Test
    void createAndGetSupply() {
        CleaningSupply supply = new CleaningSupply();
        supply.setSupplyId("1");
        supply.setItemName("Soap");

        cleaningSupplyService.createSupply(supply);
        Optional<CleaningSupply> found = cleaningSupplyService.getSupplyById("1");

        assertTrue(found.isPresent());
        assertEquals("Soap", found.get().getItemName());
    }

    @Test
    void updateSupply() {
        CleaningSupply supply = new CleaningSupply();
        supply.setSupplyId("1");
        supply.setItemName("Soap");

        cleaningSupplyService.createSupply(supply);

        supply.setItemName("Updated Soap");
        CleaningSupply updated = cleaningSupplyService.updateSupply("1", supply);

        assertEquals("Updated Soap", updated.getItemName());
    }

    @Test
    void deleteSupply() {
        CleaningSupply supply = new CleaningSupply();
        supply.setSupplyId("1");
        supply.setItemName("Soap");

        cleaningSupplyService.createSupply(supply);
        cleaningSupplyService.deleteSupply("1");

        Optional<CleaningSupply> found = cleaningSupplyService.getSupplyById("1");
        assertFalse(found.isPresent());
    }
}
