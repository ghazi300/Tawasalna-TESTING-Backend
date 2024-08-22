package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Risk;
import com.tawasalna.tawasalnacrisis.repositories.RiskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class RiskServiceImpl implements RiskService {
    private RiskRepository riskRepository;

  @Override
  public List<Risk> getAllRisks() {
        return riskRepository.findAll();
    }

    @Override
    public Optional<Risk> getRiskById(String id) {
        return riskRepository.findById(id);
    }

    @Override
    public Risk createRisk(Risk risk) {
        return riskRepository.save(risk);
    }

    @Override
    public Risk updateRisk(String id, Risk riskDetails) {
        Risk risk = riskRepository.findById(id).orElseThrow(() -> new RuntimeException("Risk not found"));
        risk.setTitle(riskDetails.getTitle());
        risk.setDescription(riskDetails.getDescription());
        risk.setSeverity(riskDetails.getSeverity());
        risk.setIdentifiedDate(riskDetails.getIdentifiedDate());
        risk.setMitigated(riskDetails.isMitigated());
        return riskRepository.save(risk);
    }

    @Override
    public void deleteRisk(String id) {
        riskRepository.deleteById(id);
    }
}
