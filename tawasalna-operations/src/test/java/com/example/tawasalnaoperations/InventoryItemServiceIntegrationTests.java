package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.InventoryItem;
import com.example.tawasalnaoperations.repositories.InventoryItemRepository;
import com.example.tawasalnaoperations.services.InventoryItemService;
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
public class InventoryItemServiceIntegrationTests {

    @InjectMocks
    private InventoryItemService inventoryItemService;

    @Mock
    private InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    public void setUp() {
        inventoryItemRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    public void testCreateItem() {
        InventoryItem item = new InventoryItem();
        item.setItemId("1");
        item.setItemName("Hammer");

        when(inventoryItemRepository.save(item)).thenReturn(item);

        InventoryItem created = inventoryItemService.createItem(item);

        assertNotNull(created);
        assertEquals("1", created.getItemId());
        assertEquals("Hammer", created.getItemName());
        verify(inventoryItemRepository, times(1)).save(item);
    }

    @Test
    public void testGetItemById() {
        InventoryItem item = new InventoryItem();
        item.setItemId("1");
        item.setItemName("Hammer");

        when(inventoryItemRepository.findById("1")).thenReturn(Optional.of(item));

        Optional<InventoryItem> found = inventoryItemService.getItemById("1");

        assertTrue(found.isPresent());
        assertEquals("Hammer", found.get().getItemName());
        verify(inventoryItemRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateItem() {
        InventoryItem item = new InventoryItem();
        item.setItemId("1");
        item.setItemName("Hammer");
        item.setQuantity(10);
        item.setCategory("Tools");

        InventoryItem updatedItem = new InventoryItem();
        updatedItem.setItemName("Screwdriver");
        updatedItem.setQuantity(20);
        updatedItem.setCategory("Tools");

        when(inventoryItemRepository.findById("1")).thenReturn(Optional.of(item));
        when(inventoryItemRepository.save(item)).thenReturn(updatedItem);

        InventoryItem updated = inventoryItemService.updateItem("1", updatedItem);

        assertNotNull(updated);
        assertEquals("Screwdriver", updated.getItemName());
        assertEquals(20, updated.getQuantity());
        assertEquals("Tools", updated.getCategory());
        verify(inventoryItemRepository, times(1)).findById("1");
        verify(inventoryItemRepository, times(1)).save(item);
    }

    @Test
    public void testGetAllInventory() {
        InventoryItem item1 = new InventoryItem();
        item1.setItemId("1");
        item1.setItemName("Hammer");

        InventoryItem item2 = new InventoryItem();
        item2.setItemId("2");
        item2.setItemName("Screwdriver");

        List<InventoryItem> items = Arrays.asList(item1, item2);

        when(inventoryItemRepository.findAll()).thenReturn(items);

        List<InventoryItem> found = inventoryItemService.getAllInventory();

        assertEquals(2, found.size());
        assertEquals("Hammer", found.get(0).getItemName());
        assertEquals("Screwdriver", found.get(1).getItemName());
        verify(inventoryItemRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteItem() {
        String itemId = "1";

        doNothing().when(inventoryItemRepository).deleteById(itemId);

        inventoryItemService.deleteItem(itemId);

        verify(inventoryItemRepository, times(1)).deleteById(itemId);
    }
}
