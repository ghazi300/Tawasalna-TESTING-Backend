package com.tawasalna.business.service;

import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Quote;
import com.tawasalna.business.models.ServiceFeature;
import com.tawasalna.business.payload.request.FeatureRequest;
import com.tawasalna.business.repository.BusinessServiceRepository;
import com.tawasalna.business.repository.QuoteRepository;
import com.tawasalna.business.repository.ServiceFeatureRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceFeatureService implements IServiceFeature {




    private final ServiceFeatureRepository serviceFeatureRepository;


    private final BusinessServiceRepository businessServiceRepository;
    private final QuoteRepository quoteRepository;

    private final QuoteService quoteService;

   @Override
    public ResponseEntity<ApiResponse> disableFeature(String featureId, String serviceId) {
        ServiceFeature feature = serviceFeatureRepository.findById(featureId)
                .orElseThrow(() -> new EntityNotFoundException("ServiceFeature", "id", featureId));

        // Check if the feature is the only feature associated with the service
        if (!isOnlyFeatureForService(featureId, serviceId)) {
            if (!feature.isArchived()) {
                feature.setArchived(true);
                serviceFeatureRepository.save(feature);
                updateServicePrice(serviceId);
                updateQuotesForFeatureDisable(feature);
            }
            return ResponseEntity.ok(new ApiResponse("Feature disabled successfully", null, HttpStatus.OK.value()));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse("Cannot disable the only feature of the service", null, HttpStatus.BAD_REQUEST.value()));
        }
    }
    @Override
    public boolean isOnlyFeatureForService(String featureId, String serviceId) {
        BusinessService service = businessServiceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("BusinessService", "id", serviceId));

        // Check if the feature is the only feature of the service
        return service.getAdditionalFeatures().size() == 1 && service.getAdditionalFeatures().get(0).getId().equals(featureId);
    }
    @Override
    public void updateServicePrice(String serviceId) {
        BusinessService service = businessServiceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("BusinessService", "id", serviceId));

        double newPrice = service.getAdditionalFeatures().stream()
                .filter(f -> !f.isArchived())
                .mapToDouble(ServiceFeature::getPrice)
                .sum();

        service.setBasePrice(newPrice);
        businessServiceRepository.save(service);
    }
    @Override
    public void updateQuotesForFeatureDisable(ServiceFeature disabledFeature) {
        List<Quote> quotes = quoteRepository.findQuotesByChosenFeatures_Id(disabledFeature.getId());

        for (Quote quote : quotes) {
            // check if the disabled feature is the only chosen feature in the quote
            if (quote.getChosenFeatures().size() == 1 && quote.getChosenFeatures().contains(disabledFeature)) {
                // remove the quote since it only contains the disabled feature
                quoteService.rejectQuote(quote.getId()); // Assuming you have a method to reject quotes
            } else {
                // remove the disabled feature from the quote's feature list
                quote.getChosenFeatures().removeIf(f -> f.getId().equals(disabledFeature.getId()));
                // recalculate quote price
                float totalPrice = calculateNewQuotePrice(quote);
                quote.setPriceTag(totalPrice);
                quoteRepository.save(quote);
            }
        }
    }


    private float calculateNewQuotePrice(Quote quote) {
        double totalPrice = quote.getChosenFeatures().stream()
                .mapToDouble(ServiceFeature::getPrice)
                .sum();

        return (float) totalPrice;
    }
    @Override
    @Transactional
    public ResponseEntity<ApiResponse> addFeatureToService(String serviceId, FeatureRequest featureRequest) {
        BusinessService service = businessServiceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("BusinessService", "id", serviceId));

        ServiceFeature newFeature = new ServiceFeature();
        newFeature.setTitle(featureRequest.getTitle());
        newFeature.setDescription(featureRequest.getDescription());
        newFeature.setPrice(featureRequest.getPrice());
        newFeature.setArchived(false);

        ServiceFeature updated= serviceFeatureRepository.save(newFeature);
        service.getAdditionalFeatures().add(updated);
        businessServiceRepository.save(service);
        updateServicePrice(serviceId);


        return ResponseEntity.ok(new ApiResponse("Feature added successfully", null, HttpStatus.OK.value()));
    }
    @Override
    public ResponseEntity<ApiResponse> enableFeature(String featureId, String serviceId) {
        ServiceFeature feature = serviceFeatureRepository.findById(featureId)
                .orElseThrow(() -> new EntityNotFoundException("ServiceFeature", "id", featureId));

        // Check if the feature is already enabled
        if (!feature.isArchived()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Feature is already enabled", null, HttpStatus.BAD_REQUEST.value()));
        }
        // Enable the feature
        feature.setArchived(false);
        serviceFeatureRepository.save(feature);
        // Update service price after enabling the feature
        updateServicePrice(serviceId);

        return ResponseEntity.ok(new ApiResponse("Feature enabled successfully", null, HttpStatus.OK.value()));
    }
}
