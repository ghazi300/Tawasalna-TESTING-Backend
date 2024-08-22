package com.tawasalna.facilitiesmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.service.IParkingLot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ParkingLotControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IParkingLot iParkingLot;

    private ParkingLot parkingLot;
    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot("1", "Lot A", "Downtown", 34.05, -118.25, 100,
                Arrays.asList("Route 1", "Route 2"),
                LocalDateTime.now(), LocalDateTime.now().plusHours(8));

    }

    @Test
    void addParkingLot() throws Exception {
        when(iParkingLot.add(any(ParkingLot.class))).thenReturn(parkingLot);

        mockMvc.perform(post("/parkinglot/addparkinglot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingLot)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(parkingLot.getName())))
                .andExpect(jsonPath("$.loacation", is(parkingLot.getLoacation())))
                .andExpect(jsonPath("$.latitude", is(parkingLot.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(parkingLot.getLongitude())))
                .andExpect(jsonPath("$.totalspace", is(parkingLot.getTotalspace())));
    }

    @Test
    void getParkingLots() throws Exception {
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);
        when(iParkingLot.getParkingLot()).thenReturn(parkingLots);

        mockMvc.perform(get("/parkinglot/getparkinglots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(parkingLot.getName())));
    }

    @Test
    void getParkingSpaceById() throws Exception {
        when(iParkingLot.getParkingLot()).thenReturn(Arrays.asList(parkingLot));

        mockMvc.perform(get("/parkinglot/{id}", parkingLot.getParkinglotid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(parkingLot.getName())))
                .andExpect(jsonPath("$.parkinglotid", is(parkingLot.getParkinglotid())));
    }

    @Test
    void updateParkingLot() throws Exception {
        when(iParkingLot.update(anyString(), any(ParkingLot.class))).thenReturn(parkingLot);

        mockMvc.perform(put("/parkinglot/updateparkinglot/{id}", parkingLot.getParkinglotid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingLot)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(parkingLot.getName())))
                .andExpect(jsonPath("$.loacation", is(parkingLot.getLoacation())));
    }

    @Test
    void deleteParkingLot() throws Exception {
        mockMvc.perform(delete("/parkinglot/deleteparkinglot/{id}", parkingLot.getParkinglotid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getActiveVehicleCount() throws Exception {
        when(iParkingLot.getDistinctLocationCount()).thenReturn(5L);

        mockMvc.perform(get("/parkinglot/getcount")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}