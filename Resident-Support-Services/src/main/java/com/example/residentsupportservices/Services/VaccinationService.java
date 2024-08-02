package com.example.residentsupportservices.Services;

import com.example.residentsupportservices.Entity.Vaccination;
import com.example.residentsupportservices.Repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccinationService implements IVaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    // Ajouter une nouvelle vaccination
    @Override
    public Vaccination addVaccination(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }

    // Obtenir une vaccination par ID
    @Override
    public Optional<Vaccination> getVaccinationById(String id) {
        return vaccinationRepository.findById(id);
    }

    // Mettre à jour une vaccination
    @Override
    public Vaccination updateVaccination(String id, Vaccination vaccination) {
        if (vaccinationRepository.existsById(id)) {
            vaccination.setId(id);
            return vaccinationRepository.save(vaccination);
        } else {
            return null;
        }
    }

    // Supprimer une vaccination
    @Override
    public void deleteVaccination(String id) {
        vaccinationRepository.deleteById(id);
    }

    // Obtenir les vaccinations par ID d'animal
    @Override
    public List<Vaccination> getVaccinationsByPetId(String petId) {
        return vaccinationRepository.findByPetId(petId);
    }
    @Override
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAll();
    }
}

