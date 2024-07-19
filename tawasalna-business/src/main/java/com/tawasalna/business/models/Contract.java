package com.tawasalna.business.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Getter
@Setter
public class Contract {

    @Id
    private String id;

    private String title;

    private String description;

    @DocumentReference
    private List<ContractClause> contractClauses;

    private String fileName;
}
