package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.CulturalProgram;
import com.ipactconsult.tawasalnabackendapp.models.DiversityInitiative;
import com.ipactconsult.tawasalnabackendapp.payload.request.InitiativeRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.InitiativeResponse;
import com.ipactconsult.tawasalnabackendapp.repository.DiversityInitiativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class DiversityInitiativeService implements IDiversityInitiativeService {
    private final DiversityInitiativeRepository diversityInitiativeRepository;
    private final DiversityMapper diversityMapper;

    @Override
    public String save(InitiativeRequest initiativeRequest) {
        DiversityInitiative diversityInitiative = diversityMapper.toDiversity(initiativeRequest);
        return diversityInitiativeRepository.save(diversityInitiative).getId();
    }

    @Override
    public List<InitiativeResponse> getAllInitiatives() {
        List<DiversityInitiative>initiativeResponseList=diversityInitiativeRepository.findAll();
        return initiativeResponseList.stream().map(diversityMapper::toDiversityResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteInitiative(String id) {
        diversityInitiativeRepository.deleteById(id);

    }
}
