package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.InventoryItem;
import com.example.tawasalnaoperations.repositories.InventoryItemRepository;
import com.example.tawasalnaoperations.services.InventoryItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class InventoryItemServiceIntegrationTests {

    @Autowired
    private InventoryItemService service;

    @Autowired
    private InventoryItemRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll(); // Nettoyer la base de données avant chaque test
    }

    @Test
    public void testCreateAndRetrieveItem() {
        InventoryItem item = new InventoryItem();
        item.setItemName("Test Item");
        item.setQuantity(10);
        item.setCategory("Category1");

        InventoryItem createdItem = service.createItem(item);

        assertNotNull(createdItem);
        assertEquals("Test Item", createdItem.getItemName());

        Optional<InventoryItem> retrievedItem = service.getItemById(createdItem.getItemId());
        assertTrue(retrievedItem.isPresent());
        assertEquals("Test Item", retrievedItem.get().getItemName());
    }

    @Test
    public void testUpdateItem() {
        InventoryItem item = new InventoryItem();
        item.setItemName("Old Item");
        item.setQuantity(5);
        item.setCategory("Old Category");
        InventoryItem createdItem = service.createItem(item);

        InventoryItem updatedItem = new InventoryItem();
        updatedItem.setItemName("Updated Item");
        updatedItem.setQuantity(15);
        updatedItem.setCategory("Updated Category");

        InventoryItem result = service.updateItem(createdItem.getItemId(), updatedItem);

        assertNotNull(result);
        assertEquals("Updated Item", result.getItemName());
    }

    @Test
    public void testDeleteItem() {
        InventoryItem item = new InventoryItem();
        item.setItemName("Test Item");
        item.setQuantity(10);
        item.setCategory("Category1");
        InventoryItem createdItem = service.createItem(item);

        service.deleteItem(createdItem.getItemId());

        Optional<InventoryItem> deletedItem = service.getItemById(createdItem.getItemId());
        assertFalse(deletedItem.isPresent());
    }
}
