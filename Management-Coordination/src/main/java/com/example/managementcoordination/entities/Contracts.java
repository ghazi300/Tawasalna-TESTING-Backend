package com.example.managementcoordination.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Contracts" )
public class Contracts {
    @Id
    private String id;
    private String contractTitle;
    private contractType contractType;
    private String description;
    private Date startDate;
    private Date endDate;
    private String negotiationOutcome;
    private ContractStatus ContractStatus ;
    public enum ContractStatus {
        SIGNED, IN_PROGRESS, TERMINATED
    }
    public enum contractType {
        Residency , Parcking
    }
}
