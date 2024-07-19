package com.tawasalna.business.repository;

import com.tawasalna.business.models.Review;
import com.tawasalna.business.models.ReviewResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewResponseRepository extends MongoRepository<ReviewResponse, String> {

    List<ReviewResponse> findByReview(Review review);
}
