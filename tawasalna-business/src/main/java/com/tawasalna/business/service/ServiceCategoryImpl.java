package com.tawasalna.business.service;

import com.tawasalna.business.models.ServiceCategory;
import com.tawasalna.business.payload.request.CategoryDto;
import com.tawasalna.business.repository.ServiceCategoryRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidServiceCategoryException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceCategoryImpl implements IServiceCategory {
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final IFileManagerService fileManager;

    @Override
    public ServiceCategory addCategory(CategoryDto categoryDTO) {

        if (Boolean.TRUE.equals(serviceCategoryRepository.existsByTitle(categoryDTO.getTitle())))
            throw new InvalidEntityBaseException("Duplicate", categoryDTO.getTitle(), "Title Duplicate");

        return serviceCategoryRepository.save(mapCategoryRequestToEntity(categoryDTO));
    }

    @Override
    public List<ServiceCategory> getAllCategory() {
        return serviceCategoryRepository.findAll();
    }

    private ServiceCategory mapCategoryRequestToEntity(CategoryDto categoryDto) {
        ServiceCategory category = new ServiceCategory();
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setImageUrl(categoryDto.getImageUrl());
        category.setServiceCount(categoryDto.getServiceCount());
        category.setIsActive(true);
        return category;
    }

    @Override
    public ResponseEntity<ServiceCategory> getCategoryById(String id) {

        return ResponseEntity.ok(
                serviceCategoryRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidServiceCategoryException(id, "Category Not Found"

                                )
                        )
        );

    }

    @Override
    public ApiResponse updateCategoryCover(MultipartFile coverPhoto, String id) {
        final String fileName = saveFileToDisk(coverPhoto);

        final ServiceCategory category = serviceCategoryRepository
                .findById(id)
                .orElseThrow(() -> new InvalidServiceCategoryException(id, "Category Not Found"));

        category.setImageUrl(fileName);
        serviceCategoryRepository.save(category);

        return ApiResponse.ofSuccess("Success", 200);
    }

    private String saveFileToDisk(MultipartFile file) {
        try {
            if (file == null || file.isEmpty())
                throw new InvalidEntityBaseException("file", "null", "invalid file");

            final String fileName = fileManager.uploadToLocalFileSystem(file, "serviceCategories").get();

            if (fileName == null)
                throw new InvalidEntityBaseException("file", "null", "no file name");

            return fileName;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<FileSystemResource>> getImage(String id) throws IOException {

        final ServiceCategory category = serviceCategoryRepository
                .findById(id)
                .orElseThrow(() -> new InvalidServiceCategoryException(
                                id,
                                "Category Not Found"
                        )
                );

        String coverPhotoName = category.getImageUrl();

        return coverPhotoName == null ? getLocalPlaceholder() : getPhotoByName(coverPhotoName);
    }

    @NotNull
    private CompletableFuture<ResponseEntity<FileSystemResource>> getLocalPlaceholder() throws IOException {

        final ClassPathResource res = new ClassPathResource("static/category-default.jpg");

        FileSystemResource fileSystemResource = new FileSystemResource(res.getFile());

        final String mediaType = Files
                .probeContentType(
                        fileSystemResource.getFile()
                                .toPath()
                );

        final ResponseEntity<FileSystemResource> resp = ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .body(fileSystemResource);

        return CompletableFuture.completedFuture(resp);
    }

    @NotNull
    private CompletableFuture<ResponseEntity<FileSystemResource>>
    getPhotoByName(String name)
            throws IOException {

        if (name == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        final FileSystemResource fileSystemResource = fileManager
                .getFileWithMediaType(name, "serviceCategories");

        if (fileSystemResource == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        final String mediaType = Files
                .probeContentType(
                        fileSystemResource.getFile()
                                .toPath()
                );

        final ResponseEntity<FileSystemResource> resp = ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .body(fileSystemResource);

        return CompletableFuture.completedFuture(resp);
    }

    @Override
    public ResponseEntity<ApiResponse> updateCategory(CategoryDto categoryDto, String categoryId) {


        final ServiceCategory updated = serviceCategoryRepository
                .findById(categoryId)
                .map(c -> {
                    c.setTitle(categoryDto.getTitle());
                    c.setDescription(categoryDto.getDescription());
                    return serviceCategoryRepository.save(c);
                })
                .orElseThrow(() -> new InvalidServiceCategoryException(categoryId, "Category not found"));

        log.info(updated.toString());

        return ResponseEntity.ok(new ApiResponse("Category updated successfully!", null, 200));
    }
    @Override
    public ResponseEntity<ApiResponse> archiveCategory(String catId) {
        final ServiceCategory category = serviceCategoryRepository
                .findById(catId)
                .orElseThrow(() -> new EntityNotFoundException(Consts.CATEGORY, catId, Consts.CATEGORY_NOT_FOUND));

        category.setIsActive(false);
        serviceCategoryRepository.save(category);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Archived", 200));
    }
}
