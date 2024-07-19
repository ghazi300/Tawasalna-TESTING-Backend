package com.tawasalna.social.service;

import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidCommunityException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.userapi.model.RolesEnum;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.userapi.service.IUserConsumerService;
import com.tawasalna.shared.utils.Consts;
import com.tawasalna.social.repository.CommunityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
@Slf4j
public class CommunityServiceImpl implements ICommunityService {

    private final CommunityRepository communityRepository;
    private final IUserConsumerService userConsumerService;


    @Override
    public List<Users> getAllUsersInCommunity(String communityId) {
        userConsumerService.getUsersByCommunityId(communityId);
        return userConsumerService.getUsersByCommunityId(communityId);
    }

    @Override
    public List<Community> getAll() {

        return communityRepository.findAll();
    }

    @Override
    public ResponseEntity<ApiResponse> addAdminToCommunity(String communityId, String userId) {
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();


            Users user = userConsumerService.getUserById(userId)
                    .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

            Set<Users> admins = new HashSet<>(community.getAdmins());
            if (admins.stream().noneMatch(admin -> admin.getId().equals(userId))) {
                admins.add(user);
                community.setAdmins(admins);
                communityRepository.save(community);

                // Set ROLE_COMMUNITY_ADMIN role via UserConsumerService
                userConsumerService.addRCA(user);
                userConsumerService.addUserToCommunity(user, communityId);

                return ResponseEntity.ok(ApiResponse.ofSuccess("Admin added to community", 200));
            } else {
                return ResponseEntity.ok(ApiResponse.ofError("User is already a community admin", 400));
            }
        } else {
            return ResponseEntity.ok(ApiResponse.ofError("User not found", 404));
        }


    }


    @Override
    public ResponseEntity<Community> getCommunityById(String id) {
        return ResponseEntity.ok(
                communityRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidCommunityException(id, "Community Not Found"

                                )
                        )
        );
    }


    @Override
    public ResponseEntity<ApiResponse> removeAdminFromCommunity(String communityId, String userId) {
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);

        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            if (community.getAdmins() != null) {
                // Remove the user from the current community's admin list
                boolean removed = community.getAdmins().removeIf(user -> user.getId().equals(userId));

                if (removed) {
                    // Save the updated community before proceeding
                    communityRepository.save(community);

                    // Retrieve the user by userId
                    Users user = userConsumerService.getUserById(userId).orElse(null);
                    if (user != null) {
                        // Check if the user is an admin of any other community
                        List<Community> otherCommunities = communityRepository.findByAdmins_Id(userId);
                        if (otherCommunities.isEmpty()) {
                            // Remove ROLE_COMMUNITY_ADMIN role from the user
                            user.getRoles().removeIf(role -> role.getName().equals(RolesEnum.ROLE_COMMUNITY_ADMIN.name()));
                            userConsumerService.removeRCA(user);
                        }
                    }
                }

                return ResponseEntity.ok(ApiResponse.ofSuccess("Admin removed from community", 200));
            } else {
                return ResponseEntity.ok(ApiResponse.ofError("Community has no admins", 400));
            }
        } else {
            return ResponseEntity.ok(ApiResponse.ofError(Consts.COMMUNITY_NOT_FOUND, 404));
        }
    }


    @Override
    public ResponseEntity<ApiResponse> updateCommunity(String communityId, String name, String description) {
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            community.setName(name);
            community.setDescription(description);
            communityRepository.save(community);
            return ResponseEntity.ok(ApiResponse.ofSuccess("Community updated successfully", 200));
        } else {
            return ResponseEntity.ok(ApiResponse.ofError("Community not found", 404));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCommunity(String communityId) {
        communityRepository.deleteById(communityId);
        return ResponseEntity.ok(ApiResponse.ofSuccess("Community deleted successfully", 200));
    }

    @Override
    public Community createCommunity(Community community) {

        return communityRepository.save(community);

    }

    @Override
    public ResponseEntity<ApiResponse> addUserToCommunity(String communityId, String userId) {
        try {
            // Retrieve the user by ID from the user service
            Users user = userConsumerService.getUserById(userId)
                    .orElseThrow(() -> new InvalidUserException(userId, "User not found"));

            // Add the user to the community via UserConsumerService
            userConsumerService.addUserToCommunity(user, communityId);

            return ResponseEntity.ok(ApiResponse.ofSuccess("User added to community", 200));
        } catch (InvalidCommunityException | InvalidUserException e) {
            // Handle custom exceptions
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.ofError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            // Log the exception for debugging purposes
            log.error("Error adding user to community: {}", e.getMessage(), e);

            // Return a generic internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.ofError("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


    @Override
    public ResponseEntity<ApiResponse> removeUserFromCommunity(String communityId, String userId) {
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();

            // Check if the user is in the community admins


            // Remove user from community via UserConsumerService
            Users user = userConsumerService.getUserById(userId).orElse(null);
            if (user != null) {
                userConsumerService.removeUserFromCommunity(user, communityId);
            }
            if (community.getAdmins() != null && community.getAdmins().stream().anyMatch(admin -> admin.getId().equals(userId))) {
                // Remove user from community admins
                community.getAdmins().removeIf(admin -> admin.getId().equals(userId));
                userConsumerService.removeRCA(user);
            }
            communityRepository.save(community);

            return ResponseEntity.ok(ApiResponse.ofSuccess("User removed from community", 200));
        } else {
            return ResponseEntity.ok(ApiResponse.ofError("Community not found", 404));
        }
    }

    @Override
    public ResponseEntity<Set<Users>> getCommunityAdminsbyid(String communityId) {

        final Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException(
                                communityId,
                                "community",
                                Consts.COMMUNITY_NOT_FOUND
                        )
                );

        return ResponseEntity.ok(community.getAdmins());
    }


    public ResponseEntity<List<Community>> getCommunitiesByAdmin(String userId) {
        Users user = userConsumerService.getUserById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        List<Community> communities = communityRepository.findByAdminId(userId);

        if (communities.isEmpty()) {
            System.out.println("No communities found for user ID: " + userId);
        } else {
            System.out.println("Found communities for user ID: " + userId + " -> " + communities);
        }

        return ResponseEntity.ok(communities);
    }


}
