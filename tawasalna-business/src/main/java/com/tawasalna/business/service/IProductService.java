package com.tawasalna.business.service;

import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Product;
import com.tawasalna.business.payload.request.ProductDTO;
import com.tawasalna.business.payload.response.ProductCreateResp;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// needs testing
public interface IProductService {

    ResponseEntity<ProductCreateResp> publishProduct(ProductDTO productDTO);

    ResponseEntity<ApiResponse> setProductPhoto(MultipartFile file, String productId);

    ResponseEntity<ApiResponse> updateProduct(String productId, ProductDTO productDTO);

    ResponseEntity<ApiResponse> archiveProduct(String productId);
    ResponseEntity<ApiResponse> unarchiveProduct(String productId);

    ResponseEntity<FileSystemResource> getProductImage(String productId);

    ResponseEntity<Page<Product>> getProductsByCategory(String categoryId, Integer page);

    ResponseEntity<Page<Product>> getProductsByShop(String shopId, Integer page);

    ResponseEntity<Product> getProductById(String productId);

    ResponseEntity<Page<Product>> getProductsByShopArchived(String shopId, Integer page);

    List<Product> getProductsActive();
    ResponseEntity <Page<Product>> getProductsActivePaginated(Integer page);

    Page<Product> filterProducts(String searchString, Double minPrice, Double maxPrice, String productCategoryId, Boolean isArchived, int page);
}
