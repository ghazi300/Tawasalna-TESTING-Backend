package com.tawasalna.auth.businesslogic;

import com.tawasalna.auth.businesslogic.auth.AuthServiceImpl;
import com.tawasalna.auth.businesslogic.auth.PMSAuthServiceImp;
import com.tawasalna.auth.businesslogic.pmsassistance.AssistanceService;
import com.tawasalna.auth.businesslogic.staffsignuprequest.IstaffSignUpReqService;
import com.tawasalna.auth.businesslogic.usermanagement.UserManagementService;
import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.models.*;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import com.tawasalna.auth.models.enums.RolesEnum;
import com.tawasalna.auth.payload.request.AssistanceDTO;
import com.tawasalna.auth.payload.request.RegisterPmsDTO;
import com.tawasalna.auth.payload.request.StaffSignupRequestDTO;
import com.tawasalna.auth.payload.response.RegisterResp;
import com.tawasalna.auth.repository.*;
import com.tawasalna.auth.security.JwtUtils;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.mail.IMailingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PMSAuthServiceImpTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserSettingsRepository userSettingsRepo;
    @Mock
    private IAuthUtilsService authUtilsService;
    @Mock
    private AssistanceRepository assistanceRepository;
    @Mock
    private IMailingService mailingService;
    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private UserManagementService userManagementService;

    @Mock
    private  IstaffSignUpReqService istaffSignUpReqService;

    @Mock
    private AssistanceService assistanceService ;

    @Mock
    private StaffSignupRequestRepo staffSignupRequestRepo;

    private UserVerifCode verifCode;

    @InjectMocks
    private PMSAuthServiceImp authService;


/*
    @Test
    void testSignupRequest() throws MessagingException {
        // Prepare test data
        RegisterPmsDTO registerPmsDTO = new RegisterPmsDTO(
                "test@example.com",
                "Azza",
                "ben romdhan",
                "password123",
                "28118515",
                "ROLE_PMS_PROSPECT",
                "azza.benromdhan@esprit.tn",
                "localost:8070/authentification/login",
                "profisionalemail@gmail.com",
                "28118515",
                "6614512599b56010615954ed"
        );

        // Mock the behavior of userRepository.existsByEmail() to return false
        when(userRepository.existsByEmail(registerPmsDTO.getEmail())).thenReturn(false);

        // Mock the behavior of roleRepository.findById() to return a Role object
        Role role = new Role();
        role.setId("1");
        role.setName("ROLE_PMS_PROSPECT");
        when(roleRepository.findById(registerPmsDTO.getRole())).thenReturn(Optional.of(role));

        // Mock the behavior of authUtilsService.makePmsUser() to return a Users object
        Users user = new Users();
        user.setId("1");
        user.setEmail(registerPmsDTO.getEmail());
        when(authUtilsService.makePmsUser(
                any(RegisterPmsDTO.class),
                isNull(),
                isNull(),
                any(ProspectProfile.class),
                isNull(),
                isNull(),
                isNull(),
                isNull()
        )).thenReturn(user);

        // Mock the behavior of userRepository.save() to return the Users object
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Mock the behavior of userSettingsRepo.save() to return a UserSettings object
        when(userSettingsRepo.save(any(UserSettings.class))).thenReturn(new UserSettings());

        // Mock the behavior of userRepository.findByEmail() to return an assistant user
        Users assistantUser = new Users();
        assistantUser.setId("2");
        assistantUser.setEmail("assistant@example.com");
        Set<Role> assistantRoles = new HashSet<>();
        Role assistantRole = new Role();
        assistantRole.setName("ROLE_PMS_AGENT");
        assistantRoles.add(assistantRole);
        assistantUser.setRoles(assistantRoles);
        when(userRepository.findByEmail(registerPmsDTO.getAssistentMail())).thenReturn(Optional.of(assistantUser));

        // Mock the behavior of assistanceRepository.save() to return an Assistance object
        Assistance assistance = new Assistance();
        when(assistanceRepository.save(any(Assistance.class))).thenReturn(assistance);

        // Mock the behavior of mailingService.sendEmail() to do nothing
        doNothing().when(mailingService).sendEmail(anyString(), anyString(), anyString(), anyList());

        // Call the SignupRequest method
        RegisterResp registerResp = authService.SignupRequest(registerPmsDTO);

        // Add assertions to check the response content and status
        assertNotNull(registerResp);
        assertEquals("1", registerResp.getUserId());
    }

 */
