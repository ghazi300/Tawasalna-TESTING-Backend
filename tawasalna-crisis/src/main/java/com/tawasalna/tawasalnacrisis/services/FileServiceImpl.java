package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.File;
import com.tawasalna.tawasalnacrisis.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepository fileRepository;

    public List<File> saveAttachments(MultipartFile[] files) throws Exception {
        List<File> attachments = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                if (fileName.contains("..")) {
                    throw new Exception("Filename contains invalid path sequence " + fileName);
                }

                // Convert the file data to byte[]
                byte[] data = file.getBytes();

                File attachment = new File(fileName, file.getContentType(), data);
                attachments.add(fileRepository.save(attachment));
            } catch (Exception e) {
                throw new Exception("Could not save file: " + fileName, e);
            }
        }
        return attachments;
    }

    @Override
    public Optional<File> getFileByName(String filename) {
        return fileRepository.findByFileName(filename);
    }
}