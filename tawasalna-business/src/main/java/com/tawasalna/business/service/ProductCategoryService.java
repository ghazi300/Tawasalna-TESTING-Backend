package com.tawasalna.business.service;

import com.tawasalna.business.models.ProductCategory;
import com.tawasalna.business.payload.request.ProductCategoryDTO;
import com.tawasalna.business.payload.response.CategoryResp;
import com.tawasalna.business.repository.ProductCategoryRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class ProductCategoryService implements IProductCategoryService {

    public static final String CATEGORIES_DIR = "business\\productCategories";
    private final ProductCategoryRepository categoryRepository;
    private final IFileManagerService fileManagerService;


    @Override
    public ResponseEntity<List<CategoryResp>> getCategories() {
        return ResponseEntity.ok(
                categoryRepository
                        .findAll()
                        .stream()
                        .filter(ProductCategory::getIsActive)
                        .map(
                                c -> new CategoryResp(
                                        c.getId(),
                                        c.getName()
                                )
                        )
                        .toList()
        );
    }

    @Override
    public ResponseEntity<ProductCategory> addCategory(ProductCategoryDTO categoryDTO) {
        return new ResponseEntity<>(
                categoryRepository.save(
                        new ProductCategory(
                                null,
                                categoryDTO.getName(),
                                categoryDTO.getDescription(),
                                null,
                                true,
                                0
                        )
                ), HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<ApiResponse> setCategoryPhoto(MultipartFile cover, String catId) {
        try {

            final ProductCategory category = categoryRepository
                    .findById(catId)
                    .orElseThrow(() -> new EntityNotFoundException(Consts.CATEGORY, catId, Consts.CATEGORY_NOT_FOUND));
            if (cover == null || cover.isEmpty())
                return ResponseEntity.ok(ApiResponse.ofError("invalid file", 404));

            final String fileName = fileManagerService.uploadToLocalFileSystem(cover, CATEGORIES_DIR).get();

            if (fileName == null)
                return ResponseEntity.ok(ApiResponse.ofError("invalid file", 404));

            category.setCover(fileName);

            categoryRepository.save(category);

            return ResponseEntity.ok(ApiResponse.ofSuccess("Cover Updated", 200));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.ok(ApiResponse.ofError("Couldn't update cover", 400));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateDetails(ProductCategoryDTO categoryDTO, String catId) {
        final ProductCategory category = categoryRepository
                .findById(catId)
                .orElseThrow(() -> new EntityNotFoundException(Consts.CATEGORY, catId, Consts.CATEGORY_NOT_FOUND));

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Updated", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> archiveCategory(String catId) {
        final ProductCategory category = categoryRepository
                .findById(catId)
                .orElseThrow(() -> new EntityNotFoundException(Consts.CATEGORY, catId, Consts.CATEGORY_NOT_FOUND));

        category.setIsActive(false);
        categoryRepository.save(category);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Archived", 200));
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<FileSystemResource>> getCategoryCover(String catId) {
        try {
            final ProductCategory category = categoryRepository
                    .findById(catId)
                    .orElseThrow(() -> new EntityNotFoundException(Consts.CATEGORY, catId, Consts.CATEGORY_NOT_FOUND));

            if (category.getCover() == null)
                throw new EntityNotFoundException(Consts.CATEGORY, catId, "No cover");

            final FileSystemResource fileSystemResource = fileManagerService
                    .getFileWithMediaType(category.getCover(), CATEGORIES_DIR);

            if (fileSystemResource == null)
                throw new EntityNotFoundException(Consts.CATEGORY, catId, "No cover");

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

        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(null));
        }
    }
}
