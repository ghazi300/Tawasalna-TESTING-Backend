package com.tawasalna.business.controllers;

import com.tawasalna.business.models.ProductCategory;
import com.tawasalna.business.payload.request.ProductCategoryDTO;
import com.tawasalna.business.payload.response.CategoryResp;
import com.tawasalna.business.service.IProductCategoryService;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/product-categories")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductCategoryController {

    private final IProductCategoryService service;

    @GetMapping("/")
    public ResponseEntity<List<CategoryResp>> getCategories() {
        return this.service.getCategories();
    }

    @GetMapping("/get-photo/{catId}")
    public CompletableFuture<ResponseEntity<FileSystemResource>> getCategoryCover(@PathVariable("catId") String catId) {
        return this.service.getCategoryCover(catId);
    }

    @PostMapping("/")
    public ResponseEntity<ProductCategory> addCategory(@RequestBody ProductCategoryDTO categoryDTO) {
        return this.service.addCategory(categoryDTO);
    }

    @PatchMapping(path = "/set-photo/{catId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> setCategoryPhoto(@RequestPart("cover")MultipartFile cover, @PathVariable("catId") String catId) {
        return this.service.setCategoryPhoto(cover, catId);
    }

    @PutMapping("/update/{catId}")
    public ResponseEntity<ApiResponse> updateDetails(@RequestBody ProductCategoryDTO categoryDTO, @PathVariable("catId") String catId) {
        return this.service.updateDetails(categoryDTO, catId);
    }

    @PatchMapping("/archive/{catId}")
    public ResponseEntity<ApiResponse> archiveCategory(@PathVariable("catId") String catId) {
        return this.service.archiveCategory(catId);
    }
}
