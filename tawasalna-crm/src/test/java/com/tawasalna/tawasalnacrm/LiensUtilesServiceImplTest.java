package com.tawasalna.tawasalnacrm;


import com.tawasalna.tawasalnacrm.models.LiensUtiles;
import com.tawasalna.tawasalnacrm.repository.LiensUtilesRepository;
import com.tawasalna.tawasalnacrm.service.LiensUtilesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LiensUtilesServiceImplTest {

    @Mock
    private LiensUtilesRepository liensUtilesRepository;

    @InjectMocks
    private LiensUtilesServiceImpl liensUtilesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllLiensUtiles_shouldReturnAllLiensUtiles() {
        // Arrange
        List<LiensUtiles> liensUtilesList = new ArrayList<>();
        liensUtilesList.add(new LiensUtiles());
        liensUtilesList.add(new LiensUtiles());
        when(liensUtilesRepository.findAll()).thenReturn(liensUtilesList);

        // Act
        List<LiensUtiles> result = liensUtilesService.getAllLiensUtiles();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getLiensUtilesById_shouldReturnLiensUtilesIfExists() {
        // Arrange
        String liensUtilesId = "123";
        LiensUtiles liensUtiles = new LiensUtiles();
        when(liensUtilesRepository.findById(liensUtilesId)).thenReturn(Optional.of(liensUtiles));

        // Act
        LiensUtiles result = liensUtilesService.getLiensUtilesById(liensUtilesId);

        // Assert
        assertEquals(liensUtiles, result);
    }

    @Test
    void saveLiensUtiles_shouldSaveLiensUtiles() {
        // Arrange
        LiensUtiles liensUtiles = new LiensUtiles();
        when(liensUtilesRepository.save(liensUtiles)).thenReturn(liensUtiles);

        // Act
        LiensUtiles result = liensUtilesService.saveLiensUtiles(liensUtiles);

        // Assert
        assertEquals(liensUtiles, result);
    }

    @Test
    void updateLiensUtiles_shouldUpdateLiensUtiles() {
        // Arrange
        LiensUtiles liensUtiles = new LiensUtiles();
        when(liensUtilesRepository.save(liensUtiles)).thenReturn(liensUtiles);

        // Act
        LiensUtiles result = liensUtilesService.updateLiensUtiles(liensUtiles);

        // Assert
        assertEquals(liensUtiles, result);
    }

    @Test
    void deleteLiensUtiles_shouldDeleteLiensUtiles() {
        // Arrange
        String liensUtilesId = "123";

        // Act
        liensUtilesService.deleteLiensUtiles(liensUtilesId);

        // Assert
        verify(liensUtilesRepository, times(1)).deleteById(liensUtilesId);
    }

    @Test
    void archiveLienUtiles_shouldArchiveSuccessfully() {
        // Arrange
        String liensUtilesId = "123";
        LiensUtiles liensUtiles = new LiensUtiles();
        liensUtiles.setId(liensUtilesId);
        liensUtiles.setArchived(false);
        when(liensUtilesRepository.findById(liensUtilesId)).thenReturn(Optional.of(liensUtiles));

        // Act
        liensUtilesService.archiveLienUtiles(liensUtilesId);

        // Assert
        verify(liensUtilesRepository, times(1)).findById(liensUtilesId);
        verify(liensUtilesRepository, times(1)).save(liensUtiles);
        assert(liensUtiles.isArchived());
    }

    @Test
    void archiveLienUtiles_shouldNotArchiveWhenIdNotFound() {
        // Arrange
        String liensUtilesId = "123";
        when(liensUtilesRepository.findById(liensUtilesId)).thenReturn(Optional.empty());

        // Act
        liensUtilesService.archiveLienUtiles(liensUtilesId);

        // Assert
        verify(liensUtilesRepository, times(1)).findById(liensUtilesId);
        verify(liensUtilesRepository, never()).save(any());
    }
}
