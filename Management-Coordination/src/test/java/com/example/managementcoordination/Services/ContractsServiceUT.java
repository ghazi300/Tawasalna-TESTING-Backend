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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractsServiceUT {

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractsService contractsService;

    private Contracts contract1;
    private Contracts contract2;

    @BeforeEach
    void setUp() {
        contract1 = new Contracts("1", "Contract A", Contracts.contractType.Residency, "Description A", new Date(), new Date(), "Outcome A", Contracts.ContractStatus.SIGNED);
        contract2 = new Contracts("2", "Contract B", Contracts.contractType.Parcking, "Description B", new Date(), new Date(), "Outcome B", Contracts.ContractStatus.IN_PROGRESS);
    }

    @Test
    void getAllContracts() {
        // Given
        List<Contracts> contracts = Arrays.asList(contract1, contract2);
        when(contractRepository.findAll()).thenReturn(contracts);

        // When
        List<Contracts> result = contractsService.getAllContracts();

        // Then
        assertEquals(2, result.size());
        assertEquals(contract1, result.get(0));
        assertEquals(contract2, result.get(1));
    }

    @Test
    void getContractById() {
        // Given
        when(contractRepository.findById("1")).thenReturn(Optional.of(contract1));

        // When
        Optional<Contracts> result = contractsService.getContractById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals(contract1, result.get());
    }

    @Test
    void saveContract() {
        // Given
        when(contractRepository.save(contract1)).thenReturn(contract1);

        // When
        Contracts result = contractsService.saveContract(contract1);

        // Then
        assertEquals(contract1, result);
        verify(contractRepository, times(1)).save(contract1);
    }

    @Test
    void deleteContract() {
        // When
        contractsService.deleteContract("1");

        // Then
        verify(contractRepository, times(1)).deleteById("1");
    }
}
