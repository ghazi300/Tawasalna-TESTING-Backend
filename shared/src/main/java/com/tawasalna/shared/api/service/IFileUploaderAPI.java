package com.tawasalna.shared.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IFileUploaderAPI {

    Optional<String> sendFileToServer(MultipartFile file, String subDir);

    Optional<List<String>> sendFilesToServer(List<MultipartFile> files, String subDir);
}
