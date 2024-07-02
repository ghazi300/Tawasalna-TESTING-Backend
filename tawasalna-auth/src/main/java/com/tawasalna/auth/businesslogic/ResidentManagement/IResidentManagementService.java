package com.tawasalna.auth.businesslogic.ResidentManagement;


import com.tawasalna.auth.payload.request.AddSecondaryEmailDTO;
import com.tawasalna.auth.payload.request.CheckPasswordMatchDTO;
import com.tawasalna.auth.payload.request.UpdatePasswordDTO;
import com.tawasalna.auth.payload.request.VerifySecondaryEmailCodeDTO;
import com.tawasalna.shared.dtos.ApiResponse;

import javax.mail.MessagingException;

public interface IResidentManagementService {
    ApiResponse updatePasswordById(String userId, UpdatePasswordDTO updatePasswordDTO);

     ApiResponse isPasswordMatch(String userId, CheckPasswordMatchDTO checkPasswordMatchDTO) throws MessagingException;


    ApiResponse switchMainEmail(String userId);



    public ApiResponse verifySecondaryEmailCode(String userId, VerifySecondaryEmailCodeDTO verifySecondaryEmailCodeDTO);

    ApiResponse addSecondaryEmail(String userId, AddSecondaryEmailDTO secondaryEmailDTO) throws MessagingException;

    ApiResponse DeleteSecondaryEmail(String userId, AddSecondaryEmailDTO secondaryEmailDTO) throws MessagingException, MessagingException, MessagingException, MessagingException;
}
