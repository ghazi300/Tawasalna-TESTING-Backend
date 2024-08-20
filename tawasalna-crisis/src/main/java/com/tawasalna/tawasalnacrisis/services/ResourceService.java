package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.models.Resource;

import java.util.List;

public interface ResourceService {
    Resource saveResource(Resource resource);
    List<Resource> getAllResources();
    Resource getResourceById(String id);
}
