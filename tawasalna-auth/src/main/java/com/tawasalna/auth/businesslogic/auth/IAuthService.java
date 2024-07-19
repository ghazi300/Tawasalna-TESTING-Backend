package com.tawasalna.auth.businesslogic.auth;

import com.tawasalna.auth.payload.request.LoginDTO;
import com.tawasalna.auth.payload.request.RegisterDTO;
import com.tawasalna.auth.payload.response.EmailVerifResp;
import com.tawasalna.auth.payload.response.JwtResponse;
import com.tawasalna.auth.payload.response.RefreshResp;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutionException;

public interface IAuthService {
    JwtResponse login(LoginDTO loginDTO);

    RefreshResp refreshToken(String expired);

    ResponseEntity<Object> register(RegisterDTO registerDTO) throws MessagingException;

    ApiResponse addLogoToBusiness(MultipartFile logo, String accId) throws ExecutionException, InterruptedException, MessagingException;

    ApiResponse verifyUserAccount(String email, String verifCode);

    ApiResponse forgotPasswordRequest(String email) throws MessagingException;

    ApiResponse updateCode(String email, String code) throws MessagingException;

    ApiResponse verifyResetPasswordCode(String email, String code);
    
    String getPasswordByEmail(String email) ;

    void deleteUserById(String userId);

    ApiResponse updatePassword(String email, String newPassword, String code) throws MessagingException;

    ResponseEntity<EmailVerifResp> existsByEmail(String email);
}
