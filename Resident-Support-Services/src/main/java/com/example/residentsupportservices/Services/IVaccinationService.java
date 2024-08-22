package com.example.residentsupportservices.Services;


import com.example.residentsupportservices.Entity.Vaccination;

import java.util.List;
import java.util.Optional;

public interface IVaccinationService {

    Vaccination addVaccination(Vaccination vaccination);

    Optional<Vaccination> getVaccinationById(String id);

    Vaccination updateVaccination(String id, Vaccination vaccination);

    void deleteVaccination(String id);

    List<Vaccination> getVaccinationsByPetId(String petId);

    List<Vaccination> getAllVaccinations();

}
