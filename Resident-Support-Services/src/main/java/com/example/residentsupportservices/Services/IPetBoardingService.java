package com.example.residentsupportservices.Services;
import com.example.residentsupportservices.Entity.PetBoarding;

import java.util.List;

public interface IPetBoardingService {
    PetBoarding addPetBoarding(PetBoarding petBoarding);
    PetBoarding updatePetBoarding(String id, PetBoarding petBoardingDetails);
    void deletePetBoarding(String id);
    PetBoarding getPetBoardingById(String id);
    List<PetBoarding> getAllPetBoardings();
    void confirmBoarding(String id);
    void rejectBoarding(String id);
}
