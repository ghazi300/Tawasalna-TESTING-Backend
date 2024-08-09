package com.tawasalnasecuritysafety.services;

import com.tawasalnasecuritysafety.models.SafetyChecklist;
import com.tawasalnasecuritysafety.repos.SafetyChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SafetyChecklistService {
    @Autowired
    private SafetyChecklistRepository safetyChecklistRepository;

    public List<SafetyChecklist> getAllSafetyChecklists() {
        return safetyChecklistRepository.findAll();
    }

    public Optional<SafetyChecklist> getSafetyChecklistById(String id) {
        return safetyChecklistRepository.findById(id);
    }

    public SafetyChecklist createSafetyChecklist(SafetyChecklist safetyChecklist) {
        return safetyChecklistRepository.save(safetyChecklist);
    }

    public SafetyChecklist updateSafetyChecklist(String id, SafetyChecklist safetyChecklistDetails) {
        SafetyChecklist safetyChecklist = safetyChecklistRepository.findById(id).orElseThrow(() -> new RuntimeException("Safety Checklist not found"));

        safetyChecklist.setTitle(safetyChecklistDetails.getTitle());
        safetyChecklist.setFireExtinguishersAccessibleAndInspected(safetyChecklistDetails.isFireExtinguishersAccessibleAndInspected());
        safetyChecklist.setFireAlarmsAndSmokeDetectorsOperational(safetyChecklistDetails.isFireAlarmsAndSmokeDetectorsOperational());
        safetyChecklist.setClearAndMarkedEmergencyExits(safetyChecklistDetails.isClearAndMarkedEmergencyExits());
        safetyChecklist.setFunctionalSprinklerSystems(safetyChecklistDetails.isFunctionalSprinklerSystems());

        safetyChecklist.setGoodConditionOfElectricalCordsAndPlugs(safetyChecklistDetails.isGoodConditionOfElectricalCordsAndPlugs());
        safetyChecklist.setNoOverloadedOutletsOrExtensionCords(safetyChecklistDetails.isNoOverloadedOutletsOrExtensionCords());
        safetyChecklist.setAccessibleAndLabeledElectricalPanels(safetyChecklistDetails.isAccessibleAndLabeledElectricalPanels());

        safetyChecklist.setCleanAndDryFloors(safetyChecklistDetails.isCleanAndDryFloors());
        safetyChecklist.setClearWalkwaysAndAisles(safetyChecklistDetails.isClearWalkwaysAndAisles());
        safetyChecklist.setAdequateLighting(safetyChecklistDetails.isAdequateLighting());
        safetyChecklist.setSecureStorageOfMaterials(safetyChecklistDetails.isSecureStorageOfMaterials());

        safetyChecklist.setProperLabelingAndStorageOfHazardousMaterials(safetyChecklistDetails.isProperLabelingAndStorageOfHazardousMaterials());
        safetyChecklist.setAvailableAndUpToDateSDS(safetyChecklistDetails.isAvailableAndUpToDateSDS());
        safetyChecklist.setProperHazardousWasteDisposal(safetyChecklistDetails.isProperHazardousWasteDisposal());

        safetyChecklist.setFullyStockedFirstAidKits(safetyChecklistDetails.isFullyStockedFirstAidKits());
        safetyChecklist.setOperationalEyewashStationsAndSafetyShowers(safetyChecklistDetails.isOperationalEyewashStationsAndSafetyShowers());
        safetyChecklist.setPostedEmergencyContactNumbers(safetyChecklistDetails.isPostedEmergencyContactNumbers());

        safetyChecklist.setProperlyGuardedMachines(safetyChecklistDetails.isProperlyGuardedMachines());
        safetyChecklist.setRegularMaintenanceAndInspection(safetyChecklistDetails.isRegularMaintenanceAndInspection());
        safetyChecklist.setTrainedEquipmentOperators(safetyChecklistDetails.isTrainedEquipmentOperators());

        safetyChecklist.setApprovedChemicalStorageContainers(safetyChecklistDetails.isApprovedChemicalStorageContainers());
        safetyChecklist.setProperVentilation(safetyChecklistDetails.isProperVentilation());
        safetyChecklist.setAvailableSpillKits(safetyChecklistDetails.isAvailableSpillKits());

        safetyChecklist.setAccessiblePPE(safetyChecklistDetails.isAccessiblePPE());
        safetyChecklist.setRegularPPEInspectionAndReplacement(safetyChecklistDetails.isRegularPPEInspectionAndReplacement());
        safetyChecklist.setEmployeeTrainingOnPPEUse(safetyChecklistDetails.isEmployeeTrainingOnPPEUse());

        safetyChecklist.setErgonomicWorkstations(safetyChecklistDetails.isErgonomicWorkstations());
        safetyChecklist.setManualHandlingControls(safetyChecklistDetails.isManualHandlingControls());
        safetyChecklist.setProperLiftingTechniqueTraining(safetyChecklistDetails.isProperLiftingTechniqueTraining());

        safetyChecklist.setRegularCleaningSchedule(safetyChecklistDetails.isRegularCleaningSchedule());
        safetyChecklist.setProperTrashAndRecyclingManagement(safetyChecklistDetails.isProperTrashAndRecyclingManagement());

        safetyChecklist.setAdequateVentilation(safetyChecklistDetails.isAdequateVentilation());
        safetyChecklist.setControlledNoiseLevels(safetyChecklistDetails.isControlledNoiseLevels());

        safetyChecklist.setFunctionalSecurityCameras(safetyChecklistDetails.isFunctionalSecurityCameras());
        safetyChecklist.setAccessControlSystems(safetyChecklistDetails.isAccessControlSystems());

        safetyChecklist.setComments(safetyChecklistDetails.getComments());

        return safetyChecklistRepository.save(safetyChecklist);
    }

    public void deleteSafetyChecklist(String id) {
        safetyChecklistRepository.deleteById(id);
    }
}
