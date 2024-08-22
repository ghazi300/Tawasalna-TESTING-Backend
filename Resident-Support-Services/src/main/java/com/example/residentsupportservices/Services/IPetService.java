package com.example.residentsupportservices.Services;

import com.example.residentsupportservices.Entity.Pet;

import java.util.List;

public interface IPetService {
    Pet addPet(Pet pet);
    Pet updatePet(String id, Pet petDetails);
    void deletePet(String id);
    Pet getPetById(String id);
    List<Pet> getAllPets();
}