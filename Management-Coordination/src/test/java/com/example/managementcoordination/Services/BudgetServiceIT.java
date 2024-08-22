package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Budget;
import com.example.managementcoordination.repositories.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceIT {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private BudgetService budgetService;

    private Budget testBudget;

    @BeforeEach
    void setUp() {
        testBudget = new Budget(
                "1",
                "2024",
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(50000),
                BigDecimal.valueOf(25000),
                BigDecimal.valueOf(25000),
                "IT Department"
        );
    }

    @Test
    void createOrUpdateBudget() {
        when(budgetRepository.save(testBudget)).thenReturn(testBudget);

        Budget result = budgetService.createOrUpdateBudget(testBudget);

        assertNotNull(result, "Saved budget should not be null.");
        assertEquals(testBudget, result, "The saved budget should match the test budget.");
        verify(budgetRepository, times(1)).save(testBudget);
    }

    @Test
    void getBudgetById() {
        when(budgetRepository.findById(testBudget.getId())).thenReturn(Optional.of(testBudget));

        Optional<Budget> retrievedBudget = budgetService.getBudgetById(testBudget.getId());

        assertTrue(retrievedBudget.isPresent(), "Budget should be present.");
        assertEquals(testBudget, retrievedBudget.get(), "The retrieved budget should match the test budget.");
        verify(budgetRepository, times(1)).findById(testBudget.getId());
    }

    @Test
    void getBudgetByYear() {
        when(budgetRepository.findByYear(testBudget.getYear())).thenReturn(testBudget);

        Budget result = budgetService.getBudgetByYear(testBudget.getYear());

        assertNotNull(result, "Budget should not be null.");
        assertEquals(testBudget, result, "The retrieved budget should match the test budget.");
        verify(budgetRepository, times(1)).findByYear(testBudget.getYear());
    }

    @Test
    void getAllBudgets() {
        when(budgetRepository.findAll()).thenReturn(Arrays.asList(testBudget));

        List<Budget> result = budgetService.getAllBudgets();

        assertFalse(result.isEmpty(), "Budgets list should not be empty.");
        assertEquals(1, result.size(), "There should be one budget in the list.");
        assertEquals(testBudget, result.get(0), "The budget retrieved should match the test budget.");
        verify(budgetRepository, times(1)).findAll();
    }

    @Test
    void deleteBudget() {
        doNothing().when(budgetRepository).deleteById(testBudget.getId());

        budgetService.deleteBudget(testBudget.getId());

        verify(budgetRepository, times(1)).deleteById(testBudget.getId());
    }
}
