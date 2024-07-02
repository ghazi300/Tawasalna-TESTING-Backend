package com.tawasalna.business.controllers;

import com.tawasalna.business.models.ServiceCategory;
import com.tawasalna.business.payload.request.CategoryDto;
import com.tawasalna.business.service.IServiceCategory;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/service-category")
@AllArgsConstructor
@CrossOrigin("*")
public class ServiceCategoryController {
    private final IServiceCategory serviceCategory;

    @PostMapping("/add")
    public ResponseEntity<ServiceCategory> addCategory(@RequestBody CategoryDto categoryDTO) {
        return new ResponseEntity<>(
                serviceCategory.addCategory(categoryDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ServiceCategory>> findAll() {
        return new ResponseEntity<>(serviceCategory.getAllCategory(), HttpStatus.OK);
    }

    @GetMapping("/get-photo/{id}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable("id") String id)
            throws IOException, ExecutionException, InterruptedException {
        return serviceCategory.getImage(id).get();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ServiceCategory> getSCategoryById(@PathVariable String categoryId) {
        return serviceCategory.getCategoryById(categoryId);
    }

    @PatchMapping(path = "/update-cover/{categoryId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> updateLogo(
            @PathVariable("categoryId") String categoryId,
            @RequestPart(value = "logo") MultipartFile logo
    ) {
        final ApiResponse resp = serviceCategory.updateCategoryCover(logo, categoryId);
        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategoryDetails(
            @RequestBody CategoryDto categoryDto,
            @PathVariable("categoryId") String categoryId
    ) {
        return serviceCategory.updateCategory(categoryDto, categoryId);
    }
}
