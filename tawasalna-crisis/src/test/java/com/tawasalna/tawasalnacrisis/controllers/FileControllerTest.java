package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.File;
import com.tawasalna.tawasalnacrisis.services.FileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadMultipleFiles() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile[] files = {file1, file2};

        File savedFile1 = new File("file1.txt", "text/plain", new byte[0]);
        File savedFile2 = new File("file2.txt", "text/plain", new byte[0]);
        List<File> savedFiles = new ArrayList<>();
        savedFiles.add(savedFile1);
        savedFiles.add(savedFile2);

        when(file1.getOriginalFilename()).thenReturn("file1.txt");
        when(file1.getContentType()).thenReturn("text/plain");
        when(file1.getBytes()).thenReturn(new byte[0]);

        when(file2.getOriginalFilename()).thenReturn("file2.txt");
        when(file2.getContentType()).thenReturn("text/plain");
        when(file2.getBytes()).thenReturn(new byte[0]);

        when(fileService.saveAttachments(files)).thenReturn(savedFiles);

        ResponseEntity<List<File>> response = fileController.uploadMultipleFiles(files);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedFiles, response.getBody());
        verify(fileService, times(1)).saveAttachments(files);
    }

    @Test
    public void testUploadMultipleFilesException() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile[] files = {file1};

        when(file1.getOriginalFilename()).thenReturn("file1.txt");
        when(file1.getContentType()).thenReturn("text/plain");
        when(file1.getBytes()).thenThrow(new Exception("File error"));

        ResponseEntity<List<File>> response = fileController.uploadMultipleFiles(files);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(fileService, times(1)).saveAttachments(files);
    }

    @Test
    public void testGetFileByNameFound() {
        String filename = "file1.txt";
        File file = new File(filename, "text/plain", new byte[0]);
        when(fileService.getFileByName(filename)).thenReturn(Optional.of(file));

        ResponseEntity<byte[]> response = fileController.getFileByName(filename);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("text/plain", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        assertEquals("attachment; filename=\"file1.txt\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(file.getData(), response.getBody());
        verify(fileService, times(1)).getFileByName(filename);
    }

    @Test
    public void testGetFileByNameNotFound() {
        String filename = "file1.txt";
        when(fileService.getFileByName(filename)).thenReturn(Optional.empty());

        ResponseEntity<byte[]> response = fileController.getFileByName(filename);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fileService, times(1)).getFileByName(filename);
    }
}
