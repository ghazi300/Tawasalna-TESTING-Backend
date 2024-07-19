package com.tawasalna.pms.models;

import com.tawasalna.pms.models.enums.Billing;
import com.tawasalna.pms.models.enums.PaymentMode;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "contract")
public class Contract {
    @Id
    private String id;
    private String agentFirstname;
    private String agentLastname;
    private String ownerFirstname;
    private String ownerLastname;
    private Date startDate;
    private Date endDate;
    private Long fixedPrice;
    private Billing billing;
    private PaymentMode paymentMode;
    private String propertyDescription;
    private String terminationClause;
    private String confidentialityClause;
    private String jurisdictionClause;
    private String agentSignature;
    private String ownerSignature;


}
