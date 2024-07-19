package com.tawasalna.auth.businesslogic.auth;

import com.tawasalna.auth.payload.request.RegisterPmsDTO;
import com.tawasalna.auth.payload.response.RegisterResp;
import com.tawasalna.shared.dtos.ApiResponse;

import javax.mail.MessagingException;

public interface IPMSAuth {

    RegisterResp CreateAccount(RegisterPmsDTO registerPmsDTO) throws MessagingException;
    ApiResponse updatePassword(String email, String newPassword);
    RegisterResp SignupRequest(RegisterPmsDTO registerPmsDTO) throws MessagingException;
    RegisterResp SignupStaff(RegisterPmsDTO registerPmsDTO) throws MessagingException;
}
