package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetRepository extends MongoRepository<Pet,String> {
}
