package com.example.managementcoordination.controllers;

import com.example.managementcoordination.Services.ContractsService;
import com.example.managementcoordination.entities.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "http://localhost:4200")

public class ContractsController {

    @Autowired
    private ContractsService contractsService;

    @GetMapping
    public ResponseEntity<List<Contracts>> getAllContracts() {
        List<Contracts> contracts = contractsService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contracts> getContractById(@PathVariable String id) {
        Optional<Contracts> contract = contractsService.getContractById(id);
        return contract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contracts> createContract(@RequestBody Contracts contract) {
        Contracts savedContract = contractsService.saveContract(contract);
        return ResponseEntity.ok(savedContract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contracts> updateContract(@PathVariable String id, @RequestBody Contracts updatedContract) {
        Optional<Contracts> existingContract = contractsService.getContractById(id);
        if (existingContract.isPresent()) {
            updatedContract.setId(id); // Ensure the ID is retained
            Contracts savedContract = contractsService.saveContract(updatedContract);
            return ResponseEntity.ok(savedContract);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable String id) {
        contractsService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}
