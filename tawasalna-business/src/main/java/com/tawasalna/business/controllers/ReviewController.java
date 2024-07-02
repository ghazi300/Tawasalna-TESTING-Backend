package com.tawasalna.business.controllers;

import com.tawasalna.business.models.Review;
import com.tawasalna.business.models.ReviewResponse;
import com.tawasalna.business.payload.request.ReviewDto;
import com.tawasalna.business.payload.request.ReviewResponseDTO;
import com.tawasalna.business.payload.request.ReviewResponseUpdateDTO;
import com.tawasalna.business.payload.request.ReviewUpdateDTO;
import com.tawasalna.business.service.IReviewService;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
@CrossOrigin("*")
public class ReviewController {

    private final IReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(reviewDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable("id") String id) {
        return reviewService.deleteReview(id);
    }

    @DeleteMapping("/delete-reply/{id}")
    public ResponseEntity<ApiResponse> deleteReviewReply(@PathVariable("id") String id) {
        return reviewService.deleteReviewReply(id);
    }

    @GetMapping("/reviewer-logo/{id}")
    public ResponseEntity<FileSystemResource> getReviewerLogo(@PathVariable("id") String id) throws IOException {
        return reviewService.getReviewerLogo(id);
    }

    @GetMapping("/serviceall/{serviceId}")
    public ResponseEntity<List<Review>> findReviewsByService(@PathVariable String serviceId) {
        return reviewService.getReviewsOfService(serviceId);
    }

    @GetMapping("/productAll/{productId}")
    public ResponseEntity<List<Review>> findReviewsByProduct(@PathVariable String productId) {
        return reviewService.getReviewsOfProduct(productId);
    }

    @PostMapping("/respond/{reviewId}")
    public ResponseEntity<ApiResponse> respondToReview(@PathVariable("reviewId") String reviewId, @RequestBody ReviewResponseDTO responseDTO) {
        return reviewService.respondToReview(reviewId, responseDTO);
    }

    @GetMapping("/service/paged/{serviceId}")
    public ResponseEntity<Page<Review>> getReviewsOfService(@PathVariable("serviceId") String serviceId, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        return this.reviewService.getReviewsOfServicePaged(serviceId, pageNo);
    }

    @GetMapping("/product/paged/{productId}")
    public ResponseEntity<Page<Review>> getReviewsOfProduct(@PathVariable("productId") String productId, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        return this.reviewService.getReviewsOfProductPaged(productId, pageNo);
    }

    @GetMapping("/replies/{reviewId}")
    public ResponseEntity<List<ReviewResponse>> getRepliesToReview(@PathVariable("reviewId") String reviewId) {
        return this.reviewService.getReviewReplies(reviewId);
    }

    @PutMapping("/update-response/{reviewResponseId}")
    public ResponseEntity<ApiResponse> updateReviewResponse(@PathVariable("reviewResponseId") String reviewResponseId, @RequestBody ReviewResponseUpdateDTO responseUpdateDTO) {
        return this.reviewService.updateReviewResponse(reviewResponseId, responseUpdateDTO);
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<ApiResponse> updateReview(@PathVariable("reviewId") String id, @RequestBody ReviewUpdateDTO reviewDto) {
        return this.reviewService.updateReview(id, reviewDto);
    }
}
