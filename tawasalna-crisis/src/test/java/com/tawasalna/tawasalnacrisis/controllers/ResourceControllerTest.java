package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Resource;
import com.tawasalna.tawasalnacrisis.services.ResourceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateResource() {
        Resource resource = new Resource();
        when(resourceService.saveResource(resource)).thenReturn(resource);

        ResponseEntity<Resource> response = resourceController.createResource(resource);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resource, response.getBody());
        verify(resourceService, times(1)).saveResource(resource);
    }

    @Test
    public void testGetAllResources() {
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();
        List<Resource> resources = Arrays.asList(resource1, resource2);

        when(resourceService.getAllResources()).thenReturn(resources);

        ResponseEntity<List<Resource>> response = resourceController.getAllResources();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resources, response.getBody());
        verify(resourceService, times(1)).getAllResources();
    }

    @Test
    public void testGetResourceByIdFound() {
        String id = "test-id";
        Resource resource = new Resource();
        when(resourceService.getResourceById(id)).thenReturn(resource);
        ResponseEntity<Resource> response = resourceController.getResourceById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resource, response.getBody());
        verify(resourceService, times(1)).getResourceById(id);
    }

    @Test
    public void testGetResourceByIdNotFound() {
        String id = "test-id";
        when(resourceService.getResourceById(id)).thenReturn(null);

        ResponseEntity<Resource> response = resourceController.getResourceById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(resourceService, times(1)).getResourceById(id);
    }
}
