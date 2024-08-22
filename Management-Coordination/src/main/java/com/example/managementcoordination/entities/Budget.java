package com.example.managementcoordination.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.List;

@Document(collection = "budgets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Budget {

    @Id
    private String id;

    private String year;
    private BigDecimal totalAmount;
    private BigDecimal allocatedAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
    private String department ;

}
