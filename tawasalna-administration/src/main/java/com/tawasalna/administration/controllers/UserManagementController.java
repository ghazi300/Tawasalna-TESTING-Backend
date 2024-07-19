package com.tawasalna.administration.controllers;

import com.tawasalna.administration.services.IUserManagementService;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.verificationrequestapi.model.VerificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/users")

@RestController
public class UserManagementController {

    private final IUserManagementService userManagementService;

    @PatchMapping("/approve-business/{id}")
    public ResponseEntity<ApiResponse> approveBusinessRequest(@PathVariable("id") String id) {
        return userManagementService.approveBusinessRequest(id);
    }

    @GetMapping("/get-requests/{communityId}")
    public ResponseEntity<Page<VerificationRequest>> getRequestsOfCommunity(
            @PathVariable("communityId") String communityId,
            @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        return userManagementService.getRequestsByCommunity(communityId, page);
    }

    @GetMapping("/getmyRequests/{userId}")
    public ResponseEntity<?> isTherePendingRequestsFromUser(
            @PathVariable("userId") String userId
    ) {
        return userManagementService.isTherePendingRequestsFromUser(userId);
    }

    @PatchMapping("/reject-business/{id}")
    public ResponseEntity<ApiResponse> rejectBusinessRequest(@PathVariable("id") String id) {
        return userManagementService.rejectBusinessRequest(id);
    }
}
