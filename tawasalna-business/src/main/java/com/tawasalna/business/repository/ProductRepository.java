package com.tawasalna.business.repository;

import com.tawasalna.business.models.Product;
import com.tawasalna.business.models.ProductCategory;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findByProductCategoryAndIsArchivedFalse(
            ProductCategory productCategory,
            Pageable pageable
    );

    Page<Product> findByPublisherAndIsArchivedFalse(Users publisher, Pageable pageable);

    Page<Product> findByPublisherAndIsArchivedTrue(Users publisher, Pageable pageable);

    List<Product> findAllByTitleLikeIgnoreCaseAndIsArchivedFalse(String title);
    List<Product> findAllByIsArchivedFalse();
}
