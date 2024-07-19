package com.tawasalna.business.controllers;

import com.tawasalna.business.service.IOpportunitiesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/opportunities")
@AllArgsConstructor
public class OpportunitiesController {

    private final IOpportunitiesService service;
}
