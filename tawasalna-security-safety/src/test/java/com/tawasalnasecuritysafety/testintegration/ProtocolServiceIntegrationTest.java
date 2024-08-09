package com.tawasalnasecuritysafety.testintegration;

import com.tawasalnasecuritysafety.models.Protocol;
import com.tawasalnasecuritysafety.repos.ProtocolRepository;
import com.tawasalnasecuritysafety.services.ProtocolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProtocolServiceIntegrationTest {

    @Mock
    private ProtocolRepository protocolRepository;

    @InjectMocks
    private ProtocolService protocolService;

    private Protocol mockProtocol;

    @BeforeEach
    void setUp() {
        mockProtocol = new Protocol();
        mockProtocol.setId("6");
        mockProtocol.setTitle("Protocol Test");
        mockProtocol.setLastUpdate("2024-08-09");
    }

    @Test
    void testCreateProtocol() {
        when(protocolRepository.save(any(Protocol.class))).thenReturn(mockProtocol);

        Protocol createdProtocol = protocolService.createProtocol(mockProtocol);

        assertNotNull(createdProtocol);
        assertEquals(mockProtocol.getId(), createdProtocol.getId());
        assertEquals("Protocol Test", createdProtocol.getTitle());
        verify(protocolRepository).save(mockProtocol);
    }

    @Test
    void testGetProtocolById() {
        when(protocolRepository.findById("6")).thenReturn(Optional.of(mockProtocol));

        Optional<Protocol> protocol = protocolService.getProtocolById("6");

        assertTrue(protocol.isPresent());
        assertEquals(mockProtocol.getId(), protocol.get().getId());
        verify(protocolRepository).findById("6");
    }

    @Test
    void testUpdateProtocol() {
        when(protocolRepository.findById("6")).thenReturn(Optional.of(mockProtocol));
        when(protocolRepository.save(any(Protocol.class))).thenReturn(mockProtocol);

        mockProtocol.setTitle("Updated Protocol");
        Protocol updatedProtocol = protocolService.updateProtocol("6", mockProtocol);

        assertNotNull(updatedProtocol);
        assertEquals("Updated Protocol", updatedProtocol.getTitle());
        verify(protocolRepository).save(mockProtocol);
    }

    @Test
    void testDeleteProtocol() {
        when(protocolRepository.findById("6")).thenReturn(Optional.of(mockProtocol));

        protocolService.deleteProtocol("6");

        verify(protocolRepository).deleteById("6");
    }

    @Test
    void testGetAllProtocols() {
        List<Protocol> protocols = new ArrayList<>();
        protocols.add(mockProtocol);

        when(protocolRepository.findAll()).thenReturn(protocols);

        List<Protocol> result = protocolService.getAllProtocols();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProtocol.getId(), result.get(0).getId());
        verify(protocolRepository).findAll();
    }

    @Test
    void testGetProtocolById_NotFound() {
        when(protocolRepository.findById("6")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            protocolService.getProtocolById("6");
        });

        String expectedMessage = "Protocol not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(protocolRepository).findById("6");
    }

    @Test
    void testUpdateProtocol_NotFound() {
        when(protocolRepository.findById("6")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            protocolService.updateProtocol("6", mockProtocol);
        });

        String expectedMessage = "Protocol not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(protocolRepository).findById("6");
    }

    @Test
    void testDeleteProtocol_NotFound() {
        when(protocolRepository.findById("6")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            protocolService.deleteProtocol("6");
        });

        String expectedMessage = "Protocol not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(protocolRepository).findById("6");
    }
}
