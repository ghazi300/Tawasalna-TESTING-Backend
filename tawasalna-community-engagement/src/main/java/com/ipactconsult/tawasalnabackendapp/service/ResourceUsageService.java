package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.ResourceUsage;
import com.ipactconsult.tawasalnabackendapp.repository.ResourceUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class ResourceUsageService implements IResourceUsageService{
    public final ResourceUsageRepository resourceUsageRepository;

    @Override
    public List<ResourceUsage> getAllResourceUsages() {
        return resourceUsageRepository.findAll();
    }
}
