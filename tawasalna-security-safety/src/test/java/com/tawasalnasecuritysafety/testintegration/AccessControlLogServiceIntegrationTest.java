package com.tawasalnasecuritysafety.testintegration;

import com.tawasalnasecuritysafety.models.AccessControlLog;
import com.tawasalnasecuritysafety.repos.AccessControlLogRepository;
import com.tawasalnasecuritysafety.services.AccessControlLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccessControlLogServiceIntegrationTest {

    @Mock
    private AccessControlLogRepository accessControlLogRepository;

    @InjectMocks
    private AccessControlLogService accessControlLogService;

    private AccessControlLog mockLog;

    @BeforeEach
    void setUp() {
        mockLog = new AccessControlLog();
        mockLog.setId("1");
        mockLog.setName("John Doe");
        mockLog.setLocation("Entrance");
        mockLog.setTime(LocalDateTime.parse("2024-08-09T10:00:00"));
        mockLog.setType("Entry");
    }

    @Test
    void testCreateAccessControlLog() {
        when(accessControlLogRepository.save(any(AccessControlLog.class))).thenReturn(mockLog);

        AccessControlLog createdLog = accessControlLogService.createAccessControlLog(mockLog);

        assertNotNull(createdLog);
        assertEquals(mockLog.getId(), createdLog.getId());
        assertEquals("John Doe", createdLog.getName());
        verify(accessControlLogRepository).save(mockLog);
    }

    @Test
    void testGetAccessControlLogById() {
        when(accessControlLogRepository.findById("1")).thenReturn(Optional.of(mockLog));

        Optional<AccessControlLog> log = accessControlLogService.getAccessControlLogById("1");

        assertTrue(log.isPresent());
        assertEquals(mockLog.getId(), log.get().getId());
        verify(accessControlLogRepository).findById("1");
    }

    @Test
    void testUpdateAccessControlLog() {
        when(accessControlLogRepository.findById("1")).thenReturn(Optional.of(mockLog));
        when(accessControlLogRepository.save(any(AccessControlLog.class))).thenReturn(mockLog);

        mockLog.setName("Jane Doe");
        AccessControlLog updatedLog = accessControlLogService.updateAccessControlLog("1", mockLog);

        assertNotNull(updatedLog);
        assertEquals("Jane Doe", updatedLog.getName());
        verify(accessControlLogRepository).save(mockLog);
    }

    @Test
    void testDeleteAccessControlLog() {
        when(accessControlLogRepository.findById("1")).thenReturn(Optional.of(mockLog));

        accessControlLogService.deleteAccessControlLog("1");

        verify(accessControlLogRepository).deleteById("1");
    }

    @Test
    void testGetAllAccessControlLogs() {
        List<AccessControlLog> logs = new ArrayList<>();
        logs.add(mockLog);

        when(accessControlLogRepository.findAll()).thenReturn(logs);

        List<AccessControlLog> result = accessControlLogService.getAllAccessControlLogs();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockLog.getId(), result.get(0).getId());
        verify(accessControlLogRepository).findAll();
    }

    @Test
    void testGetAccessControlLogById_NotFound() {
        when(accessControlLogRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessControlLogService.getAccessControlLogById("1");
        });

        String expectedMessage = "AccessControlLog not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(accessControlLogRepository).findById("1");
    }

    @Test
    void testUpdateAccessControlLog_NotFound() {
        when(accessControlLogRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessControlLogService.updateAccessControlLog("1", mockLog);
        });

        String expectedMessage = "AccessControlLog not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(accessControlLogRepository).findById("1");
    }

    @Test
    void testDeleteAccessControlLog_NotFound() {
        when(accessControlLogRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accessControlLogService.deleteAccessControlLog("1");
        });

        String expectedMessage = "AccessControlLog not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(accessControlLogRepository).findById("1");
    }
}
