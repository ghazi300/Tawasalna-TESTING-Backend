package com.tawasalnasecuritysafety.models;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "safetychecklist-tawasalna")
@Tag(name = "safetychecklist")
public class SafetyChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title;

    // Fire Safety
    private boolean fireExtinguishersAccessibleAndInspected;
    private boolean fireAlarmsAndSmokeDetectorsOperational;
    private boolean clearAndMarkedEmergencyExits;
    private boolean functionalSprinklerSystems;

    // Electrical Safety
    private boolean goodConditionOfElectricalCordsAndPlugs;
    private boolean noOverloadedOutletsOrExtensionCords;
    private boolean accessibleAndLabeledElectricalPanels;

    // General Workplace Safety
    private boolean cleanAndDryFloors;
    private boolean clearWalkwaysAndAisles;
    private boolean adequateLighting;
    private boolean secureStorageOfMaterials;

    // Hazardous Materials
    private boolean properLabelingAndStorageOfHazardousMaterials;
    private boolean availableAndUpToDateSDS;
    private boolean properHazardousWasteDisposal;

    // First Aid and Emergency Preparedness
    private boolean fullyStockedFirstAidKits;
    private boolean operationalEyewashStationsAndSafetyShowers;
    private boolean postedEmergencyContactNumbers;

    // Machinery and Equipment
    private boolean properlyGuardedMachines;
    private boolean regularMaintenanceAndInspection;
    private boolean trainedEquipmentOperators;

    // Chemical Safety
    private boolean approvedChemicalStorageContainers;
    private boolean properVentilation;
    private boolean availableSpillKits;

    // Personal Protective Equipment (PPE)
    private boolean accessiblePPE;
    private boolean regularPPEInspectionAndReplacement;
    private boolean employeeTrainingOnPPEUse;

    // Ergonomics
    private boolean ergonomicWorkstations;
    private boolean manualHandlingControls;
    private boolean properLiftingTechniqueTraining;

    // Housekeeping
    private boolean regularCleaningSchedule;
    private boolean properTrashAndRecyclingManagement;

    // Environmental Safety
    private boolean adequateVentilation;
    private boolean controlledNoiseLevels;

    // Security
    private boolean functionalSecurityCameras;
    private boolean accessControlSystems;

    @Lob

    private String comments;

    private String name;
    private String status;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItem(String safetyCheckTest) {
        this.title = safetyCheckTest;
    }

    public void setDate(String date) {
        this.comments = date;
    }

    public String getItem() {
        return this.title;
    }

}

