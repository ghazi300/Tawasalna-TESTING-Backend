package com.tawasalna.pms.buisnesslogic;

import com.tawasalna.pms.exceptions.InvalidPropertyException;
import com.tawasalna.pms.models.Contract;
import com.tawasalna.pms.models.Property;
import com.tawasalna.pms.models.enums.Billing;
import com.tawasalna.pms.models.enums.PaymentMode;
import com.tawasalna.pms.payload.request.ContractDTO;
import com.tawasalna.pms.repos.ContractRepository;
import com.tawasalna.pms.repos.PropertyRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class ContractManagementServiceImp implements IContractManagementService{
    private final ContractRepository contractRepository;

    @Override
    public ResponseEntity<ApiResponse> addContract(ContractDTO contractDTO, List<MultipartFile>images) throws ExecutionException, InterruptedException{
        Contract c = new Contract();
        c.setAgentFirstname(contractDTO.getAgentFirstname());
        c.setAgentLastname(contractDTO.getAgentLastname());
        c.setOwnerFirstname(contractDTO.getOwnerFirstname());
        c.setOwnerLastname(contractDTO.getOwnerLastname());
        c.setStartDate(contractDTO.getStartDate());
        c.setEndDate(contractDTO.getEndDate());
        c.setFixedPrice(contractDTO.getFixedPrice());
        c.setBilling(contractDTO.getBilling());
        c.setPaymentMode(contractDTO.getPaymentMode());
        c.setPropertyDescription(contractDTO.getPropertyDescription());
        c.setTerminationClause(contractDTO.getTerminationClause());
        c.setConfidentialityClause(contractDTO.getConfidentialityClause());
        c.setJurisdictionClause(contractDTO.getJurisdictionClause());
        c.setAgentSignature(contractDTO.getAgentSignature());
        c.setOwnerSignature(contractDTO.getOwnerSignature());

        contractRepository.save(c);

        return ResponseEntity.ok(new ApiResponse("Contract successfully added", null, 200));
    }

    @Override
    public void delete(String id) {
        Contract contract= contractRepository
                .findById(id).orElseThrow(() -> new InvalidPropertyException(id, Consts.CONTRACT_NOT_FOUND));
        contractRepository.delete(contract);
    }
}

