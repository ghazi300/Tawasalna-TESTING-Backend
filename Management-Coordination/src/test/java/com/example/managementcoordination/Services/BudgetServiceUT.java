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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceUT {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private BudgetService budgetService;

    private Budget budget1;
    private Budget budget2;

    @BeforeEach
    void setUp() {
        budget1 = new Budget("1", "2024", new BigDecimal("10000"), new BigDecimal("5000"), new BigDecimal("2000"), new BigDecimal("3000"), "Department A");
        budget2 = new Budget("2", "2025", new BigDecimal("15000"), new BigDecimal("7000"), new BigDecimal("3000"), new BigDecimal("4000"), "Department B");
    }

    @Test
    void createOrUpdateBudget() {
        // Given
        when(budgetRepository.save(budget1)).thenReturn(budget1);

        // When
        Budget result = budgetService.createOrUpdateBudget(budget1);

        // Then
        assertEquals(budget1, result);
        verify(budgetRepository, times(1)).save(budget1);
    }

    @Test
    void getBudgetById() {
        // Given
        when(budgetRepository.findById("1")).thenReturn(Optional.of(budget1));

        // When
        Optional<Budget> result = budgetService.getBudgetById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals(budget1, result.get());
    }

    @Test
    void getBudgetByYear() {
        // Given
        when(budgetRepository.findByYear("2024")).thenReturn(budget1);

        // When
        Budget result = budgetService.getBudgetByYear("2024");

        // Then
        assertEquals(budget1, result);
    }

    @Test
    void getAllBudgets() {
        // Given
        List<Budget> budgets = Arrays.asList(budget1, budget2);
        when(budgetRepository.findAll()).thenReturn(budgets);

        // When
        List<Budget> result = budgetService.getAllBudgets();

        // Then
        assertEquals(2, result.size());
        assertEquals(budget1, result.get(0));
        assertEquals(budget2, result.get(1));
    }

    @Test
    void deleteBudget() {
        // When
        budgetService.deleteBudget("1");

        // Then
        verify(budgetRepository, times(1)).deleteById("1");
    }
}
