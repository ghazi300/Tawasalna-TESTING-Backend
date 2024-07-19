package com.tawasalna.auth.businesslogic;

import com.tawasalna.auth.businesslogic.ResidentManagement.ResidentManagementServiceImpl;
import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidUserVerifCodeException;
import com.tawasalna.auth.models.UserVerifCode;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.payload.request.AddSecondaryEmailDTO;
import com.tawasalna.auth.payload.request.CheckPasswordMatchDTO;
import com.tawasalna.auth.payload.request.UpdatePasswordDTO;
import com.tawasalna.auth.payload.request.VerifySecondaryEmailCodeDTO;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.auth.repository.UserVerifCodeRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.mail.IMailingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ResidentManagementServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserVerifCodeRepository userVerifCodeRepository;

    @Mock
    private IMailingService mailingService;

    @Mock
    private IAuthUtilsService authUtilsService;

    @InjectMocks
    private ResidentManagementServiceImpl residentManagementService;

    @BeforeEach
    void setUp() {
    }


    /*
    @Test
    void testUpdatePasswordById_WhenUserExists() {
        // Setup
        String userId = "user123";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";
        String currentPassword = "currentPassword";

        Users user = new Users();
        user.setId(userId);
        user.setPassword(currentPassword);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(authUtilsService.comparePwd(currentPassword, currentPassword)).thenReturn(true);
        when(authUtilsService.comparePwd(newPassword, currentPassword)).thenReturn(false);

        // Invoke
        ApiResponse response = residentManagementService.updatePasswordById(userId, new UpdatePasswordDTO(newPassword, confirmPassword, currentPassword));

        // Verify
        assertTrue(response.getMessage().contains("Password updated successfully"));
    }

     */


    @Test
    void testUpdatePasswordById_WhenUserDoesNotExist() {
        // Setup
        String userId = "nonExistentUser";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";
        String currentPassword = "currentPassword";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Invoke and Verify
        assertThrows(InvalidUserException.class, () -> {
            residentManagementService.updatePasswordById(userId, new UpdatePasswordDTO(newPassword, confirmPassword, currentPassword));
        });
    }


    @Test
    void testIsPasswordMatch_WhenPasswordMatches() throws MessagingException {
        // Setup
        String userId = "user123";
        String enteredPassword = "password";

        Users user = new Users();
        user.setId(userId);
        user.setPassword(enteredPassword);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(authUtilsService.comparePwd(enteredPassword, enteredPassword)).thenReturn(true);

        // Invoke
        ApiResponse response = residentManagementService.isPasswordMatch(userId, new CheckPasswordMatchDTO(enteredPassword));

        // Verify
        assertTrue(response.getMessage().contains("Password Verification done!"));
    }



    @Test
    void testIsPasswordMatch_WhenPasswordDoesNotMatch() throws MessagingException {
        // Setup
        String userId = "user123";
        String enteredPassword = "password";
        String actualPassword = "actualPassword";

        Users user = new Users();
        user.setId(userId);
        user.setPassword(actualPassword);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(authUtilsService.comparePwd(enteredPassword, actualPassword)).thenReturn(false);

        // Invoke and Verify
        assertThrows(InvalidUserException.class, () -> {
            residentManagementService.isPasswordMatch(userId, new CheckPasswordMatchDTO(enteredPassword));
        });
    }



    @Test
    void testSwitchMainEmail_WhenUserExists() {
        // Setup
        String userId = "user123";
        String mainEmail = "main@example.com";
        String secondaryEmail = "secondary@example.com";

        Users user = new Users();
        user.setId(userId);
        user.setEmail(mainEmail);
        user.setSecondaryemail(secondaryEmail);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Invoke
        ApiResponse response = residentManagementService.switchMainEmail(userId);

        // Verify
        assertTrue(response.getMessage().contains("Success"));
    }




    @Test
    void testSwitchMainEmail_WhenUserDoesNotExist() {
        // Setup
        String userId = "nonExistentUser";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Invoke and Verify
        assertThrows(InvalidUserException.class, () -> {
            residentManagementService.switchMainEmail(userId);
        });
    }



    @Test
    void testVerifySecondaryEmailCode_WhenCodeIsValidAndUserExists() {
        // Setup
        String userId = "user123";
        String email = "secondary@example.com";
        String code = "123456";

        UserVerifCode userVerifCode = new UserVerifCode(null, code, email, LocalDateTime.now().plusMinutes(5));

        Users user = new Users();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userVerifCodeRepository.findByCodeAndEmail(code, email)).thenReturn(Optional.of(userVerifCode));

        // Invoke
        ApiResponse response = residentManagementService.verifySecondaryEmailCode(userId, new VerifySecondaryEmailCodeDTO(email, code));

        // Verify
        assertTrue(response.getMessage().contains("Success"));
    }



    @Test
    void testVerifySecondaryEmailCode_WhenCodeIsInvalid() {
        // Setup
        String userId = "user123";
        String email = "secondary@example.com";
        String code = "123456";

        when(userVerifCodeRepository.findByCodeAndEmail(code, email)).thenReturn(Optional.empty());

        // Invoke and Verify
        assertThrows(InvalidUserVerifCodeException.class, () -> {
            residentManagementService.verifySecondaryEmailCode(userId, new VerifySecondaryEmailCodeDTO(email, code));
        });
    }


/*
    @Test
    void testAddSecondaryEmail_WhenUserExists() throws MessagingException {
        // Setup
        String userId = "user123";
        String email = "secondary@example.com";
        String verificationCode = "123456";

        Users user = new Users();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(authUtilsService.generateCode()).thenReturn(verificationCode);

        // Invoke
        ApiResponse response = residentManagementService.addSecondaryEmail(userId, new AddSecondaryEmailDTO(email));

        // Verify
        assertTrue(response.getMessage().contains("Success"));
        verify(userRepository, times(1)).findById(userId);
        verify(userVerifCodeRepository, times(1)).save(any(UserVerifCode.class));
        verify(mailingService, times(1)).sendEmail(eq(email), anyString(), anyString(), anyList());
    }

*/

    @Test
    void testAddSecondaryEmail_WhenUserDoesNotExist() {
        // Setup
        String userId = "nonExistentUser";
        String email = "secondary@example.com";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Invoke and Verify
        assertThrows(InvalidUserVerifCodeException.class, () -> {
            residentManagementService.addSecondaryEmail(userId, new AddSecondaryEmailDTO(email));
        });
    }


/*
    @Test
    void testDeleteSecondaryEmail_WhenUserExists() throws MessagingException {
        // Setup
        String userId = "user123";
        String email = "secondary@example.com";
        String verificationCode = "123456";

        Users user = new Users();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(authUtilsService.generateCode()).thenReturn(verificationCode);

        // Invoke
        ApiResponse response = residentManagementService.DeleteSecondaryEmail(userId, new AddSecondaryEmailDTO(email));

        // Verify
        assertTrue(response.getMessage().contains("Success"));
        verify(userRepository, times(1)).findById(userId);
        verify(userVerifCodeRepository, times(1)).save(any(UserVerifCode.class));
        verify(mailingService, times(1)).sendEmail(eq(email), anyString(), anyString(), anyList());
    }
*/

    @Test
    void testDeleteSecondaryEmail_WhenUserDoesNotExist() {
        // Setup
        String userId = "nonExistentUser";
        String email = "secondary@example.com";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Invoke and Verify
        assertThrows(InvalidUserVerifCodeException.class, () -> {
            residentManagementService.DeleteSecondaryEmail(userId, new AddSecondaryEmailDTO(email));
        });
    }



}