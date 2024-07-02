package com.tawasalna.business.controllers;

import com.tawasalna.business.service.IContractService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
@AllArgsConstructor
public class ContractController {

    private final IContractService contractService;
}
