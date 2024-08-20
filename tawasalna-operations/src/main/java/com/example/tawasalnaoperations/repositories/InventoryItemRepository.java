package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryItemRepository extends MongoRepository<InventoryItem, String> {
}