package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.CulturalProgram;
import com.ipactconsult.tawasalnabackendapp.models.Event;
import com.ipactconsult.tawasalnabackendapp.payload.request.CulturalRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.CulturalResponse;
import com.ipactconsult.tawasalnabackendapp.repository.CulturalProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CulturalProgramService implements ICulturalProgramService{
    private  final CulturalProgramRepository culturalProgramRepository;
    private final CulturalMapper culturalMapper;

    @Override
    public String save(CulturalRequest culturalRequest) {
        CulturalProgram culturalProgram = culturalMapper.toCultural(culturalRequest);
        return culturalProgramRepository.save(culturalProgram).getId();
    }

    @Override
    public List<CulturalResponse> getAllPrograms() {
        List<CulturalProgram> culturalProgramList = culturalProgramRepository.findAll();
        return culturalProgramList.stream()
                .map(culturalMapper::toCulturalResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProgram(String id) {
        culturalProgramRepository.deleteById(id);
    }
}
