package com.tawasalna.business.service;

import com.tawasalna.business.models.ProductCategory;
import com.tawasalna.business.payload.request.ProductCategoryDTO;
import com.tawasalna.business.payload.response.CategoryResp;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductCategoryService {


    ResponseEntity<List<CategoryResp>> getCategories();

    ResponseEntity<ProductCategory> addCategory(ProductCategoryDTO categoryDTO);

    ResponseEntity<ApiResponse> setCategoryPhoto(MultipartFile cover, String catId);

    ResponseEntity<ApiResponse> updateDetails(ProductCategoryDTO categoryDTO, String catId);

    ResponseEntity<ApiResponse> archiveCategory(String catId);

    CompletableFuture<ResponseEntity<FileSystemResource>> getCategoryCover(String catId);
}
