package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.InventoryItem;
import com.example.tawasalnaoperations.services.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:4200")

public class InventoryItemController {

    @Autowired
    private InventoryItemService service;

    @PostMapping
    public ResponseEntity<InventoryItem> createItem(@RequestBody InventoryItem item) {
        InventoryItem createdItem = service.createItem(item);
        return ResponseEntity.ok(createdItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable String id) {
        Optional<InventoryItem> item = service.getItemById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventory() {
        List<InventoryItem> reports = service.getAllInventory();
        return ResponseEntity.ok(reports);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable String id, @RequestBody InventoryItem updatedItem) {
        InventoryItem item = service.updateItem(id, updatedItem);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}