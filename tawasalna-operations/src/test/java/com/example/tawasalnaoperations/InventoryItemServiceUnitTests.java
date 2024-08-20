package com.example.tawasalnaoperations;


import com.example.tawasalnaoperations.entities.EquipmentMaintenance;
import com.example.tawasalnaoperations.entities.InventoryItem;
import com.example.tawasalnaoperations.repositories.InventoryItemRepository;
import com.example.tawasalnaoperations.services.InventoryItemService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryItemServiceUnitTests {

    @Mock
    private InventoryItemRepository repository;

    @InjectMocks
    private InventoryItemService service;
    private InventoryItem inventory;

    public InventoryItemServiceUnitTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateItem() {
        InventoryItem item = new InventoryItem();
        item.setItemName("Test Item");
        item.setQuantity(10);
        item.setCategory("Category1");

        when(repository.save(any(InventoryItem.class))).thenReturn(item);

        InventoryItem createdItem = service.createItem(item);
        assertNotNull(createdItem);
        assertEquals("Test Item", createdItem.getItemName());
        verify(repository, times(1)).save(item);
    }

    @Test
    public void testGetItemById() {
        InventoryItem item = new InventoryItem();
        item.setItemName("Test Item");
        item.setQuantity(10);
        item.setCategory("Category1");

        when(repository.findById("1")).thenReturn(Optional.of(item));

        Optional<InventoryItem> foundItem = service.getItemById("1");
        assertTrue(foundItem.isPresent());
        assertEquals("Test Item", foundItem.get().getItemName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void updateItem() {
        // Create existing item and the updated item
        InventoryItem existingItem = new InventoryItem();
        existingItem.setItemId("1");
        existingItem.setItemName("Old Item");
        existingItem.setQuantity(5);
        existingItem.setCategory("Old Category");

        InventoryItem updatedItem = new InventoryItem();
        updatedItem.setItemId("1");
        updatedItem.setItemName("Updated Item");
        updatedItem.setQuantity(10);
        updatedItem.setCategory("Updated Category");

        // Set up mock behavior
        when(repository.findById("1")).thenReturn(Optional.of(existingItem));
        when(repository.save(any(InventoryItem.class))).thenReturn(updatedItem);

        // Call the service method
        InventoryItem result = service.updateItem("1", updatedItem);

        // Verify the results
        assertNotNull(result);
        assertEquals("1", result.getItemId());
        assertEquals("Updated Item", result.getItemName());
        assertEquals(10, result.getQuantity());
        assertEquals("Updated Category", result.getCategory());

        // Capture and verify repository interactions
        ArgumentCaptor<InventoryItem> captor = ArgumentCaptor.forClass(InventoryItem.class);
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(captor.capture());

        InventoryItem savedItem = captor.getValue();
        assertEquals("1", savedItem.getItemId());
        assertEquals("Updated Item", savedItem.getItemName());
        assertEquals(10, savedItem.getQuantity());
        assertEquals("Updated Category", savedItem.getCategory());
    }

    @Test
    public void testDeleteItem() {
        doNothing().when(repository).deleteById("1");

        service.deleteItem("1");

        verify(repository, times(1)).deleteById("1");
    }
}

