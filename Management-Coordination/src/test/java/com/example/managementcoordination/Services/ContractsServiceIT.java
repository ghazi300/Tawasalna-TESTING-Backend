package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Contracts;
import com.example.managementcoordination.repositories.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractsServiceIT {

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractsService contractsService;

    private Contracts testContract;

    @BeforeEach
    void setUp() {
        testContract = new Contracts(
                "1",
                "Contract Title",
                Contracts.contractType.Residency,
                "Description of the contract",
                new Date(),
                new Date(),
                "Negotiation Outcome",
                Contracts.ContractStatus.SIGNED
        );
    }

    @Test
    void getAllContracts() {
        when(contractRepository.findAll()).thenReturn(Arrays.asList(testContract));

        List<Contracts> contracts = contractsService.getAllContracts();

        assertFalse(contracts.isEmpty(), "Contracts list should not be empty.");
        assertEquals(1, contracts.size(), "There should be one contract in the list.");
        assertEquals(testContract, contracts.get(0), "The contract retrieved should match the test contract.");
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    void getContractById() {
        when(contractRepository.findById(testContract.getId())).thenReturn(Optional.of(testContract));

        Optional<Contracts> retrievedContract = contractsService.getContractById(testContract.getId());

        assertTrue(retrievedContract.isPresent(), "Contract should be present.");
        assertEquals(testContract, retrievedContract.get(), "The retrieved contract should match the test contract.");
        verify(contractRepository, times(1)).findById(testContract.getId());
    }

    @Test
    void saveContract() {
        when(contractRepository.save(testContract)).thenReturn(testContract);

        Contracts savedContract = contractsService.saveContract(testContract);

        assertNotNull(savedContract, "Saved contract should not be null.");
        assertEquals(testContract, savedContract, "The saved contract should match the test contract.");
        verify(contractRepository, times(1)).save(testContract);
    }

    @Test
    void deleteContract() {
        doNothing().when(contractRepository).deleteById(testContract.getId());

        contractsService.deleteContract(testContract.getId());

        verify(contractRepository, times(1)).deleteById(testContract.getId());
    }
}
