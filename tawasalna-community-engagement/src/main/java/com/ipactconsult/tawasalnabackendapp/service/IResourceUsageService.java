package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.ResourceUsage;

import java.util.List;

public interface IResourceUsageService {
    List<ResourceUsage> getAllResourceUsages();
}
