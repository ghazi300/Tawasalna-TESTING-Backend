package com.tawasalna.business.controllers;

import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.service.BusinessServiceImpl;
import com.tawasalna.business.service.IBusinessService;
import com.tawasalna.shared.userapi.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BusinessServiceControllerTest {
/*
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BusinessServiceImpl service;

    private List<BusinessService> mockServices;

    @Test
    void testGetAllServicesNotArchived() throws Exception {
        // Perform GET request to /findAll endpoint
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/service/findAll")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify that the response status is OK (200)
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the response content matches the mocked data
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("66468e7c09428555df650545"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Service Exemple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("6654ee29cbd64a007f1ac9cd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("new service test prod"));
    }



    @Test
    void testGetServiceById() throws Exception {
        // Mocking the service to return a sample service with ID "1"


        // Perform GET request to /{serviceId} endpoint with ID "1"
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/service/66468e7c09428555df650545")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify that the response status is OK (200)
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the response content matches the mocked service
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("66468e7c09428555df650545"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Service Exemple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.basePrice").value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryTimeInHours").value(0));
        // Add assertions for other fields as needed
    }

   /* @Test
    void testGetServiceByOwnerId() throws Exception {
        // Mocking the service to return a list of sample services with owner ID "owner1"

        // Perform GET request to /findAll/{ownerId} endpoint with owner ID "owner1"
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/service/findAll/661cef7a860cb0007d305a61")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify that the response status is OK (200)
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());



        // Verify that the response content matches the mocked service
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("66468e7c09428555df650545"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Service Exemple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ,Description of this Service ."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.basePrice").value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryTimeInHours").value(0));


    }






    @Test
    void testGetServicesActivePaged() throws Exception {
        // Mocking the service to return a paginated list of active services
        Page<BusinessService> mockPage = new PageImpl<>(Collections.emptyList());

        // Perform GET request to /findAll/paginated endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/service/findAll/paginated?page=1")
                        .contentType(MediaType.APPLICATION_JSON))
                // Verify that the response status is OK (200)
                .andExpect(MockMvcResultMatchers.status().isOk());
        // You can add more assertions here if needed
    }

*/
}

