package com.tawasalna.business.service;

import com.tawasalna.business.repository.ContractClauseRepository;
import com.tawasalna.business.repository.ContractRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ContractServiceImpl implements IContractService {

    private final ContractRepository contractRepository;
    private final ContractClauseRepository contractClauseRepository;
}
