package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.payload.request.InitiativeRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.InitiativeResponse;

import java.util.List;

public interface IDiversityInitiativeService {
    String save(InitiativeRequest initiativeRequest);

    List<InitiativeResponse> getAllInitiatives();

    void deleteInitiative(String id);
}
