package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.File;
import com.ipactconsult.tawasalnabackendapp.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor

public class FileService {
    private final FileRepository fileRepository;
    public File saveFile(MultipartFile file) throws IOException {
        File fileModel = File.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(file.getBytes())

                .build();

        return fileRepository.save(fileModel);
    }

    public File getFile(String id) {
        return fileRepository.findById(id).orElse(null);
    }

    public Resource loadFileAsResource(String id) {
        File fileModel = getFile(id);
        return new ByteArrayResource(fileModel.getData());
    }
}
