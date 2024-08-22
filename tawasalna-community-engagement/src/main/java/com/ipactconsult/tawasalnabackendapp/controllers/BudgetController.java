package com.ipactconsult.tawasalnabackendapp.controllers;


import com.ipactconsult.tawasalnabackendapp.service.IBudgetService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("budgets")
@RequiredArgsConstructor
@CrossOrigin("*")

public class BudgetController {
    private final IBudgetService budgetService;

}
