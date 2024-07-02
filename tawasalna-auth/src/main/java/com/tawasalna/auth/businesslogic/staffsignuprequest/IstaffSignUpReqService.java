package com.tawasalna.auth.businesslogic.staffsignuprequest;

import com.tawasalna.auth.models.StaffSignupRequest;
import com.tawasalna.auth.payload.request.StaffSignupRequestDTO;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.List;

public interface IstaffSignUpReqService {
   StaffSignupRequest makesignupRequest(StaffSignupRequestDTO request);
   List<StaffSignupRequest> getSignupRequestsByAdminId(String adminId);
   List<StaffSignupRequest> getSignupRequestsByAdminIdAndBroker(String adminId);
   List<StaffSignupRequest> getSignupRequestsByAdminIdAndAgent(String adminId);

   ResponseEntity<StaffSignupRequest> acceptRequest(String requestId) throws MessagingException;
   ResponseEntity<StaffSignupRequest> reject(StaffSignupRequestDTO requestDTO, String requestId) throws MessagingException;
   ResponseEntity<StaffSignupRequest> Archiver(StaffSignupRequestDTO requestDTO);
}
