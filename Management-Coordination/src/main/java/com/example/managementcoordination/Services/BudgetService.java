package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Budget;
import com.example.managementcoordination.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public Budget createOrUpdateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Optional<Budget> getBudgetById(String id) {
        return budgetRepository.findById(id);
    }

    public Budget getBudgetByYear(String year) {
        return budgetRepository.findByYear(year);
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public void deleteBudget(String id) {
        budgetRepository.deleteById(id);
    }
}
