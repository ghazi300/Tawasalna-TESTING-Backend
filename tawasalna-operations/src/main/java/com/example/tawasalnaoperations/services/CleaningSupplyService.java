package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.entities.InventoryItem;
import com.example.tawasalnaoperations.repositories.CleaningSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CleaningSupplyService {

    @Autowired
    private CleaningSupplyRepository cleaningSupplyRepository;

    public CleaningSupply createSupply(CleaningSupply supply) {
        return cleaningSupplyRepository.save(supply);
    }

    public Optional<CleaningSupply> getSupplyById(String supplyId) {
        return cleaningSupplyRepository.findById(supplyId);
    }

    public CleaningSupply updateSupply(String supplyId, CleaningSupply supply) {
        if (cleaningSupplyRepository.existsById(supplyId)) {
            supply.setSupplyId(supplyId);
            return cleaningSupplyRepository.save(supply);
        } else {
            return null; // Or throw an exception
        }
    }
    public List<CleaningSupply> getAllSupply() {
        return cleaningSupplyRepository.findAll();
    }


    public void deleteSupply(String supplyId) {
        cleaningSupplyRepository.deleteById(supplyId);
    }
}

