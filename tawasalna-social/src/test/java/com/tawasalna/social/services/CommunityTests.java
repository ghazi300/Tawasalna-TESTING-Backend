package com.tawasalna.social.services;

import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidCommunityException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.userapi.model.AccountStatus;
import com.tawasalna.shared.userapi.model.Role;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.userapi.service.IUserConsumerService;
import com.tawasalna.social.repository.CommunityRepository;
import com.tawasalna.social.service.CommunityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunityTests {

    @Mock
    private CommunityRepository communityRepository;

    @Mock
    private IUserConsumerService userConsumerService;

    @InjectMocks
    private CommunityServiceImpl communityService;

    private static Users makeMockUser() {
        Set<Role> roles = new HashSet<>();
        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");
        roles.add(role1);

        // Create a Users object
        return new Users(
                "1L",
                "testuser@example.com",
                "test user",
                "new york city",
                "mypassword",
                "",
                roles,
                AccountStatus.ENABLED,
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                null
        );
    }

    @Test
    void testAddAdminToCommunity_Success() {
        String communityId = "communityId";
        String userId = "userId";

        Community community = new Community("communityId", "CommunityName", "Description", Collections.emptySet());
        Users user = makeMockUser();

        when(communityRepository.findById(communityId)).thenReturn(Optional.of(community));
        when(userConsumerService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse> response = communityService.addAdminToCommunity(communityId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin added to community", response.getBody().getMessage());
        verify(communityRepository).findById(communityId);
        verify(userConsumerService).getUserById(userId);
        verify(userConsumerService).addRCA(user);
        verify(userConsumerService).addUserToCommunity(user, communityId);
        verify(communityRepository).save(community);
    }

    @Test
    void testAddAdminToCommunity_UserAlreadyAdmin_ErrorResponse() {
        String communityId = "communityId";
        Users user = makeMockUser();

        Community community = new Community("communityId", "CommunityName", "Description", Collections.singleton(user));

        when(communityRepository.findById(communityId)).thenReturn(Optional.of(community));
        when(userConsumerService.getUserById(user.getId())).thenReturn(Optional.of(makeMockUser()));

        ResponseEntity<ApiResponse> response = communityService.addAdminToCommunity(communityId, user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User is already a community admin", response.getBody().getError());
        verify(communityRepository).findById(communityId);
        verify(userConsumerService).getUserById(user.getId());
        verify(userConsumerService, never()).addRCA(any());
        verify(userConsumerService, never()).addUserToCommunity(any(), anyString());
        verify(communityRepository, never()).save(any());
    }

    @Test
    void testAddAdminToCommunity_UserNotFound_ErrorResponse() {
        // Arrange
        String communityId = "communityId";
        String userId = "userId";

        Community community = new Community("communityId", "CommunityName", "Description", Collections.emptySet());

        when(communityRepository.findById(communityId)).thenReturn(Optional.of(community));
        when(userConsumerService.getUserById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        // Use assertThrows to verify that InvalidUserException is thrown
        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> {
            ResponseEntity<ApiResponse> response = communityService.addAdminToCommunity(communityId, userId);
        });

        // Verify the exception message and cause
        assertEquals("Invalid user with identifier: userId - Cause: User not found", exception.getMessage());

        // Verify method invocations
        verify(communityRepository).findById(communityId);
        verify(userConsumerService).getUserById(userId);
        verify(userConsumerService, never()).addRCA(any());
        verify(userConsumerService, never()).addUserToCommunity(any(), anyString());
        verify(communityRepository, never()).save(any());
    }

    @Test
    void testAddAdminToCommunity_CommunityNotFound_ErrorResponse() {
        String communityId = "communityId";
        String userId = "userId";

        when(communityRepository.findById(communityId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = communityService.addAdminToCommunity(communityId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User not found", response.getBody().getError());
        verify(communityRepository).findById(communityId);
        verify(userConsumerService, never()).getUserById(anyString());
        verify(userConsumerService, never()).addRCA(any());
        verify(userConsumerService, never()).addUserToCommunity(any(), anyString());
        verify(communityRepository, never()).save(any());
    }

    @Test
    void testAddCommunity_Success() {
        String communityName = "TestCommunity";
        String description = "Description";

        Community newCommunity = new Community("", communityName, description, null);

        when(communityRepository.save(any(Community.class))).thenReturn(newCommunity);
        Community community = new Community();
        community.setName(communityName);
        community.setDescription(description);
        Community addedCommunity = communityService.createCommunity(community);

        assertNotNull(addedCommunity);
        assertEquals(communityName, addedCommunity.getName());
    }

  /*  @Test
    void testAddCommunity_DuplicateName_ThrowException() {
        String communityName = "ExistingCommunity";
        String description = "Description";
        Community community = new Community();
        community.setName(communityName);
        community.setDescription(description);
        when(communityRepository.existsByName(communityName)).thenReturn(true);

        assertThrows(InvalidCommunityException.class, () -> communityService.createCommunity(community));
    }
*/
/*
    @Test
    void testCreateCommunity_Success() {
        Community communityToCreate = new Community("", "CommunityName", "Description", null);
        Community savedCommunity = new Community("1", "CommunityName", "Description", null);
        when(communityRepository.save(any(Community.class))).thenReturn(savedCommunity);

        Community createdCommunity = communityService.createCommunity(communityToCreate);

        assertNotNull(createdCommunity);
        assertEquals(savedCommunity.getId(), createdCommunity.getId());
        System.out.println(createdCommunity.toString());
        assertEquals(communityToCreate.getName(), createdCommunity.getName());
        assertEquals(communityToCreate.getDescription(), createdCommunity.getDescription());
    }

 */

    @Test
    void testGetCommunityById_ExistingId_ReturnCommunity() {
        String communityId = "1";
        Community existingCommunity = new Community(communityId, "CommunityName", "Description", null);
        when(communityRepository.findById(communityId)).thenReturn(Optional.of(existingCommunity));

        ResponseEntity<Community> response = communityService.getCommunityById(communityId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCommunity, response.getBody());
    }


    @Test
    void testGetCommunityById_NonExistingId_ReturnErrorResponse() {
        // Arrange
        String communityId = "999";
        when(communityRepository.findById(communityId)).thenReturn(Optional.empty());

        // Act & Assert
        InvalidCommunityException exception = assertThrows(InvalidCommunityException.class, () -> {
            communityService.getCommunityById(communityId);
        });

        assertEquals("Invalid community with identifier: 999 - Cause: Community Not Found", exception.getMessage());
        assertEquals(communityId, exception.getId());

        // Verify repository method invocation
        verify(communityRepository).findById(communityId);
    }

    @Test
    void testGetAllUsersInCommunity_ValidCommunityId_ReturnUserList() {
        String communityId = "1";
        List<Users> userList = Arrays.asList(makeMockUser(), makeMockUser());
        when(userConsumerService.getUsersByCommunityId(communityId)).thenReturn(userList);

        List<Users> result = communityService.getAllUsersInCommunity(communityId);

        assertEquals(userList.size(), result.size());
        assertTrue(result.containsAll(userList));
    }

/*
    @Test
    void testAddUserToCommunity_InvalidCommunityId_ReturnErrorResponse() {
        String communityId = "999";
        String userId = "1";
        when(communityRepository.findById(communityId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = communityService.addUserToCommunity(communityId, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Invalid community with identifier: " + communityId + " - Cause: Community not found", response.getBody().getError());
    }

 */

    /*
    @Test
    void testAddUserToCommunity_InvalidUserId_ReturnErrorResponse() {
        String communityId = "1";
        String userId = "999";
        Community existingCommunity = new Community(communityId, "CommunityName", "Description", null);
        when(communityRepository.findById(communityId)).thenReturn(Optional.of(existingCommunity));
        when(userConsumerService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = communityService.addUserToCommunity(communityId, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Invalid user with identifier: " + userId + " - Cause: User not found", response.getBody().getError());
    }

     */

    @Test
    void testRemoveUserFromCommunity_UserRemovedSuccessfully() {
        // Arrange
        String communityId = "1";
        Users userToRemove = makeMockUser();
        String userId = userToRemove.getId();

        // Create existing community and user
        Community existingCommunity = new Community(communityId, "CommunityName", "Description", null);
        userToRemove.setCommunity(existingCommunity);

        // Stub repository and service calls
        when(communityRepository.findById(communityId)).thenReturn(Optional.of(existingCommunity));
        when(userConsumerService.getUserById(userId)).thenReturn(Optional.of(userToRemove));

        // Act
        ResponseEntity<ApiResponse> response = communityService.removeUserFromCommunity(communityId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User removed from community", response.getBody().getMessage());

        // Verify interactions with userConsumerService
        verify(userConsumerService, times(1)).removeUserFromCommunity(userToRemove, communityId);
        verify(communityRepository, times(1)).save(existingCommunity);


    }

}




