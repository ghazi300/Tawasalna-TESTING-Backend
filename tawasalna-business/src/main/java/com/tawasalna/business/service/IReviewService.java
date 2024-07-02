package com.tawasalna.business.service;

import com.tawasalna.business.models.Review;
import com.tawasalna.business.models.ReviewResponse;
import com.tawasalna.business.payload.request.ReviewDto;
import com.tawasalna.business.payload.request.ReviewResponseDTO;
import com.tawasalna.business.payload.request.ReviewResponseUpdateDTO;
import com.tawasalna.business.payload.request.ReviewUpdateDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IReviewService {

    ResponseEntity<ApiResponse> addReview(ReviewDto reviewDto);

    ResponseEntity<ApiResponse> deleteReview(String id);

    ResponseEntity<ApiResponse> deleteReviewReply(String id);

    ResponseEntity<List<Review>> getReviewsOfService(String serviceId);

    ResponseEntity<List<Review>> getReviewsOfProduct(String productId);

    ResponseEntity<ApiResponse> updateReview(String id, ReviewUpdateDTO reviewDto);

    ResponseEntity<Page<Review>> getReviewsOfServicePaged(String serviceId, int pageNo);

    ResponseEntity<Page<Review>> getReviewsOfProductPaged(String productId, int pageNo);

    ResponseEntity<List<ReviewResponse>> getReviewReplies(String reviewId);

    ResponseEntity<ApiResponse> respondToReview(String reviewId, ReviewResponseDTO responseDTO);

    ResponseEntity<ApiResponse> updateReviewResponse(String reviewResponseId, ReviewResponseUpdateDTO responseUpdateDTO);

    ResponseEntity<FileSystemResource> getReviewerLogo(String id) throws IOException;
}
