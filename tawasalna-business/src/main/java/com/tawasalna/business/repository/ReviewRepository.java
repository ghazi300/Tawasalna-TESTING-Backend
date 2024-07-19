package com.tawasalna.business.repository;

import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Product;
import com.tawasalna.business.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findReviewsByService(BusinessService service);

    List<Review> findReviewsByProduct(Product product);

    Page<Review> findAllReviewsByService(BusinessService service, Pageable pageable);
    Page<Review> findAllReviewsByProduct(Product product, Pageable pageable);
}
