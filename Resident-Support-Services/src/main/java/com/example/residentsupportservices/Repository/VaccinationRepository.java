package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.Vaccination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {

    // Méthode pour obtenir les vaccinations par identifiant d'animal
    List<Vaccination> findByPetId(String petId);
}
