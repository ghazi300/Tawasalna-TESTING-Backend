package com.example.residentsupportservices.Services;

import com.example.residentsupportservices.Entity.PetBoarding;
import com.example.residentsupportservices.Repository.PetBoardingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetBoardingService implements IPetBoardingService {

    @Autowired
    private PetBoardingRepository petBoardingRepository;

    @Override
    public PetBoarding addPetBoarding(PetBoarding petBoarding) {
        return petBoardingRepository.save(petBoarding);
    }

    @Override
    public PetBoarding updatePetBoarding(String id, PetBoarding petBoardingDetails) {
        PetBoarding petBoarding = petBoardingRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet Boarding not found"));
        petBoarding.setPetId(petBoardingDetails.getPetId());
        petBoarding.setOwnerName(petBoardingDetails.getOwnerName());
        petBoarding.setStartDate(petBoardingDetails.getStartDate());
        petBoarding.setEndDate(petBoardingDetails.getEndDate());
        petBoarding.setNotes(petBoardingDetails.getNotes());
        return petBoardingRepository.save(petBoarding);
    }

    @Override
    public void deletePetBoarding(String id) {
        petBoardingRepository.deleteById(id);
    }

    @Override
    public PetBoarding getPetBoardingById(String id) {
        return petBoardingRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet Boarding not found"));
    }

    @Override
    public List<PetBoarding> getAllPetBoardings() {
        return petBoardingRepository.findAll();
    }
    @Override
    @Transactional
    public void confirmBoarding(String id) {
        PetBoarding petBoarding = petBoardingRepository.findById(id)
                .orElse(null);
        petBoarding.setStatus("Confirmed");
        petBoardingRepository.save(petBoarding);
    }

    @Override
    // Method to reject a pet boarding request
    @Transactional
    public void rejectBoarding(String id) {
        PetBoarding petBoarding = petBoardingRepository.findById(id)
                .orElse(null);
        petBoarding.setStatus("Rejected");
        petBoardingRepository.save(petBoarding);
    }
}
