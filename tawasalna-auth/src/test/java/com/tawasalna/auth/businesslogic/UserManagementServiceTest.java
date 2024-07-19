package com.tawasalna.auth.businesslogic;

import com.tawasalna.auth.businesslogic.usermanagement.UserManagementService;
import com.tawasalna.auth.models.Role;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.RolesEnum;
import com.tawasalna.auth.repository.RoleRepository;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserManagementServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    UserManagementService userManagementService;

    private Users user;


    @BeforeEach
    void setUp() {
        user = new Users();
    }

    @Test
    void testGetUserById() {
        // Arrange
        final String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<Users> response = userManagementService.getUserById(userId);

        // Assert
        assertEquals(user, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        final List<Users> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        ResponseEntity<List<Users>> response = userManagementService.getAll();

        // Assert
        assertEquals(users, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void testGetAllAgents() {

        // Arrange
        final List<Users> agents = new ArrayList<>();
        final Role r = new Role("1", RolesEnum.ROLE_PMS_AGENT.name());
        final Set<Role> roles = new HashSet<>();
        roles.add(r);
        user.setRoles(roles);

        when(roleRepository.findByName(RolesEnum.ROLE_PMS_AGENT.name())).thenReturn(Optional.of(r));
        when(userRepository.findAll()).thenReturn(agents);

        // Act
        final List<Users> response = userManagementService.getAgents();

        // Assert
        assertEquals(agents, response);
    }

    @Test
    void testGetBrokers() {

        // Arrange
        final List<Users> brokers = new ArrayList<>();
        final Role r = new Role("1", RolesEnum.ROLE_PMS_BROKER.name());
        final Set<Role> roles = new HashSet<>();
        roles.add(r);

        when(roleRepository.findByName(RolesEnum.ROLE_PMS_BROKER.name())).thenReturn(Optional.of(r));

        user.setRoles(roles);
        when(userRepository.findAll()).thenReturn(brokers);

        // Act
        final List<Users> response = userManagementService.getBrokers();

        // Assert
        assertEquals(brokers, response);
    }

    @Test
    void testGetProspects() {

        // Arrange
        final List<Users> prospects = new ArrayList<>();
        final Role r = new Role("1", RolesEnum.ROLE_PMS_PROSPECT.name());
        final Set<Role> roles = new HashSet<>();
        roles.add(r);

        when(roleRepository.findByName(RolesEnum.ROLE_PMS_PROSPECT.name())).thenReturn(Optional.of(r));

        user.setRoles(roles);
        when(userRepository.findAll()).thenReturn(prospects);

        // Act
        final List<Users> response = userManagementService.getProspects();

        // Assert
        assertEquals(prospects, response);
    }

    @Test
    void testGetCommunityAdmins() {

        // Arrange
        final List<Users> communityAdmins = new ArrayList<>();
        final Role r = new Role("1", RolesEnum.ROLE_COMMUNITY_ADMIN.name());
        final Set<Role> roles = new HashSet<>();
        roles.add(r);

        when(roleRepository.findByName(RolesEnum.ROLE_COMMUNITY_ADMIN.name())).thenReturn(Optional.of(r));

        user.setRoles(roles);
        when(userRepository.findAll()).thenReturn(communityAdmins);

        // Act
        final List<Users> response = userManagementService.getCommunityAdmins();

        // Assert
        assertEquals(communityAdmins, response);
    }

    @Test
    void testGetPMSAdmins() {

        // Arrange
        final List<Users> agents = new ArrayList<>();
        final Role r = new Role("1", RolesEnum.ROLE_PMS_ADMIN.name());
        final Set<Role> roles = new HashSet<>();
        roles.add(r);

        when(roleRepository.findByName(RolesEnum.ROLE_PMS_ADMIN.name())).thenReturn(Optional.of(r));

        user.setRoles(roles);
        when(userRepository.findAll()).thenReturn(agents);

        // Act
        final List<Users> response = userManagementService.getPMSAdmins();

        // Assert
        assertEquals(agents, response);
    }

    @Test
    void testBanUser() {
        // Arrange
        final String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<ApiResponse> response = userManagementService.banUser(userId);

        // Assert
        assertEquals("Success", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(200, response.getBody().getStatus());
    }

    @Test
    void testDisableUser() {
        // Arrange
        final String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<ApiResponse> response = userManagementService.disableUser(userId);

        // Assert
        assertEquals("Success", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(200, response.getBody().getStatus());
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        final String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<Users> response = userManagementService.getUserByEmail(email);

        // Assert
        assertEquals(user, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
}
