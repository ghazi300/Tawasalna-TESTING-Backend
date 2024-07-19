package com.tawasalna.business.service;

import com.tawasalna.business.models.ServiceFeature;
import com.tawasalna.business.payload.request.FeatureRequest;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IServiceFeature {
    ResponseEntity<ApiResponse> disableFeature(String featureId, String serviceId);

    boolean isOnlyFeatureForService(String featureId, String serviceId);

    void updateServicePrice(String serviceId);

    void updateQuotesForFeatureDisable(ServiceFeature disabledFeature);



    @Transactional
    ResponseEntity<ApiResponse> addFeatureToService(String serviceId, FeatureRequest featureRequest);

    ResponseEntity<ApiResponse> enableFeature(String featureId, String serviceId);
}
