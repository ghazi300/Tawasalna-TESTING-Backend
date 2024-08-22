package com.example.residentsupportservices.Services;

import com.example.residentsupportservices.Entity.Pet;
import com.example.residentsupportservices.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService implements IPetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(String id, Pet petDetails) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            pet.setName(petDetails.getName());
            pet.setType(petDetails.getType());
            pet.setBreed(petDetails.getBreed());
            pet.setBirthDate(petDetails.getBirthDate());
        }
        return petRepository.save(pet);
    }

    @Override
    public void deletePet(String id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            petRepository.delete(pet);
        }
    }

    @Override
    public Pet getPetById(String id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}
