package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.PetBoarding;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetBoardingRepository extends MongoRepository<PetBoarding, String> {
}
