package com.tawasalna.tawasalnacrm;


import com.tawasalna.tawasalnacrm.models.ReseauxSociaux;
import com.tawasalna.tawasalnacrm.repository.ReseauxSociauxRepository;
import com.tawasalna.tawasalnacrm.service.ReseauxSociauxServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReseauxSociauxServiceImplTest {

    @Mock
    private ReseauxSociauxRepository reseauxSociauxRepository;

    @InjectMocks
    private ReseauxSociauxServiceImpl reseauxSociauxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllReseauxSociaux_shouldReturnAllReseauxSociaux() {
        // Arrange
        List<ReseauxSociaux> reseauxSociauxList = new ArrayList<>();
        reseauxSociauxList.add(new ReseauxSociaux());
        reseauxSociauxList.add(new ReseauxSociaux());
        when(reseauxSociauxRepository.findAll()).thenReturn(reseauxSociauxList);

        // Act
        List<ReseauxSociaux> result = reseauxSociauxService.getAllReseauxSociaux();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getReseauxSociauxById_shouldReturnReseauxSociauxIfExists() {
        // Arrange
        String reseauxSociauxId = "123";
        ReseauxSociaux reseauxSociaux = new ReseauxSociaux();
        when(reseauxSociauxRepository.findById(reseauxSociauxId)).thenReturn(Optional.of(reseauxSociaux));

        // Act
        ReseauxSociaux result = reseauxSociauxService.getReseauxSociauxById(reseauxSociauxId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void saveReseauxSociaux_shouldSaveReseauxSociaux() {
        // Arrange
        ReseauxSociaux reseauxSociaux = new ReseauxSociaux();
        when(reseauxSociauxRepository.save(reseauxSociaux)).thenReturn(reseauxSociaux);

        // Act
        ReseauxSociaux result = reseauxSociauxService.saveReseauxSociaux(reseauxSociaux);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateReseauxSociaux_shouldUpdateReseauxSociaux() {
        // Arrange
        ReseauxSociaux reseauxSociaux = new ReseauxSociaux();
        when(reseauxSociauxRepository.save(reseauxSociaux)).thenReturn(reseauxSociaux);

        // Act
        ReseauxSociaux result = reseauxSociauxService.updateReseauxSociaux(reseauxSociaux);

        // Assert
        assertNotNull(result);
    }

    @Test
    void deleteReseauxSociaux_shouldDeleteReseauxSociaux() {
        // Arrange
        String reseauxSociauxId = "123";

        // Act
        reseauxSociauxService.deleteReseauxSociaux(reseauxSociauxId);

        // Assert
        verify(reseauxSociauxRepository, times(1)).deleteById(reseauxSociauxId);
    }

    @Test
    void getReseauxDetailsList_shouldReturnReseauxDetailsList() {
        // Arrange
        List<ReseauxSociaux> reseauxSociauxesDetailsList = new ArrayList<>();
        reseauxSociauxesDetailsList.add(new ReseauxSociaux());
        reseauxSociauxesDetailsList.add(new ReseauxSociaux());
        when(reseauxSociauxRepository.findAll()).thenReturn(reseauxSociauxesDetailsList);

        // Act
        List<ReseauxSociaux> result = reseauxSociauxService.getReseauxDetailsList();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void archiveReseauxSociaux_shouldArchiveReseauxSociaux() {
        // Arrange
        String reseauxSociauxId = "123";
        ReseauxSociaux reseauxSociaux = new ReseauxSociaux();
        when(reseauxSociauxRepository.findById(reseauxSociauxId)).thenReturn(Optional.of(reseauxSociaux));

        // Act
        reseauxSociauxService.archiveReseauxSociaux(reseauxSociauxId);

        // Assert
        assertTrue(reseauxSociaux.isArchived());
        verify(reseauxSociauxRepository, times(1)).save(reseauxSociaux);
    }
}
