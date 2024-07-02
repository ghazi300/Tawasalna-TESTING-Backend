package com.tawasalna.business.service;

import com.tawasalna.business.repository.OpportunityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OpportunitiesServiceImpl implements IOpportunitiesService {

    private final OpportunityRepository opportunityRepository;
}
