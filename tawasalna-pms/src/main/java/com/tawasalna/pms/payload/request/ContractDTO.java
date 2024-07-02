package com.tawasalna.pms.payload.request;
import com.tawasalna.pms.models.enums.Billing;
import com.tawasalna.pms.models.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
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
