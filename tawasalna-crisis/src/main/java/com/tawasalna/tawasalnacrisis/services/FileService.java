package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {
    List<File> saveAttachments(MultipartFile[] files) throws Exception;
    Optional<File> getFileById(String id);

    List<File> getAllFiles();
}
