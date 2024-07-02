package com.tawasalna.business.controllers;

import com.tawasalna.business.models.Product;
import com.tawasalna.business.payload.request.ProductDTO;
import com.tawasalna.business.payload.response.ProductCreateResp;
import com.tawasalna.business.service.IProductService;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/findOne/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/publish")
    public ResponseEntity<ProductCreateResp> publishProduct(@RequestBody ProductDTO productDTO) {
        return this.productService.publishProduct(productDTO);
    }

    @PatchMapping(path = "/update-photo/{productId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse> setProductPhoto(@RequestPart(value = "productImage") MultipartFile file,
            @PathVariable("productId") String productId) {
        return this.productService.setProductPhoto(file, productId);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") String productId,
            @RequestBody ProductDTO productDTO) {
        return this.productService.updateProduct(productId, productDTO);
    }

    @PatchMapping("/archive/{productId}")
    public ResponseEntity<ApiResponse> archiveProduct(@PathVariable("productId") String productId) {
        return this.productService.archiveProduct(productId);
    }

    @PatchMapping("/unarchive/{productId}")
    public ResponseEntity<ApiResponse> unarchiveProduct(@PathVariable("productId") String productId) {
        return this.productService.unarchiveProduct(productId);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<Product>> getProductsByCategory(@PathVariable("categoryId") String categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return this.productService.getProductsByCategory(categoryId, page);
    }

    @GetMapping("/get-by-shop/{shopId}")
    public ResponseEntity<Page<Product>> getProductsByShop(@PathVariable("shopId") String shopId,
            @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return this.productService.getProductsByShop(shopId, page);
    }

    @GetMapping("/get-by-shop-archived/{shopId}")
    public ResponseEntity<Page<Product>> getProductsByShopArchived(@PathVariable("shopId") String shopId,
            @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return this.productService.getProductsByShopArchived(shopId, page);
    }

    @GetMapping("/getProducts/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        return new ResponseEntity<>(this.productService.getProductsActive(), HttpStatus.OK);
    }

    @GetMapping("/getProducts/activePage")
    public ResponseEntity<Page<Product>> getActivePage(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return this.productService.getProductsActivePaginated(page);
    }

    @GetMapping("/image/{productId}")
    public ResponseEntity<FileSystemResource> getProductImage(@PathVariable("productId") String productId) {
        return this.productService.getProductImage(productId);
    }

    @GetMapping("/filter")
    public Page<Product> filterProducts(
            @RequestParam(required = false, defaultValue = "") String searchString,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,

            @RequestParam(required = false) String productCategoryId,
            @RequestParam(required = false) Boolean isArchived,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        return this.productService.filterProducts(searchString, minPrice, maxPrice, productCategoryId, isArchived,
                page);
    }
}
