package com.tawasalna.auth.businesslogic;

import com.tawasalna.auth.businesslogic.pmsusersmanagement.PmsUsersManagementServiceImp;
import com.tawasalna.auth.models.*;

import com.tawasalna.auth.payload.request.AdminProfileDTO;
import com.tawasalna.auth.payload.request.AgentProfileDTO;
import com.tawasalna.auth.payload.request.BrokerProfileDTO;
import com.tawasalna.auth.payload.request.ProspectProfileDTO;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.utils.Consts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
 class PmsUsersManagementServiceImpTest {
    @Mock
    private  UserRepository userRepository;

    @Mock
    private IFileManagerService fileManager;
    @InjectMocks
    PmsUsersManagementServiceImp pmsUsersManagementServiceImp;

    @Test
    public void testUpdateAgentProfile() {

        // Création d'un utilisateur fictif
        Users user = new Users();
        user.setId("userId");
        AgentProfile agentProfile = new AgentProfile();
        user.setAgentProfile(agentProfile);

        // Configuration du comportement du mock
        when(userRepository.findById("userId")).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Création d'une instance du service à tester en lui passant le mock
        PmsUsersManagementServiceImp userService = new PmsUsersManagementServiceImp(userRepository, fileManager);

        // Création d'un AgentProfileDTO fictif
        AgentProfileDTO agentProfileDTO = new AgentProfileDTO();
        agentProfileDTO.setFirstname("John");
        agentProfileDTO.setLastname("Doe");
        agentProfileDTO.setTitle("Manager");
        agentProfileDTO.setProfessionalEmail("john.doe@example.com");
        agentProfileDTO.setProfessionalPhone("123456789");
        agentProfileDTO.setAddress("123 Street");
        agentProfileDTO.setPostalCode("12345");

        // Appel de la méthode à tester
        ResponseEntity<ApiResponse> response = userService.updateAgentProfile(agentProfileDTO, "userId");

        // Vérification du résultat
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Consts.SUCCESS, response.getBody().getMessage());

        // Vérification des appels de méthodes sur le mock
        verify(userRepository, times(1)).findById("userId");
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void updateAdminProfile() {

        // Création d'un utilisateur fictif
        Users user = new Users();
        user.setId("userId");
        AdminProfile adminProfile = new AdminProfile();
        user.setAdminProfile(adminProfile);

        // Configuration du comportement du mock
        when(userRepository.findById("userId")).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Création d'une instance du service à tester en lui passant le mock
        PmsUsersManagementServiceImp userService = new PmsUsersManagementServiceImp(userRepository, fileManager);

        // Création d'un AgentProfileDTO fictif
        AdminProfileDTO adminProfileDTO = new AdminProfileDTO();
        adminProfileDTO.setFirstname("John");
        adminProfileDTO.setLastname("Doe");
        adminProfileDTO.setTitle("Manager");
        adminProfileDTO.setProfessionalEmail("john.doe@example.com");
        adminProfileDTO.setProfessionalPhone("123456789");
        adminProfileDTO.setAddress("123 Street");
        adminProfileDTO.setPostalCode("12345");

        // Appel de la méthode à tester
        ResponseEntity<ApiResponse> response = userService.updateAdminProfile(adminProfileDTO, "userId");

        // Vérification du résultat
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Consts.SUCCESS, response.getBody().getMessage());

        // Vérification des appels de méthodes sur le mock
        verify(userRepository, times(1)).findById("userId");
        verify(userRepository, times(1)).save(any(Users.class));
    }


    @Test
    public void updateBrokerProfile() {

        // Création d'un utilisateur fictif
        Users user = new Users();
        user.setId("userId");
        BrokerProfile brokerProfile = new BrokerProfile();
        user.setBrokerProfile(brokerProfile);

        // Configuration du comportement du mock
        when(userRepository.findById("userId")).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Création d'une instance du service à tester en lui passant le mock
        PmsUsersManagementServiceImp userService = new PmsUsersManagementServiceImp(userRepository, fileManager);

        // Création d'un AgentProfileDTO fictif
        BrokerProfileDTO brokerProfileDTO = new BrokerProfileDTO();
        brokerProfileDTO.setFirstname("John");
        brokerProfileDTO.setLastname("Doe");
        brokerProfileDTO.setTitle("Manager");
        brokerProfileDTO.setProfessionalEmail("john.doe@example.com");
        brokerProfileDTO.setProfessionalPhone("123456789");
        brokerProfileDTO.setAddress("123 Street");
        brokerProfileDTO.setPostalCode("12345");

        // Appel de la méthode à tester
        ResponseEntity<ApiResponse> response = userService.updateBrokerProfile(brokerProfileDTO, "userId");

        // Vérification du résultat
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Consts.SUCCESS, response.getBody().getMessage());

        // Vérification des appels de méthodes sur le mock
        verify(userRepository, times(1)).findById("userId");
        verify(userRepository, times(1)).save(any(Users.class));
    }
    @Test
    public void updateProspectProfile() {

        // Création d'un utilisateur fictif
        Users user = new Users();
        user.setId("userId");
        ProspectProfile prospectProfile = new ProspectProfile();
        user.setProspectProfile(prospectProfile);

        // Configuration du comportement du mock
        when(userRepository.findById("userId")).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Création d'une instance du service à tester en lui passant le mock
        PmsUsersManagementServiceImp userService = new PmsUsersManagementServiceImp(userRepository, fileManager);

        // Création d'un AgentProfileDTO fictif
        ProspectProfileDTO prospectProfileDTO = new ProspectProfileDTO();
        prospectProfileDTO.setFirstname("John");
        prospectProfileDTO.setLastname("Doe");
        prospectProfileDTO.setTitle("Manager");
        prospectProfileDTO.setAddress("123 Street");
        prospectProfileDTO.setPostalCode("12345");

        // Appel de la méthode à tester
        ResponseEntity<ApiResponse> response = userService.updateProspectProfile(prospectProfileDTO, "userId");

        // Vérification du résultat
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Consts.SUCCESS, response.getBody().getMessage());

        // Vérification des appels de méthodes sur le mock
        verify(userRepository, times(1)).findById("userId");
        verify(userRepository, times(1)).save(any(Users.class));
    }
}