/*
    @Test
    void () throws MessagingException {
        // Prepare test data
        RegisterPmsDTO registerPmsDTO = new RegisterPmsDTO(
                "azza@example.com",
                "Azza",
                "ben romdhan",
                "password1234",
                "28118515",
                "ROLE_PMS_AGENT",
                "azza.benromdhan@esprit.tn",
                "localost:8070/authentification/login",
                "profisionalemail@gmail.com",
                "28118515",
                "6614512599b56010615954ed"
        );

        // Mock the behavior of userRepository.existsByEmail() to return false
        when(userRepository.existsByEmail(registerPmsDTO.getEmail())).thenReturn(false);

        // Mock the behavior of roleRepository.findById() to return a Role object
        Role role = new Role();
        role.setId("65d671c031baa16064d291de");
        role.setName("ROLE_PMS_AGENT");
        when(roleRepository.findById(registerPmsDTO.getRole())).thenReturn(Optional.of(role));

        // Mock the behavior of communityRepository.findById() to return a Community object
        Community community = new Community();
        when(communityRepository.findById(registerPmsDTO.getCommunityId())).thenReturn(Optional.of(community));

        // Create a non-null Users object to be returned by authUtilsService.makePmsUser
        // Mock the behavior of authUtilsService.makePmsUser() to return a Users object
        Users user = new Users();
        user.setId("2");
        user.setEmail(registerPmsDTO.getEmail());
        when(authUtilsService.makePmsUser(
                any(RegisterPmsDTO.class),
                isNull(),
                isNull(),
                isNull(),
                any(AgentProfile.class),
                isNull(),
                isNull(),
                isNull()
        )).thenReturn(user);

        // Mock the behavior of userRepository.save() to return the Users object
        when(userRepository.save(any(Users.class))).thenReturn(user);


        // Mock the behavior of userSettingsRepo.save() to return a UserSettings object
        when(userSettingsRepo.save(any(UserSettings.class))).thenReturn(new UserSettings());

        // Mock the behavior of userManagementService.getPmsAdminsPerCommunity() to return a list of admin users
        Users admin = new Users();
        admin.setEmail("celina.celine999@gmail.com");
        List<Users> admins = Collections.singletonList(admin);
        when(userManagementService.getPmsAdminsPerCommunity(any(Community.class))).thenReturn(admins);

        // Mock the behavior of istaffSignUpReqService.makesignupRequest() to return a StaffSignupRequest object
        StaffSignupRequest signupRequest = new StaffSignupRequest();
        when(istaffSignUpReqService.makesignupRequest(any(StaffSignupRequestDTO.class))).thenReturn(signupRequest);

        // Mock the behavior of staffSignupRequestRepo.save() to return the StaffSignupRequest object
        when(staffSignupRequestRepo.save(any(StaffSignupRequest.class))).thenReturn(signupRequest);

        // Mock the behavior of mailingService.sendEmailtoMany() to do nothing
        doNothing().when(mailingService).sendEmailtoMany(anyList(), anyString(), anyString(), anyList());

        // Call the SignupStaff method
        RegisterResp registerResp = authService.SignupStaff(registerPmsDTO);

      /*  // Verify interactions with mocks
        verify(userRepository, times(1)).existsByEmail(registerPmsDTO.getEmail());
        verify(roleRepository, times(1)).findById(registerPmsDTO.getRole());
        verify(communityRepository, times(1)).findById(registerPmsDTO.getCommunityId());
        verify(authUtilsService, times(1)).makePmsUser(any(RegisterPmsDTO.class), isNull(), any(ProspectProfile.class), any(AgentProfile.class), any(BrokerProfile.class), any(AdminProfile.class), any(Community.class));
        verify(userRepository, times(1)).save(any(Users.class));
        verify(userSettingsRepo, times(1)).save(any(UserSettings.class));
        verify(userManagementService, times(1)).getPmsAdminsPerCommunity(any(Community.class));
        verify(istaffSignUpReqService, times(1)).makesignupRequest(any(StaffSignupRequestDTO.class));
        verify(staffSignupRequestRepo, times(1)).save(any(StaffSignupRequest.class));
        verify(mailingService, times(1)).sendEmailtoMany(anyList(), anyString(), anyString(), anyList());

        // Add assertions to check the response content and status
        assertNotNull(registerResp);
        assertEquals("1", registerResp.getUserId());
    }
    */
}

