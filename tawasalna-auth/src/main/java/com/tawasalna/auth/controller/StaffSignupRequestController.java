package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.staffsignuprequest.IstaffSignUpReqService;
import com.tawasalna.auth.models.Role;
import com.tawasalna.auth.models.StaffSignupRequest;
import com.tawasalna.auth.payload.request.StaffSignupRequestDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/staffSignupRequest")
@RequiredArgsConstructor
@Tag(name = "Staff Signup request Controller", description = "Controller with methods for staff signup request")
@CrossOrigin("*")
public class StaffSignupRequestController {
    private final IstaffSignUpReqService istaffSignUpReqService;

    @GetMapping("/listResuestPerAmin/{adminId}")
    public ResponseEntity<List<StaffSignupRequest>> getSignupRequestsByAdminId(@PathVariable String adminId) {
        return new ResponseEntity<>(istaffSignUpReqService.getSignupRequestsByAdminId(adminId), HttpStatus.OK);
    }
    @GetMapping("/listResuestPerAminAndBroker/{adminId}")
    public ResponseEntity<List<StaffSignupRequest>> getSignupRequestsByAdminIdAndBroker(@PathVariable String adminId) {
        return new ResponseEntity<>(istaffSignUpReqService.getSignupRequestsByAdminIdAndBroker(adminId), HttpStatus.OK);
    }

    @GetMapping("/listResuestPerAminAndAgent/{adminId}")
    public ResponseEntity<List<StaffSignupRequest>> getSignupRequestsByAdminIdAndAgent(@PathVariable String adminId) {
        return new ResponseEntity<>(istaffSignUpReqService.getSignupRequestsByAdminIdAndAgent(adminId), HttpStatus.OK);
    }

    @PutMapping("/acceptRequest/{requestId}")
    public ResponseEntity<StaffSignupRequest> acceptRequest(@PathVariable String requestId) throws MessagingException {
        return istaffSignUpReqService.acceptRequest(requestId);
    }
    @PutMapping("/rejectRequest/{requestId}")
    public ResponseEntity<StaffSignupRequest> rejectRequest(@RequestBody StaffSignupRequestDTO requestDTO, @PathVariable String requestId) throws MessagingException {
        return istaffSignUpReqService.reject(requestDTO, requestId);
    }
}
