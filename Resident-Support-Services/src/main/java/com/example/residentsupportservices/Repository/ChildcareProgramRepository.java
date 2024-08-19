package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.ChildcareProgram;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChildcareProgramRepository extends MongoRepository<ChildcareProgram, String> {
}
