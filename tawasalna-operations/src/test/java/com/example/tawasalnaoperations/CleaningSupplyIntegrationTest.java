package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.controllers.CleaningSupplyController;
import com.example.tawasalnaoperations.entities.CleaningSupply;
import com.example.tawasalnaoperations.repositories.CleaningSupplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CleaningSupplyController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc

public class CleaningSupplyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CleaningSupplyRepository cleaningSupplyRepository;

    @InjectMocks
    private CleaningSupplyController cleaningSupplyController;

    private CleaningSupply mockCleaningSupply;

    @BeforeEach
    void setUp() {
        mockCleaningSupply = new CleaningSupply("1", "Detergent", 100, "Cleaning");
    }

    @Test
    void getCleaningSupplies_shouldReturnCleaningSupplies() {
        when(cleaningSupplyRepository.findAll()).thenReturn(Collections.singletonList(mockCleaningSupply));

        ResponseEntity<List<CleaningSupply>> response = cleaningSupplyController.getAllSupply();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check status code
        List<CleaningSupply> result = response.getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockCleaningSupply, result.get(0));
        verify(cleaningSupplyRepository).findAll();
    }


    @Test
    void addCleaningSupply_shouldSaveAndReturnCleaningSupply() {
        when(cleaningSupplyRepository.save(any(CleaningSupply.class))).thenReturn(mockCleaningSupply);

        ResponseEntity<CleaningSupply> response = cleaningSupplyController.createSupply(mockCleaningSupply);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Check status code
        CleaningSupply result = response.getBody();
        assertNotNull(result);
        assertEquals(mockCleaningSupply, result);
        verify(cleaningSupplyRepository).save(mockCleaningSupply);
    }


    @Test
    void updateCleaningSupply_shouldUpdateAndReturnUpdatedCleaningSupply() {
        when(cleaningSupplyRepository.findById("1")).thenReturn(Optional.of(mockCleaningSupply));
        when(cleaningSupplyRepository.save(any(CleaningSupply.class))).thenReturn(mockCleaningSupply);

        CleaningSupply updatedCleaningSupply = new CleaningSupply("1", "Soap", 150, "Cleaning");

        ResponseEntity<CleaningSupply> response = cleaningSupplyController.updateSupply(mockCleaningSupply.getSupplyId(), updatedCleaningSupply);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check status code
        CleaningSupply result = response.getBody();
        assertNotNull(result);
        assertEquals(updatedCleaningSupply.getItemName(), result.getItemName());
        assertEquals(updatedCleaningSupply.getQuantity(), result.getQuantity());
        verify(cleaningSupplyRepository).save(updatedCleaningSupply);
    }

    @Test
    void updateCleaningSupply_shouldThrowExceptionWhenCleaningSupplyNotFound() {
        when(cleaningSupplyRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            cleaningSupplyController.updateSupply(mockCleaningSupply.getSupplyId(), mockCleaningSupply);
        });

        String expectedMessage = "404 NOT_FOUND \"CleaningSupply not found\"";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(cleaningSupplyRepository).findById("1");
    }


    @Test
    void deleteCleaningSupply_shouldDeleteCleaningSupply() {
        when(cleaningSupplyRepository.findById("1")).thenReturn(Optional.of(mockCleaningSupply));

        ResponseEntity<Void> response = cleaningSupplyController.deleteSupply(mockCleaningSupply.getSupplyId());

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Check status code
        verify(cleaningSupplyRepository).deleteById("1");
    }
}