package com.tawasalna.tawasalnacrm;




import com.tawasalna.tawasalnacrm.models.About;
import com.tawasalna.tawasalnacrm.repository.AboutRepository;
import com.tawasalna.tawasalnacrm.service.AboutServiceImpl;
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

class AboutServiceImplTest {

    @Mock
    private AboutRepository aboutRepository;

    @InjectMocks
    private AboutServiceImpl aboutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllAbout_shouldReturnAllAbout() {
        // Arrange
        List<About> aboutList = new ArrayList<>();
        aboutList.add(new About());
        aboutList.add(new About());
        when(aboutRepository.findAll()).thenReturn(aboutList);

        // Act
        List<About> result = aboutService.getAllAbout();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getAboutById_shouldReturnAboutIfExists() {
        // Arrange
        String aboutId = "123";
        About about = new About();
        when(aboutRepository.findById(aboutId)).thenReturn(Optional.of(about));

        // Act
        About result = aboutService.getAboutById(aboutId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void saveAbout_shouldSaveAbout() {
        // Arrange
        About about = new About();
        when(aboutRepository.save(about)).thenReturn(about);

        // Act
        About result = aboutService.saveAbout(about);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateAbout_shouldUpdateAbout() {
        // Arrange
        About about = new About();
        when(aboutRepository.save(about)).thenReturn(about);

        // Act
        About result = aboutService.updateAbout(about);

        // Assert
        assertNotNull(result);
    }

    @Test
    void deleteAbout_shouldDeleteAbout() {
        // Arrange
        String aboutId = "123";

        // Act
        aboutService.deleteAbout(aboutId);

        // Assert
        verify(aboutRepository, times(1)).deleteById(aboutId);
    }

    @Test
    void archiveAbout_shouldArchiveAbout() {
        // Arrange
        String aboutId = "123";
        About about = new About();
        when(aboutRepository.findById(aboutId)).thenReturn(Optional.of(about));

        // Act
        aboutService.archiveAbout(aboutId);

        // Assert
        assertTrue(about.isArchived());
        verify(aboutRepository, times(1)).save(about);
    }
}
