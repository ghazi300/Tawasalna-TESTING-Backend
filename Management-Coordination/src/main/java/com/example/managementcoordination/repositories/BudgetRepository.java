package com.example.managementcoordination.repositories;

import com.example.managementcoordination.entities.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends MongoRepository<Budget,String> {
    Budget findByYear(String year);

}
