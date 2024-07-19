package com.tawasalna.auth.businesslogic.usermanagement;

import com.tawasalna.auth.exceptions.InvalidRoleException;
import com.tawasalna.auth.models.Role;
import com.tawasalna.auth.models.StaffSignupRequest;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.AccountStatus;
import com.tawasalna.auth.models.enums.RolesEnum;
import com.tawasalna.auth.models.enums.StaffSignupStatus;
import com.tawasalna.auth.repository.RoleRepository;
import com.tawasalna.auth.repository.StaffSignupRequestRepo;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.communityapi.service.CommunityConsumerServiceImpl;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidCommunityException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@Slf4j
@AllArgsConstructor


public class UserManagementService implements IUserManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CommunityConsumerServiceImpl communityConsumerService;
    private final StaffSignupRequestRepo staffSignupRequestRepo;
    @Override
    public ResponseEntity<Users> getUserById(String id) {
        return ResponseEntity.ok(
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidUserException(
                                        id,
                                        Consts.USER_NOT_FOUND
                                )
                        )
        );
    }

    @Override
    public ResponseEntity<ApiResponse> banUser(String id) {
        updateUserStatus(id, AccountStatus.BANNED);

        return ResponseEntity.ok(
                new ApiResponse(
                        Consts.SUCCESS,
                        null,
                        200
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse> disableUser(String id) {
        updateUserStatus(id, AccountStatus.DISABLED);
        return ResponseEntity.ok(
                new ApiResponse(
                        "Success",
                        null,
                        200
                )
        );

    }

    @Override
    public ResponseEntity<ApiResponse> enableUser(String id) {
        updateUserStatus(id, AccountStatus.ENABLED);
        return ResponseEntity.ok(
                new ApiResponse(
                        Consts.SUCCESS,
                        null,
                        200
                )
        );
    }
    @Override
    public ResponseEntity<Users> getUserByEmail(String email) {
        return ResponseEntity.ok(userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidUserException(
                        email,
                        Consts.USER_NOT_FOUND)
                )
        );
    }

    @Override
    public ResponseEntity<List<Users>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public List<Users> getAgents() {
        return getUsersByRole(RolesEnum.ROLE_PMS_AGENT.name());
    }

    @Override
    public List<Users> getBrokers() {
        return getUsersByRole(RolesEnum.ROLE_PMS_BROKER.name());
    }

    @Override
    public List<Users> findBrokersWithAcceptedSignupRequest() {
        List<StaffSignupRequest> acceptedRequests = staffSignupRequestRepo.findByStatus(StaffSignupStatus.ACCEPTED);

        // Extract broker users from the accepted signup requests
        List<Users> brokersWithAcceptedRequests = acceptedRequests.stream()
                .map(StaffSignupRequest::getBroker)
                .distinct() // To ensure unique brokers
                .collect(Collectors.toList());

        return brokersWithAcceptedRequests;
    }
    @Override
    public List<Users> findAgentsWithAcceptedSignupRequest() {
        List<StaffSignupRequest> acceptedRequests = staffSignupRequestRepo.findByStatus(StaffSignupStatus.ACCEPTED);

        // Extract broker users from the accepted signup requests
        List<Users> brokersWithAcceptedRequests = acceptedRequests.stream()
                .map(StaffSignupRequest::getAgent)
                .distinct() // To ensure unique brokers
                .collect(Collectors.toList());

        return brokersWithAcceptedRequests;
    }

    @Override
    public List<Users> getCommunityAdmins() {
        return getUsersByRole(RolesEnum.ROLE_COMMUNITY_ADMIN.name());
    }

    @Override
    public List<Users> getPMSAdmins() {
        return getUsersByRole(RolesEnum.ROLE_PMS_ADMIN.name());
    }

    @Override
    public List<Users> getProspects() {
        return getUsersByRole(RolesEnum.ROLE_PMS_PROSPECT.name());
    }

    @Override
    public List<Users> getPmsAdminsPerCommunity(Community community) {
        List<Users> pmsAdmins = getUsersByRole(RolesEnum.ROLE_PMS_ADMIN.name());
        List<Users> pmsAdminsPerCommunity = new ArrayList<>();

        if (community == null) {
            throw new IllegalArgumentException("Community cannot be null");
        }

        log.warn("Community ID: {}", community.getId());

        for (Users pmsAdmin : pmsAdmins) {
            log.warn("Checking admin with ID: {} in community with ID: {}", pmsAdmin.getId(), pmsAdmin.getCommunity().getId());
            if (pmsAdmin.getCommunity() != null && pmsAdmin.getCommunity().getId().equals(community.getId())) {
                pmsAdminsPerCommunity.add(pmsAdmin);
            }

        }
        return pmsAdminsPerCommunity;
    }

    private void updateUserStatus(String id, AccountStatus status) {
        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(
                                id,
                                Consts.USER_NOT_FOUND
                        )
                );

        user.setAccountStatus(status);
        userRepository.save(user);
    }

    private List<Users> getUsersByRole(String roleName) {
        final Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new InvalidRoleException(
                        format(
                                "{0}",
                                roleName
                        ),
                        "invalid role")
                );

        return userRepository.findByRolesId(role.getId());
    }

    @Override
    public ResponseEntity<List<Users>> getUsersByCommunity(String communityId) {
        final Community community = communityConsumerService.getCommunityById(communityId)
                .orElseThrow(() -> new InvalidCommunityException(
                        format(
                                "{0}",
                                communityId
                        ),
                        "invalid Community")
                );
        return ResponseEntity.ok(userRepository.findByCommunityId(community.getId()));
    }
    @Override
    public ResponseEntity<Page<Users>> getUsersByCommunityPage(int pageNumber, String communityId) {
        if (pageNumber <= 0) pageNumber = 1;
        final Community community = communityConsumerService
                .getCommunityById(communityId)
                .orElseThrow(() ->
                        new InvalidCommunityException(
                                communityId,
                                Consts.USER_NOT_FOUND
                        )
                );
        return ResponseEntity.ok(userRepository
                .findByCommunityId(community.getId(), PageRequest.of(pageNumber - 1, 9)));
    }


    @Override
    public ResponseEntity<ApiResponse> addCommunityAdminRole(String userId) {
        // Retrieve the ROLE_COMMUNITY_ADMIN role from the repository
        Role adminRole = roleRepository.findByName(RolesEnum.ROLE_COMMUNITY_ADMIN.name())
                .orElseThrow(() -> new InvalidRoleException(userId, "Role Not Found"));
        // Find the user by ID
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));
        // Get the current roles of the user
        Set<Role> roles = user.getRoles();
        // Check if the user already has the ROLE_COMMUNITY_ADMIN role
        boolean alreadyHasAdminRole = roles.stream()
                .anyMatch(role -> role.getName().equals(adminRole.getName()));
        if (!alreadyHasAdminRole) {
            // Add the ROLE_COMMUNITY_ADMIN role to the user's roles
            roles.add(adminRole);
            // Update the user's roles
            user.setRoles(roles);
            // Save the updated user
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse("Profile Role Added successfully!", null, 200));
        } else {
            // User already has the ROLE_COMMUNITY_ADMIN role, no need to update
            return ResponseEntity.ok(new ApiResponse("User already has the admin role!", null, 200));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> removeRoleCommunityAdmin(String userId) {
        // Retrieve the ROLE_COMMUNITY_ADMIN role from the repository
        Role adminRole = roleRepository.findByName(RolesEnum.ROLE_COMMUNITY_ADMIN.name())
                .orElseThrow(() -> new InvalidRoleException(RolesEnum.ROLE_COMMUNITY_ADMIN.name(), "Role Not Found"));

        // Find the user by ID
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        // Get the current roles of the user
        Set<Role> roles = user.getRoles();

        // Check if the user has the ROLE_COMMUNITY_ADMIN role
        boolean hasAdminRole = roles.removeIf(role -> role.getName().equals(adminRole.getName()));

        if (hasAdminRole) {
            // Update the user's roles after removing the ROLE_COMMUNITY_ADMIN role
            user.setRoles(roles);
            // Save the updated user
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse("Profile Role Removed successfully!", null, 200));
        } else {
            // The user does not have the ROLE_COMMUNITY_ADMIN role, no need to update
            log.info("User does not have ROLE_COMMUNITY_ADMIN role");

            return ResponseEntity.ok(new ApiResponse("User does not have the admin role!", null, 200));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> addToCommunity(String communityId, String userId) {
        // Get the community by ID
        Community community = communityConsumerService.getCommunityById(communityId)
                .orElseThrow(() -> new InvalidUserException(communityId, "Community not found"));
        // Get the user by ID
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));
        // Set the user's community
        user.setCommunity(community);
        // Save the updated user
        Users updatedUser = userRepository.save(user);

        log.info("User updated with community: {}", updatedUser);

        return ResponseEntity.ok(new ApiResponse("Community assigned successfully!", null, 200));
    }

    @Override
    public ResponseEntity<ApiResponse> RemoveFromCommunity(String communityId, String userId) {
        // Get the user by ID
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));
        user.setCommunity(null);
        if( user.getBusinessProfile()!=null&& user.getBusinessProfile().isVerified()){
            user.getBusinessProfile().setVerified(false);
        }
        // Save the updated user
        Users updatedUser = userRepository.save(user);
        log.info("User updated after removing from community: {}", updatedUser);
        return ResponseEntity.ok(new ApiResponse("User removed from community and roles updated!", null, 200));
    }
}
