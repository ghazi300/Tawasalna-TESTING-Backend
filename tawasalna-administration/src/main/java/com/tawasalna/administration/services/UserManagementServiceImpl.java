package com.tawasalna.administration.services;

import com.tawasalna.administration.repos.UserRepository;
import com.tawasalna.administration.repos.VerificationRequestRepository;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.communityapi.service.CommunityConsumerServiceImpl;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidCommunityException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.userapi.service.UserConsumerServiceImpl;
import com.tawasalna.shared.utils.Consts;
import com.tawasalna.shared.verificationrequestapi.model.VerificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class UserManagementServiceImpl implements IUserManagementService {

    private final VerificationRequestRepository verificationRequestRepository;
    private final UserRepository userRepository;
    private final CommunityConsumerServiceImpl communityConsumerService;
    private final UserConsumerServiceImpl userConsumerService;

    @Override
    public ResponseEntity<ApiResponse> approveBusinessRequest(String id) {
        approve(id);
        return ResponseEntity
                .ok(ApiResponse
                        .ofSuccess(
                                "Business approved and notified",
                                200
                        )
                );
    }

    @Override
    public String approveBusinessRequestWS(String id) {
        return approve(id).getRequester().getId();
    }

    @Override
    public ResponseEntity<ApiResponse> rejectBusinessRequest(String id) {
        reject(id);
        return ResponseEntity
                .ok(ApiResponse
                        .ofSuccess(
                                "Business rejected and notified",
                                200
                        )
                );
    }

    @Override
    public String rejectBusinessRequestWS(String id) {
        return reject(id).getRequester().getId();
    }

    private VerificationRequest approve(String id) {
        final VerificationRequest verificationRequest =
                verificationRequestRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        "verifRequest",
                                        id,
                                        "verification request not found"
                                )
                        );

        verificationRequest.setIsAccepted(true);
        verificationRequest.setIsArchived(false);
        verificationRequest.setAcceptedAt(LocalDateTime.now());
        verificationRequestRepository.save(verificationRequest);

        final Users business = verificationRequest.getRequester();

        business.getBusinessProfile().setVerified(true);
        business.setCommunity(verificationRequest.getCommunity());
        userRepository.save(business);

        return verificationRequest;
    }

    private VerificationRequest reject(String id) {
        final VerificationRequest verificationRequest =
                verificationRequestRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        "verifRequest",
                                        id,
                                        "verification request not found"
                                )
                        );

        verificationRequest.setIsAccepted(false);
        verificationRequest.setIsArchived(true);
        verificationRequestRepository.save(verificationRequest);

        return verificationRequest;
    }

    @Override
    public ResponseEntity<Page<VerificationRequest>> getRequestsByCommunity(String communityId, Integer page) {
        if (page <= 0) page = 1;
        final Community community = communityConsumerService
                .getCommunityById(communityId)
                .orElseThrow(() ->
                        new InvalidCommunityException(
                                communityId,
                                Consts.COMMUNITY_NOT_FOUND
                        )
                );
        return ResponseEntity.ok(verificationRequestRepository
                .findAllByCommunityAndIsAcceptedFalseAndIsArchivedFalse(community, PageRequest.of(page - 1, 10)));
    }

    @Override
    public ResponseEntity<Boolean> isTherePendingRequestsFromUser(String userId) {

        final Users user = userConsumerService
                .getUserById(userId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                userId,
                                Consts.USER_NOT_FOUND
                        )
                );
        //  call a repository method to check the database
        boolean hasPendingRequests = verificationRequestRepository.existsByRequesterAndIsAcceptedFalseAndIsArchivedFalse(user);

        // Return ResponseEntity based on the result
        if (hasPendingRequests) {
            return ResponseEntity.ok(Boolean.TRUE);
        } else {
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }
}
