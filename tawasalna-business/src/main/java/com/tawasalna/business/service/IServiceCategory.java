package com.tawasalna.business.service;

import com.tawasalna.business.models.ServiceCategory;
import com.tawasalna.business.payload.request.CategoryDto;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IServiceCategory {
    ServiceCategory addCategory(CategoryDto categoryDTO);

    List<ServiceCategory> getAllCategory();

    ResponseEntity<ServiceCategory> getCategoryById(String id);

    ApiResponse updateCategoryCover(MultipartFile coverPhoto, String id);

    @Async
    CompletableFuture<ResponseEntity<FileSystemResource>> getImage(String id)
            throws IOException;

    ResponseEntity<ApiResponse> updateCategory(CategoryDto categoryDto, String categoryId);

    ResponseEntity<ApiResponse> archiveCategory(String catId);
}
