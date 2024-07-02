package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.auth.IAuthService;
import com.tawasalna.auth.businesslogic.auth.IPMSAuth;
import com.tawasalna.auth.businesslogic.role.IRoleService;
import com.tawasalna.auth.models.Role;
import com.tawasalna.auth.businesslogic.usermanagement.IUserManagementService;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.payload.request.*;
import com.tawasalna.auth.payload.response.EmailVerifResp;
import com.tawasalna.auth.payload.response.JwtResponse;
import com.tawasalna.auth.payload.response.RefreshResp;
import com.tawasalna.auth.payload.response.RegisterResp;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Controller with methods for authentication")
@CrossOrigin("*")
public class AuthController {

    private final IAuthService iAuthService;
    private final IPMSAuth ipmsAuth;
    private final IRoleService roleService;
    private final IUserManagementService userManagementService;


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDTO user) {
        return new ResponseEntity<>(iAuthService.login(user), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.getAllRole(), HttpStatus.OK);
    }

    @PostMapping("/existsByEmail")
    public ResponseEntity<EmailVerifResp> existsByEmail(@RequestParam("email") String email) {
        return iAuthService.existsByEmail(email);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterDTO registerDTO) throws MessagingException {
        return iAuthService.register(registerDTO);
    }

    @PostMapping(path = "/post-register-upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> postRegisterUpload(@RequestParam("accId") String accId, @RequestPart(value = "logo") MultipartFile logo) throws MessagingException, ExecutionException, InterruptedException {
        final ApiResponse resp = iAuthService.addLogoToBusiness(logo, accId);
        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ApiResponse> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) throws MessagingException {

        final ApiResponse resp = iAuthService.forgotPasswordRequest(forgetPasswordDTO.getEmail());

        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PatchMapping("/reset-code")
    public ResponseEntity<ApiResponse> resetCode(@RequestBody VerifyCodeDTO forgetPasswordDTO) throws MessagingException {

        final ApiResponse resp = iAuthService.updateCode(forgetPasswordDTO.getEmail(), forgetPasswordDTO.getCode());

        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<ApiResponse> verifyCode(@RequestBody VerifyCodeDTO forgotPasswordCodeDTO) {

        final ApiResponse resp = iAuthService.verifyResetPasswordCode(forgotPasswordCodeDTO.getEmail(), forgotPasswordCodeDTO.getCode());

        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PatchMapping("/verifyAccount")
    public ResponseEntity<ApiResponse> verifyUserAccount(@RequestBody VerifyCodeDTO verifyAccDto) {
        final ApiResponse resp = iAuthService.verifyUserAccount(verifyAccDto.getEmail(), verifyAccDto.getCode());
        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PatchMapping("/resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws MessagingException {
        final ApiResponse resp = iAuthService.updatePassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getNewPassword(), resetPasswordDTO.getCode());

        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshAccessToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        try {
            final RefreshResp resp = iAuthService.refreshToken(refreshTokenDTO.getExpiredToken());
            if (resp == null)
                return new ResponseEntity<>(new ApiResponse(null, "Invalid token", 400), HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            log.error("REFRESH TOKEN ERROR", e);
            return new ResponseEntity<>(new ApiResponse(null, "Invalid token", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/createAccount")
    public ResponseEntity<RegisterResp> createAccount(@Valid @RequestBody RegisterPmsDTO registerPmsDTO) throws MessagingException {
        return new ResponseEntity<>(ipmsAuth.CreateAccount(registerPmsDTO), HttpStatus.CREATED);
    }

    @PostMapping("/signupRequest")
    public ResponseEntity<RegisterResp> signupRequest(@Valid @RequestBody RegisterPmsDTO registerPmsDTO) throws MessagingException {
        return new ResponseEntity<>(ipmsAuth.SignupRequest(registerPmsDTO), HttpStatus.CREATED);
    }
    @PostMapping("/staffSignup")
    public ResponseEntity<RegisterResp> staffSignup(@Valid @RequestBody RegisterPmsDTO registerPmsDTO) throws MessagingException {
        return new ResponseEntity<>(ipmsAuth.SignupStaff(registerPmsDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<ApiResponse> updatePasswordPMS(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        final ApiResponse resp = ipmsAuth.updatePassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @GetMapping("/agents")
    public ResponseEntity<List<Users>> getAgents() {
        return ResponseEntity.ok(userManagementService.getAgents());
    }

    @GetMapping("/brokers")
    public ResponseEntity<List<Users>> getBrokers() {
        return ResponseEntity.ok(userManagementService.getBrokers());
    }

    @GetMapping("/brokersAccepted")
    public ResponseEntity<List<Users>> getBrokersAccepted() {
        return ResponseEntity.ok(userManagementService.findBrokersWithAcceptedSignupRequest());
    }
    @GetMapping("/agentsAccepted")
    public ResponseEntity<List<Users>> getAgentsAccepted() {
        return ResponseEntity.ok(userManagementService.findAgentsWithAcceptedSignupRequest());
    }

    @GetMapping("/pms-admins")
    public ResponseEntity<List<Users>> getPmsAdmins() {
        return ResponseEntity.ok(userManagementService.getPMSAdmins());
    }
    @GetMapping("/prospects")
    public ResponseEntity<List<Users>> getProspects() {
        return ResponseEntity.ok(userManagementService.getProspects());
    }

    @GetMapping("/pms-adminspercommunity")
    public ResponseEntity<List<Users>> getPmsAdminsadminspercommunity( @RequestBody Community community) {
        return ResponseEntity.ok(userManagementService.getPmsAdminsPerCommunity(community));
    }

    @GetMapping("/getpasswordByEmail/{email}")
    public ResponseEntity<?> getPasswordByEmail(@PathVariable String email) {
        String password = iAuthService.getPasswordByEmail(email);
        if (password != null) {
            return ResponseEntity.ok(password);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password not found for the given email");
        }
    }

    @DeleteMapping("/deleteuser/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable String userId) {
        try {
            iAuthService.deleteUserById(userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error deleting user with ID {}: {}", userId, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting user with ID {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }
}
