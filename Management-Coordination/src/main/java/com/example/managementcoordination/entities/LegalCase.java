package com.example.managementcoordination.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "legal_cases")
@AllArgsConstructor
@Getter
@Setter
public class LegalCase {

    @Id
    private String id;
    private String caseTitle;
    private CaseType caseType;
    private String description;
    private CaseStatus caseStatus;
    private String advice;




    public enum CaseType {
        LITIGATION, COMPLIANCE, CONTRACT
    }

    public enum CaseStatus {
        OPEN, CLOSED, PENDING
    }



    public LegalCase() {}

}
