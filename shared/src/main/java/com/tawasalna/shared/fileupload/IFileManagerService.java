package com.tawasalna.shared.fileupload;


import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IFileManagerService {

    /**
     * Uploads the image to file system
     *
     * @param file   the file to upload
     * @param subDir the subDir to where it needs to save the file
     * @return file name after upload
     */
    CompletableFuture<String> uploadToLocalFileSystem(MultipartFile file, String subDir);

    /**
     * Upload many files to local file system
     * @param files - the files to upload
     * @param subDir - the subDir to where it needs to save the files
     * @return list of file names after upload
     */
    CompletableFuture<List<String>> uploadManyToLocalFileSystem(List<MultipartFile> files, String subDir);


    /**
     * Returns the file's data as file system resource
     *
     * @param fileName the image's name
     * @param subDir   the subdirectory of the image, <br>
     *                 used to know if we're saving for user or category
     * @return representation of the file
     * @throws IOException if it can't find the picture or open the directory !
     */
    FileSystemResource getFileWithMediaType(String fileName, String subDir) throws IOException;
}
