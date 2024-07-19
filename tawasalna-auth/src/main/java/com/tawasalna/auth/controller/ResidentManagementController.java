package com.tawasalna.auth.controller;

import com.tawasalna.auth.models.Users;

import com.tawasalna.auth.payload.request.AddSecondaryEmailDTO;
import com.tawasalna.auth.payload.request.CheckPasswordMatchDTO;
import com.tawasalna.auth.payload.request.UpdatePasswordDTO;
import com.tawasalna.auth.payload.request.VerifySecondaryEmailCodeDTO;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.auth.businesslogic.ResidentManagement.IResidentManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/residentmanagement")
@RequiredArgsConstructor
public class ResidentManagementController {
    private final IResidentManagementService iResidentManagementService;
    private final UserRepository userRepository;

    @PatchMapping("/updatepassword/{userId}")
    public ResponseEntity<?> updatePasswordById(@PathVariable String userId, @RequestBody UpdatePasswordDTO updatePasswordDTO) {

        return new ResponseEntity<> (iResidentManagementService.updatePasswordById(userId, updatePasswordDTO), HttpStatus.OK);


    }

    @PostMapping("/checkPasswordMatch/{userId}")
    public ResponseEntity<?> checkPasswordMatch(@PathVariable String userId, @RequestBody CheckPasswordMatchDTO checkPasswordMatchDTO) throws MessagingException {
        return new ResponseEntity<> (iResidentManagementService.isPasswordMatch(userId, checkPasswordMatchDTO),HttpStatus.OK);

    }

    @PostMapping("/switchmainemail/{userId}")
    public ResponseEntity<?> SwitchMainEmail(@PathVariable String userId) {
        try {
            ApiResponse response = iResidentManagementService.switchMainEmail(userId);
            return ResponseEntity.ok(response);
        } catch (InvalidUserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to switch mail email.");
        }
    }

    @GetMapping("/getuser/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            Optional<Users> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error retrieving user with ID {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user.");
        }
    }

    @PostMapping("/addsecondaryemail/{userId}")
    public ResponseEntity<?> AddSecondaryEmail(@PathVariable String userId, @RequestBody AddSecondaryEmailDTO addSecondaryEmailDTO) throws MessagingException {
        return new ResponseEntity<> (iResidentManagementService.addSecondaryEmail(userId, addSecondaryEmailDTO), HttpStatus.OK);

    }

    @PostMapping("/deletesecondaryemail/{userId}")
    public ResponseEntity<?> DeleteSecondaryEmail(@PathVariable String userId, @RequestBody AddSecondaryEmailDTO addSecondaryEmailDTO) throws MessagingException {
        return new ResponseEntity<> (iResidentManagementService.DeleteSecondaryEmail(userId, addSecondaryEmailDTO), HttpStatus.OK);
    }

    @PostMapping ("/verifysecondaryemailcode/{userId}")
    public ResponseEntity<?> verifySecondaryEmailCode(@PathVariable String userId , @RequestBody VerifySecondaryEmailCodeDTO verifySecondaryEmailCodeDTO) {
        return new ResponseEntity<>(iResidentManagementService.verifySecondaryEmailCode(userId, verifySecondaryEmailCodeDTO), HttpStatus.OK);
    }



}


