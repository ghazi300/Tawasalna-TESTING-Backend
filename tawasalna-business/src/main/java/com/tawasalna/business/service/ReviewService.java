package com.tawasalna.business.service;

import com.tawasalna.business.exceptions.InvalidServiceException;
import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Product;
import com.tawasalna.business.models.Review;
import com.tawasalna.business.models.ReviewResponse;
import com.tawasalna.business.payload.request.ReviewDto;
import com.tawasalna.business.payload.request.ReviewResponseDTO;
import com.tawasalna.business.payload.request.ReviewResponseUpdateDTO;
import com.tawasalna.business.payload.request.ReviewUpdateDTO;
import com.tawasalna.business.repository.*;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
@Slf4j
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BusinessServiceRepository businessServiceRepository;
    private final ProductRepository productRepository;
    private final ReviewResponseRepository reviewResponseRepository;
    private final IFileManagerService fileManagerService;

    ////////// ADD REVIEW ///////////////
    @Override
    public ResponseEntity<ApiResponse> addReview(@NotNull ReviewDto reviewDto) {

        final Review review = new Review();

        review.setReviewedAt(LocalDateTime.now());
        review.setContent(reviewDto.getContent());
        review.setNumberOfStars(reviewDto.getNumberOfStars());

        final Users owner = userRepository
                .findById(reviewDto.getOwnerId())
                .orElseThrow(() -> new
                                InvalidUserException(
                                reviewDto.getOwnerId(),
                                Consts.USER_NOT_FOUND
                        )
                );

        review.setOwner(owner);

        if (reviewDto.getServiceId() != null) {
            addReviewService(reviewDto, review);
        }

        if (reviewDto.getProductId() != null) {
            addReviewProduct(reviewDto, review);
        }

        return new ResponseEntity<>(ApiResponse.ofSuccess("Review Published !", 201), HttpStatus.CREATED);
    }

    private void addReviewService(@NotNull ReviewDto reviewDto, @NotNull Review review) {
        final Map<Integer, Integer> reviewsAggregated = getReviewsAggregated();

        final BusinessService service = businessServiceRepository
                .findById(reviewDto.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("service", reviewDto.getServiceId(), "service not found"));

        reviewRepository
                .findReviewsByService(service)
                .stream()
                .mapToInt(Review::getNumberOfStars)
                .forEach(
                        count -> reviewsAggregated.put(count, reviewsAggregated.get(count) + 1)
                );

        reviewsAggregated.put(
                reviewDto.getNumberOfStars(),
                reviewsAggregated.get(reviewDto.getNumberOfStars()) + 1
        );

        service.setTotalReviews(service.getTotalReviews() + 1);

        final float averageStars = avgReviews(reviewsAggregated, service.getTotalReviews());

        service.setAverageStars(
                averageStars == 0.0f ? reviewDto.getNumberOfStars() : Float.parseFloat(String.format("%.1f", averageStars))
        );

        review.setService(service);

        businessServiceRepository.save(service);

        reviewRepository.save(review);
    }

    private void addReviewProduct(@NotNull ReviewDto reviewDto, @NotNull Review review) {
        final Map<Integer, Integer> reviewsAggregated = getReviewsAggregated();

        final Product product = productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(Consts.PRODUCT, reviewDto.getProductId(), Consts.PRODUCT_NOT_FOUND));

        reviewRepository
                .findReviewsByProduct(product)
                .stream()
                .mapToInt(Review::getNumberOfStars)
                .forEach(
                        count -> reviewsAggregated.put(count, reviewsAggregated.get(count) + 1)
                );

        reviewsAggregated.put(
                reviewDto.getNumberOfStars(),
                reviewsAggregated.get(reviewDto.getNumberOfStars()) + 1
        );

        product.setTotalReviews(product.getTotalReviews() + 1);

        final float averageStars = avgReviews(reviewsAggregated, product.getTotalReviews());

        product.setAverageStars(
                averageStars == 0.0f ? reviewDto.getNumberOfStars() : Float.parseFloat(String.format("%.1f", averageStars))
        );

        review.setProduct(product);

        productRepository.save(product);

        reviewRepository.save(review);
    }

    ////////// DELETE REVIEW ///////////////
    @Override
    public ResponseEntity<ApiResponse> deleteReview(String id) {
        final Review review = reviewRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Consts.REVIEW, id, Consts.REVIEW_NOT_FOUND));

        reviewResponseRepository.deleteAll(reviewResponseRepository.findByReview(review));

        if (review.getService() != null) {
            deleteReviewService(review.getService(), review);
        }

        if (review.getProduct() != null) {
            deleteReviewProduct(review.getProduct(), review);
        }

        return ResponseEntity.ok(ApiResponse.ofSuccess("Review deleted", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteReviewReply(String id) {
        try {
            final ReviewResponse rep = reviewResponseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("reply", id, "reply not found"));
            reviewResponseRepository.deleteById(id);
            rep.getReview().setCountOfReplies(rep.getReview().getCountOfReplies() - 1);
            reviewRepository.save(rep.getReview());
            return ResponseEntity.ok(ApiResponse.ofSuccess("Response deleted", 200));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    ApiResponse.ofError("Response not found", 404)
                    , HttpStatus.NOT_FOUND
            );
        }
    }

    private void deleteReviewService(@NotNull BusinessService service, Review review) {
        final Map<Integer, Integer> reviewsAggregated = getReviewsAggregated();

        service.setTotalReviews(service.getTotalReviews() - 1);

        reviewRepository.delete(review);

        reviewRepository
                .findReviewsByService(service)
                .stream()
                .map(Review::getNumberOfStars)
                .forEach(count -> reviewsAggregated.put(
                                count,
                                reviewsAggregated.get(count) + 1
                        )
                );

        final float averageStars = avgReviews(
                reviewsAggregated,
                service.getTotalReviews()
        );

        service.setAverageStars(
                averageStars == 0.0f ? (float) review.getNumberOfStars() : Float.parseFloat(String.format("%.1f", averageStars))
        );

        businessServiceRepository.save(service);
    }

    private void deleteReviewProduct(@NotNull Product product, Review review) {
        final Map<Integer, Integer> reviewsAggregated = getReviewsAggregated();

        product.setTotalReviews(product.getTotalReviews() - 1);

        product = productRepository.save(product);

        reviewRepository.delete(review);

        reviewRepository
                .findReviewsByProduct(product)
                .stream()
                .map(Review::getNumberOfStars)
                .forEach(count -> reviewsAggregated.put(
                                count,
                                reviewsAggregated.get(count) + 1
                        )
                );

        final float averageStars = avgReviews(
                reviewsAggregated,
                product.getTotalReviews()
        );

        product.setAverageStars(
                averageStars == 0.0f ? review.getNumberOfStars() : Float.parseFloat(String.format("%.1f", averageStars))
        );

        productRepository.save(product);
    }

    ////////// UPDATE REVIEW ///////////////
    @Override
    public ResponseEntity<ApiResponse> updateReview(String id, ReviewUpdateDTO reviewDto) {
        final Review review = reviewRepository
                .findById(id)
                .map(r -> {
                    r.setUpdatedAt(LocalDateTime.now());
                    r.setEdited(true);
                    r.setContent(reviewDto.getContent());
                    r.setNumberOfStars(reviewDto.getNumberOfStars());
                    return reviewRepository.save(r);
                })
                .orElseThrow(() -> new EntityNotFoundException(Consts.REVIEW, id, Consts.REVIEW_NOT_FOUND));

        if (review.getService() != null) {
            updateReviewService(review);
        }

        if (review.getProduct() != null) {
            updateReviewProduct(review);
        }

        return ResponseEntity.ok(ApiResponse.ofSuccess("Review updated", 200));
    }

    private void updateReviewService(@NotNull Review review) {
        final BusinessService service = review.getService();

        final Map<Integer, Integer> reviewsAggregated = getReviewsAggregated();

        reviewRepository
                .findReviewsByService(service)
                .stream()
                .mapToInt(Review::getNumberOfStars)
                .forEach(count ->
                        reviewsAggregated
                                .put(
                                        count, reviewsAggregated.get(count) + 1
                                )
                );

        final float averageStars = avgReviews(
                reviewsAggregated,
                service.getTotalReviews()
        );

        service.setAverageStars(
                averageStars == 0.0f ? (float) review.getNumberOfStars() : Float.parseFloat(String.format("%.1f", averageStars))
        );

        reviewRepository.save(review);

        businessServiceRepository.save(service);
    }

    private void updateReviewProduct(@NotNull Review review) {
        final Product product = review.getProduct();
        final Map<Integer, Integer> reviewsAggregated = getReviewsAggregated();

        reviewRepository
                .findReviewsByProduct(product)
                .stream()
                .mapToInt(Review::getNumberOfStars)
                .forEach(count ->
                        reviewsAggregated
                                .put(
                                        count, reviewsAggregated.get(count) + 1
                                )
                );

        final float averageStars = avgReviews(
                reviewsAggregated,
                product.getTotalReviews()
        );

        product.setAverageStars(
                averageStars == 0.0f ? review.getNumberOfStars() : Float.parseFloat(String.format("%.1f", averageStars))
        );

        reviewRepository.save(review);

        productRepository.save(product);
    }

    ////////// UPDATE REVIEW RESPONSE ///////////////
    @Override
    public ResponseEntity<ApiResponse> updateReviewResponse(String reviewResponseId, ReviewResponseUpdateDTO responseUpdateDTO) {

        reviewResponseRepository
                .findById(reviewResponseId)
                .map(r -> {

                    r.setContent(responseUpdateDTO.getContent());
                    r.setMode(responseUpdateDTO.getMode());

                    return reviewResponseRepository.save(r);
                })
                .orElseThrow(() -> new EntityNotFoundException("reviewResponse", reviewResponseId, "Review response not found"));

        return ResponseEntity.ok(ApiResponse.ofSuccess("Response updated", 200));
    }

    @Override
    public ResponseEntity<FileSystemResource> getReviewerLogo(String id) throws IOException {
        final Users user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id, Consts.USER_NOT_FOUND));

        if (user.getBusinessProfile() != null) {
            return ResponseEntity.ok(fileManagerService.getFileWithMediaType(user.getBusinessProfile().getLogoName(), "\\business"));
        }

        if (user.getResidentProfile() != null)
            return ResponseEntity.ok(fileManagerService.getFileWithMediaType(user.getResidentProfile().getProfilephoto(), "\\profilePhotos"));

        return ResponseEntity.notFound().build();
    }

    ////////// GET REVIEW ///////////////
    @Override
    public ResponseEntity<List<Review>> getReviewsOfService(String serviceId) {
        final BusinessService service = businessServiceRepository
                .findById(serviceId)
                .orElseThrow(() ->
                        new InvalidServiceException(
                                serviceId,
                                "Service Not Found"
                        )
                );
        return ResponseEntity.ok(reviewRepository.findReviewsByService(service));
    }

    ////////// GET REVIEW ///////////////
    @Override
    public ResponseEntity<Page<Review>> getReviewsOfServicePaged(String serviceId, int pageNo) {
        if (pageNo <= 1) pageNo = 1;

        final BusinessService service = businessServiceRepository
                .findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("service",
                        serviceId, "Service not found"));

        return ResponseEntity.ok(
                reviewRepository
                        .findAllReviewsByService(service, PageRequest.of(pageNo - 1, 10))
        );
    }

    ////////// GET REVIEW ///////////////
    @Override
    public ResponseEntity<Page<Review>> getReviewsOfProductPaged(String productId, int pageNo) {
        if (pageNo <= 1) pageNo = 1;

        final Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Consts.PRODUCT,
                        productId, "Product not found"));

        return ResponseEntity.ok(
                reviewRepository
                        .findAllReviewsByProduct(product, PageRequest.of(pageNo - 1, 10))
        );
    }

    ////////// GET REVIEW ///////////////
    @Override
    public ResponseEntity<List<Review>> getReviewsOfProduct(String productId) {
        final Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Consts.PRODUCT,
                        productId, "Product not found"));

        return ResponseEntity.ok(
                reviewRepository
                        .findReviewsByProduct(product)
        );
    }

    ////////// GET REVIEW RESPONSE ///////////////
    @Override
    public ResponseEntity<List<ReviewResponse>> getReviewReplies(String reviewId) {
        return ResponseEntity.ok(reviewResponseRepository
                .findByReview(
                        reviewRepository.findById(reviewId)
                                .orElseThrow(() ->
                                        new EntityNotFoundException(
                                                Consts.REVIEW,
                                                reviewId,
                                                Consts.REVIEW_NOT_FOUND
                                        )
                                )
                )
        );
    }

    ////////// RESPOND TO REVIEW ///////////////
    @Override
    public ResponseEntity<ApiResponse> respondToReview(String reviewId, @NotNull ReviewResponseDTO responseDTO) {
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(Consts.REVIEW, reviewId, "Review not found"));

        final Users responder = userRepository
                .findById(responseDTO.getAuthor())
                .orElseThrow(() -> new EntityNotFoundException("user", responseDTO.getAuthor(), Consts.USER_NOT_FOUND));

        reviewResponseRepository.save(
                new ReviewResponse(
                        null,
                        responseDTO.getContent(),
                        responder,
                        review,
                        responseDTO.getMode()
                )
        );

        review.setCountOfReplies((review.getCountOfReplies() == null ? 0 : review.getCountOfReplies()) + 1);

        reviewRepository.save(review);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Response sent", 200));
    }

    private float avgReviews(@NotNull Map<Integer, Integer> reviewsAggregated, Integer total) {

        if (total == 0) {
            return 0.0f;
        }

        int sum = 0;

        for (Map.Entry<Integer, Integer> entry : reviewsAggregated.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
            sum += entry.getKey() * entry.getValue();
        }

        return (float) sum / total;
    }

    private @NotNull Map<Integer, Integer> getReviewsAggregated() {
        final Map<Integer, Integer> reviewsAggregated = new HashMap<>(0);

        reviewsAggregated.put(1, 0);
        reviewsAggregated.put(2, 0);
        reviewsAggregated.put(3, 0);
        reviewsAggregated.put(4, 0);
        reviewsAggregated.put(5, 0);

        return reviewsAggregated;
    }
}
