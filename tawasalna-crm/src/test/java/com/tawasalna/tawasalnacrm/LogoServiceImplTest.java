package com.tawasalna.tawasalnacrm;


import com.tawasalna.tawasalnacrm.models.Logo;
import com.tawasalna.tawasalnacrm.repository.LogoRepository;
import com.tawasalna.tawasalnacrm.service.LogoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogoServiceImplTest {

    @Mock
    private LogoRepository logoRepository;

    @InjectMocks
    private LogoServiceImpl logoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveLogo_shouldSaveLogoSuccessfully() throws IOException {
        // Arrange
        String filename = "test_logo.png";
        byte[] data = "test data".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "image/png", data);
        Logo logo = new Logo();
        logo.setFilename(filename);
        logo.setData(data);
        when(logoRepository.save(any())).thenReturn(logo);

        // Act
        Logo result = logoService.saveLogo(file);

        // Assert
        assertNotNull(result);
        assertEquals(filename, result.getFilename());
        assertArrayEquals(data, result.getData());
    }

    @Test
    void deleteLogo_shouldDeleteLogoSuccessfully() {
        // Arrange
        String logoId = "123";

        // Act
        logoService.deleteLogo(logoId);

        // Assert
        verify(logoRepository, times(1)).deleteById(logoId);
    }

    @Test
    void getLogo_shouldReturnLogoIfExists() {
        // Arrange
        String logoId = "123";
        Logo logo = new Logo();
        when(logoRepository.findById(logoId)).thenReturn(Optional.of(logo));

        // Act
        Optional<Logo> result = logoService.getLogo(logoId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(logo, result.get());
    }
/*
    @Test
    void updateLogo_shouldUpdateLogoSuccessfully() throws IOException {
        // Arrange
        String logoId = "123";
        String filename = "updated_logo.png";
        byte[] data = "updated data".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "image/png", data);
        Logo logo = new Logo();
        logo.setId(logoId);
        when(logoRepository.findById(logoId)).thenReturn(Optional.of(logo));
        when(logoRepository.save(any())).thenReturn(logo);

        // Act
        Logo result = logoService.updateLogo(logoId, file);

        // Assert
        assertNotNull(result);
        assertEquals(filename, result.getFilename());
        assertArrayEquals(data, result.getData());
    }
*/

    @Test
    void getAllLogos_shouldReturnAllLogos() {
        // Arrange
        List<Logo> logos = new ArrayList<>();
        logos.add(new Logo());
        logos.add(new Logo());
        when(logoRepository.findAll()).thenReturn(logos);

        // Act
        List<Logo> result = logoService.getAllLogos();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void archiveLogo_shouldArchiveLogoSuccessfully() {
        // Arrange
        String logoId = "123";
        Logo logo = new Logo();
        when(logoRepository.findById(logoId)).thenReturn(Optional.of(logo));

        // Act
        logoService.archiveLogo(logoId);

        // Assert
        assertTrue(logo.isArchived());
        verify(logoRepository, times(1)).save(logo);
    }
}
