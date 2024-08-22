package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Risk;

import java.util.List;
import java.util.Optional;

public interface RiskService {
    List<Risk> getAllRisks();
    Optional<Risk> getRiskById(String id);
    Risk createRisk(Risk risk);
    Risk updateRisk(String id, Risk riskDetails);
    void deleteRisk(String id);
}
