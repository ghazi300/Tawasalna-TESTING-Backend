package com.tawasalna.business.service;

import com.tawasalna.business.models.Product;
import com.tawasalna.business.models.ProductCategory;
import com.tawasalna.business.payload.request.ProductDTO;
import com.tawasalna.business.payload.response.ProductCreateResp;
import com.tawasalna.business.repository.ProductCategoryRepository;
import com.tawasalna.business.repository.ProductRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.userapi.service.IUserConsumerService;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService implements IProductService {

    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final IFileManagerService fileManager;
    private final IUserConsumerService userConsumerService;

    @Override
    public ResponseEntity<ProductCreateResp> publishProduct(ProductDTO productDTO) {
        final ProductCategory category = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(productDTO.getCategoryId(),
                                "Category",
                                "Category not found"
                        )
                );

        final Users owner = userConsumerService
                .getUserById(productDTO.getPublisherId())
                .orElseThrow(() ->
                        new EntityNotFoundException(productDTO.getCategoryId(),
                                "user",
                                Consts.USER_NOT_FOUND
                        )
                );

        final Product published = productRepository.save(
                new Product(
                        null,
                        productDTO.getTitle(),
                        productDTO.getDescription(),
                        null,
                        productDTO.getPrice(),
                        false,
                        owner,
                        category,
                        0,
                        0.0f
                )
        );

        category.setProductsCount(category.getProductsCount() + 1);
        categoryRepository.save(category);

        return new ResponseEntity<>(
                new ProductCreateResp(published.getId()),
                HttpStatusCode.valueOf(201)
        );
    }

    @Override
    public ResponseEntity<ApiResponse> setProductPhoto(MultipartFile file, String productId) {
        try {
            if (file == null || file.isEmpty())
                throw new InvalidEntityBaseException("file", "null", "invalid file");

            final String fileName = fileManager.uploadToLocalFileSystem(file, "business\\products").get();

            if (fileName == null)
                throw new InvalidEntityBaseException("file", "null", "no file name");

            final Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("product", productId, "Product not found"));

            product.setImage(fileName);

            productRepository.save(product);

            return ResponseEntity.ok(ApiResponse.ofSuccess("Product photo uploaded", 200));

        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.badRequest().body(ApiResponse.ofError("Couldn't upload image", 400));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateProduct(String productId, ProductDTO productDTO) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("product", productId, "Product not found"));

        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Product updated", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> archiveProduct(String productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        "product",
                                        productId,
                                        "Product not found"
                                )
                );

        product.setIsArchived(true);
        productRepository.save(product);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Product updated", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> unarchiveProduct(String productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        "product",
                                        productId,
                                        "Product not found"
                                )
                );

        product.setIsArchived(false);
        productRepository.save(product);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Product updated", 200));
    }

    @Override
    public ResponseEntity<FileSystemResource> getProductImage(String productId) {
        try {

            final Product product = productRepository.findById(productId)
                    .orElseThrow(
                            () ->
                                    new EntityNotFoundException(
                                            "product",
                                            productId,
                                            "Product not found"
                                    )
                    );

            return this.getPhotoByName(product.getImage()).get();
        } catch (InterruptedException | ExecutionException | IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
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
                .getFileWithMediaType(name, "business\\products");

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
    public ResponseEntity<Page<Product>> getProductsByCategory(String categoryId, Integer page) {
        if (page <= 1) page = 1;
        final ProductCategory category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new EntityNotFoundException(categoryId,
                                "Category",
                                "Category not found"
                        )
                );

        return ResponseEntity.ok(
                productRepository.findByProductCategoryAndIsArchivedFalse(
                        category,
                        PageRequest.of(page - 1, 10)
                )
        );
    }

    @Override
    public ResponseEntity<Page<Product>> getProductsByShop(String shopId, Integer page) {
        if (page <= 1) page = 1;
        final Users owner = userConsumerService
                .getUserById(shopId)
                .orElseThrow(() ->
                        new EntityNotFoundException(shopId,
                                "user",
                                Consts.USER_NOT_FOUND
                        )
                );

        return ResponseEntity.ok(
                productRepository.findByPublisherAndIsArchivedFalse(
                        owner,
                        PageRequest.of(page - 1, 10)
                )
        );
    }

    @Override
    public ResponseEntity<Product> getProductById(String productId) {
        return ResponseEntity.ok(productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("product", productId, "Product not found")));
    }

    @Override
    public ResponseEntity<Page<Product>> getProductsByShopArchived(String shopId, Integer page) {
        if (page <= 1) page = 1;
        final Users owner = userConsumerService
                .getUserById(shopId)
                .orElseThrow(() ->
                        new EntityNotFoundException(shopId,
                                "user",
                                Consts.USER_NOT_FOUND
                        )
                );


        return ResponseEntity.ok(
                productRepository.findByPublisherAndIsArchivedTrue(
                        owner,
                        PageRequest.of(page - 1, 10)
                )
        );
    }

    @Override
    public List<Product> getProductsActive() {
        return productRepository.findAllByIsArchivedFalse();
    }

    @Override
    public ResponseEntity<Page<Product>> getProductsActivePaginated(Integer page) {
        if (page <= 1) page = 1;



        return ResponseEntity.ok(
                productRepository.findAll(
                        PageRequest.of(page - 1, 9)
                )
        );
    }
    @Override
    public Page<Product> filterProducts(String searchString, Double minPrice, Double maxPrice, String productCategoryId, Boolean isArchived, int page) {



        int pageSize = 9;




        // Retrieve page of objects from the database
        List<Product> allServices = productRepository.findAll();

        // Log input parameters
        System.out.println("Filtering products with parameters: searchString=" + searchString + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", productCategoryId=" + productCategoryId +  ", isArchived=" + isArchived + ", page=" + page);

        // Apply filtering conditions based on parameters
        List<Product> filteredProducts = allServices.stream()
                .filter(product -> {
                    boolean match = searchString.isEmpty() ||
                            product.getTitle().toLowerCase().contains(searchString.toLowerCase()) ||
                            product.getDescription().toLowerCase().contains(searchString.toLowerCase()) ;

                    System.out.println("Product " + product.getTitle() + " matched searchString: " + match);
                    return match;
                })
                .filter(product -> {
                    boolean match = minPrice == null || product.getPrice() >= minPrice;
                    System.out.println("Product " + product.getTitle() + " matched minPrice: " + match);
                    return match;
                })
                .filter(product -> {
                    boolean match = maxPrice == null || product.getPrice() <= maxPrice;
                    System.out.println("Product " + product.getTitle() + " matched maxPrice: " + match);
                    return match;
                })


                .filter(product -> {
                    boolean match = productCategoryId == null || product.getProductCategory().getId().equals(productCategoryId);
                    System.out.println("Product " + product.getTitle() + " matched productCategoryId: " + match);
                    return match;
                })





                .filter(product -> {
                    boolean match = isArchived == null || product.getIsArchived().equals(isArchived);
                    System.out.println("Service " + product.getTitle() + " matched isArchived: " + match);
                    return match;
                })
                .collect(Collectors.toList());

        // Log the number of filtered products
        System.out.println("Number of filtered products: " + filteredProducts.size());
        int totalPages = (int) Math.ceil((double) filteredProducts.size() / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, filteredProducts.size());
        List<Product> pageContent = filteredProducts.subList(startIndex, endIndex);

        // Create a pageable object for the current page
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // Return the paginated list as a page object with correct pagination information
        return new PageImpl<>(pageContent, pageable, filteredProducts.size());
    }

}
