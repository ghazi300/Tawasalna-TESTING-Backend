package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Contracts;
import com.example.managementcoordination.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ContractsService {
    @Autowired
    private ContractRepository contractsRepository;

    public List<Contracts> getAllContracts() {
        return contractsRepository.findAll();
    }

    public Optional<Contracts> getContractById(String id) {
        return contractsRepository.findById(id);
    }

    public Contracts saveContract(Contracts contract) {
        return contractsRepository.save(contract);
    }

    public void deleteContract(String id) {
        contractsRepository.deleteById(id);
    }
}
