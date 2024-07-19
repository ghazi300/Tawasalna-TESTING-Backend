package com.tawasalna.auth.businesslogic;

import com.tawasalna.auth.businesslogic.auth.AuthServiceImpl;
import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.models.*;
import com.tawasalna.auth.models.enums.RolesEnum;
import com.tawasalna.auth.payload.request.LoginDTO;
import com.tawasalna.auth.payload.request.RegisterDTO;
import com.tawasalna.auth.payload.response.JwtResponse;
import com.tawasalna.auth.repository.RoleRepository;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.auth.repository.UserSettingsRepository;
import com.tawasalna.auth.repository.UserVerifCodeRepository;
import com.tawasalna.auth.security.JwtUtils;
import com.tawasalna.auth.security.UserDetailsImpl;
import com.tawasalna.shared.communityapi.service.CommunityConsumerServiceImpl;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.mail.IMailingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceImplTest {

    @Mock
    private IAuthUtilsService authUtilsService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserSettingsRepository userSettingsRepo;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserVerifCodeRepository userVerifCodeRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private IFileManagerService fileManager;
    @Mock
    private IMailingService mailingService;

    @InjectMocks
    private AuthServiceImpl authService;
    @InjectMocks
    private CommunityConsumerServiceImpl communityConsumerService;
    private Users user;
    private UserVerifCode verifCode;

    private static Users makeMockUser() {
        Set<Role> roles = new HashSet<>();

        Role role1 = new Role();

        role1.setName(RolesEnum.ROLE_COMMUNITY_MEMBER.name());

        roles.add(role1);

        //create a Users object
        return new Users();
    }

    @BeforeEach
    public void setUp() {
        user = makeMockUser();
        verifCode = new UserVerifCode(null, "45615", "john.doe@example.com", LocalDateTime.now().plusMinutes(15));
        authService = new AuthServiceImpl(
                userRepository,
                roleRepository,
                jwtUtils,
                authenticationManager,
                userSettingsRepo,
                userVerifCodeRepository,
                authUtilsService,
                mailingService,
                fileManager
        );
        SecurityContextHolder.clearContext();
    }
/*
    @Test
    void testLogin() {
        // create a LoginRequest object
        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "mypassword");

        Users user = makeMockUser();

        // create a Company object
        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCountry("Tunis");
        businessProfile.setCity("test");

        user.setBusinessProfile(businessProfile);

        // create a JwtResponse object
        JwtResponse expectedResponse = new JwtResponse(
                "1L",
                "testjwt",
                "testRefresh",
                getAuthorityStrings(),
                true
        );

        // mock the userRepository.findByEmail() method to return the Users object
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));

        // mock the authenticationManager.authenticate() method to return an Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Create a mock UserDetailsImpl object
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Set up behavior for the getEmail method of UserDetailsImpl
        when(userDetails.getEmail()).thenReturn(user.getEmail());

        // mock the jwtUtils.generateJwtToken() method to return a JWT token string
        when(jwtUtils.generateToken(user.getEmail())).thenReturn("testjwt");
        when(jwtUtils.generateRefreshToken()).thenReturn("testRefresh");
        // call the login() method and verify that it returns the expected JwtResponse object
        JwtResponse actualResponse = authService.login(loginDTO);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getToken(), actualResponse.getToken());
        assertEquals(expectedResponse.getRefreshToken(), actualResponse.getRefreshToken());
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getRoles(), actualResponse.getRoles());
    }

    private List<String> getAuthorityStrings() {
        List<String> authorityStrings = new ArrayList<>();
        authorityStrings.add("ROLE_COMMUNITY_MEMBER");
        return authorityStrings;
    }


    @Test
    void testRegister() throws MessagingException {
        // Prepare test data
        RegisterDTO registerDTO = new RegisterDTO(
                "test@example.com",
                "test",
                "ROLE_COMMUNITY_MEMBER",
                "addr",
                "5as4d5a",
                "",
                null,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        // Mock behavior of userRepository.existsByEmail() to return false, indicating the user does not exist
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // Mock behavior of roleRepository.findById() to return a Role object when called with "ROLE_ADMIN"
        Role role = new Role();
        role.setId("1");
        role.setName("ROLE_COMMUNITY_MEMBER");// Assuming the ID of ROLE_ADMIN is "1" in your database
        when(roleRepository.findById("ROLE_COMMUNITY_MEMBER")).thenReturn(Optional.of(role));

        // Mock behavior of userVerifCodeRepository.save() to return a UserVerifCode object
        UserVerifCode userVerifCode = new UserVerifCode();
        when(userVerifCodeRepository.save(any(UserVerifCode.class))).thenReturn(userVerifCode);

        // Mock behavior of userRepository.save() to return a User object
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Mock behavior of userSettingsRepo.save() to return a UserSettings object
        when(userSettingsRepo.save(any(UserSettings.class))).thenReturn(new UserSettings());


        // Mock behavior of mailingService.sendEmail() to do nothing


        // Call the register method
        ResponseEntity<?> registerResponse = authService.register(registerDTO);


        // Add assertions as necessary based on the expected behavior of the method
        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        // Add more assertions based on the expected ResponseEntity content
    }


    @Test
    void TestExistUserByEmail() {
        String email = "john.doe@test.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);
        {
            boolean result = userRepository.existsByEmail(email);
            assertTrue(result);
        }

        when(userRepository.existsByEmail(email)).thenReturn(false);
        {
            boolean result = userRepository.existsByEmail(email);

            assertFalse(result);
        }
    }

    @Test
    void testUpdatePassword() throws MessagingException {
        String encodedNewPassword = "encodednewpassword";
        String newPassword = "newpassword";

        given(userRepository
                .findByEmail(user.getEmail()))
                .willReturn(Optional.of(user));

        given(userVerifCodeRepository.save(verifCode)).willReturn(verifCode);

        user.setUserVerifCode(verifCode);

        given(userRepository.save(user)).willReturn(user);

        given(userVerifCodeRepository
                .findByCodeAndEmail(verifCode.getCode(), user.getEmail()))
                .willReturn(Optional.of(verifCode));

        boolean b = authUtilsService.verifyCodeValidity(verifCode);

        given(passwordEncoder.encode(newPassword)).willReturn(encodedNewPassword);

        given(userRepository.save(user)).willReturn(user);

        ApiResponse res1 = authService.forgotPasswordRequest(user.getEmail());

        ApiResponse res2 = authService.verifyResetPasswordCode(user.getEmail(), verifCode.getCode());

        ApiResponse res3 = authService.updatePassword(user.getEmail(), newPassword, "45615");

        assertThat(res1.getStatus()).isEqualTo(200);

        assertThat(res2.getStatus()).isEqualTo(200);

        assertThat(res3.getStatus()).isEqualTo(200);

        assertThat(b).isTrue();
    }

 */
}
