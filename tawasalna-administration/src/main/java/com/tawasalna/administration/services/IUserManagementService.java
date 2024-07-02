package com.tawasalna.administration.services;


import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.verificationrequestapi.model.VerificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IUserManagementService {
    String approveBusinessRequestWS(String id);

    String rejectBusinessRequestWS(String id);

    ResponseEntity<ApiResponse> approveBusinessRequest(String id);

    ResponseEntity<ApiResponse> rejectBusinessRequest(String id);

    ResponseEntity<Page<VerificationRequest>> getRequestsByCommunity(String communityId, Integer page);

    ResponseEntity<?> isTherePendingRequestsFromUser(String userId);
}
