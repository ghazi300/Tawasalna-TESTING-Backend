package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.payload.request.CulturalRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.CulturalResponse;

import java.util.List;

public interface ICulturalProgramService {
    String save(CulturalRequest culturalRequest);

    List<CulturalResponse> getAllPrograms();

    void deleteProgram(String id);
}
