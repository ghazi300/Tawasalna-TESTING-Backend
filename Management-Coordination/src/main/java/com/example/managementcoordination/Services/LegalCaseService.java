package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.LegalCase;
import com.example.managementcoordination.repositories.LegalCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LegalCaseService {
    @Autowired
    private LegalCaseRepository LegalCaseRepository ;
    public List<LegalCase> getAllCases() {
        return LegalCaseRepository.findAll();
    }

    public Optional<LegalCase> getCaseById(String id) {
        return LegalCaseRepository.findById(id);
    }

    public LegalCase saveCase(LegalCase legalCase) {
        return LegalCaseRepository.save(legalCase);
    }

    public void deleteCase(String id) {
        LegalCaseRepository.deleteById(id);
    }

}
