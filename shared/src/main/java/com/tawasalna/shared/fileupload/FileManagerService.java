package com.tawasalna.shared.fileupload;

import com.tawasalna.shared.exceptions.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.text.MessageFormat.format;

@Slf4j
@Service
public class FileManagerService implements IFileManagerService {

    private final
    Path uploaderDes = Paths.get(System.getProperty("user.home") + "\\Tawasalna");
    private final
    Path uploaderDes2 = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\PropertyImages");

    @Override
    @Async
    public CompletableFuture<String> uploadToLocalFileSystem(MultipartFile file, String subDir) {
        try {
            final Path storageDirectory = Paths.get(uploaderDes + "\\" + subDir);

            final String contentType = Objects
                    .requireNonNull(file.getContentType())
                    .split("/")[1];

            final String generatedFileName =
                    MessageFormat.format(
                            "{0}.{1}",
                            Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0],
                            contentType
                    );

            log.info("FILE TO UPLOAD: {}", generatedFileName);

            if (!Files.exists(storageDirectory))
                Files.createDirectories(storageDirectory);

            final Path destination = this.uploaderDes.resolve(
                    format("{0}\\{1}",
                            storageDirectory,
                            generatedFileName
                    )
            );

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return CompletableFuture.completedFuture(generatedFileName);

        } catch (Exception e) {
            log.trace("Failed to store file.", e);
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    @Async
    public CompletableFuture<List<String>> uploadManyToLocalFileSystem(List<MultipartFile> files, String subDir) {
        try {
            final Path storageDirectory = Paths.get(uploaderDes2 + "\\" + subDir);

            if (!Files.exists(storageDirectory)) {
                Files.createDirectories(storageDirectory);
            }

            List<String> uploadedFileNames = files.stream()
                    .map(file -> {
                        String contentType = Objects.requireNonNull(file.getContentType()).split("/")[1];
                        String generatedFileName = String.format("%s.%s",
                                Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0],
                                contentType);

                        Path destination = storageDirectory.resolve(generatedFileName);

                        try {
                            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                            return generatedFileName;
                        } catch (Exception e) {
                            // Handle exception (e.g., log error)
                            return null;
                        }
                    })
                    .toList();

            return CompletableFuture.completedFuture(uploadedFileNames);
        } catch (Exception e) {
            log.trace("Failed to store file.", e);
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public FileSystemResource getFileWithMediaType(String fileName, String subDir) {
        Path filePath = Paths.get(
                format(
                        "{0}\\{1}\\{2}",
                        uploaderDes,
                        subDir,
                        fileName
                )
        );

        if (!checkFileExistence(filePath))
            throw new StorageFileNotFoundException(filePath.toString());

        return new FileSystemResource(filePath);
    }

    private boolean checkFileExistence(Path p) {
        return p.toFile().exists();
    }
}
