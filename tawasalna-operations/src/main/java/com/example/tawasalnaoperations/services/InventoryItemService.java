package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.InventoryItem;
import com.example.tawasalnaoperations.entities.PestControlReport;
import com.example.tawasalnaoperations.repositories.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {
    @Autowired
    private InventoryItemRepository repository;

    public InventoryItem createItem(InventoryItem item) {
        return repository.save(item);
    }

    public Optional<InventoryItem> getItemById(String id) {
        return repository.findById(id);
    }
    public List<InventoryItem> getAllInventory() {
        return repository.findAll();
    }

    public InventoryItem updateItem(String id, InventoryItem updatedItem) {
        return repository.findById(id)
                .map(item -> {
                    item.setItemName(updatedItem.getItemName());
                    item.setQuantity(updatedItem.getQuantity());
                    item.setCategory(updatedItem.getCategory());
                    return repository.save(item);
                })
                .orElse(null);
    }

    public void deleteItem(String id) {
        repository.deleteById(id);
    }
}
