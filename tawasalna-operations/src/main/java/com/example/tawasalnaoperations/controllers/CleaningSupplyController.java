package com.example.tawasalnaoperations.controllers;

import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.entities.InventoryItem;
import com.example.tawasalnaoperations.services.CleaningSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supply")
@CrossOrigin(origins = "http://localhost:4200")

public class CleaningSupplyController {

    @Autowired
    private CleaningSupplyService cleaningSupplyService;

    @PostMapping
    public ResponseEntity<CleaningSupply> createSupply(@RequestBody CleaningSupply supply) {
        CleaningSupply createdSupply = cleaningSupplyService.createSupply(supply);
        return ResponseEntity.ok(createdSupply);
    }

    @GetMapping("/{supplyId}")
    public ResponseEntity<CleaningSupply> getSupplyById(@PathVariable String supplyId) {
        Optional<CleaningSupply> supply = cleaningSupplyService.getSupplyById(supplyId);
        return supply.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<CleaningSupply>> getAllSupply() {
        List<CleaningSupply> supply = cleaningSupplyService.getAllSupply();
        return ResponseEntity.ok(supply);
    }

    @PutMapping("/{supplyId}")
    public ResponseEntity<CleaningSupply> updateSupply(@PathVariable String supplyId, @RequestBody CleaningSupply supply) {
        CleaningSupply updatedSupply = cleaningSupplyService.updateSupply(supplyId, supply);
        return updatedSupply != null ? ResponseEntity.ok(updatedSupply) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{supplyId}")
    public ResponseEntity<Void> deleteSupply(@PathVariable String supplyId) {
        cleaningSupplyService.deleteSupply(supplyId);
        return ResponseEntity.noContent().build();
    }
}
