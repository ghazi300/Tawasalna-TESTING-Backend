package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.businessmanagement.IBusinessManagementService;
import com.tawasalna.auth.payload.request.BusinessProfileDTO;
import com.tawasalna.auth.payload.request.PhoneNumberDTO;
import com.tawasalna.auth.payload.request.PhoneUpdateDTO;
import com.tawasalna.auth.payload.request.VerifyPhoneCodeDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/business-profile")
@Tag(name = "Business Controller", description = "Controller with CRUD methods for various business role operations")
public class BusinessController {

    private final IBusinessManagementService businessManagementService;

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateBusinessProfileDetails(
            @RequestBody BusinessProfileDTO businessProfileDTO,
            @PathVariable("userId") String userId
    ) {
        return businessManagementService.updateBusinessProfile(businessProfileDTO, userId);
    }

    @GetMapping("/logo/{id}")
    public ResponseEntity<FileSystemResource> getLogo(@PathVariable("id") String id)
            throws IOException, ExecutionException, InterruptedException {
        return businessManagementService.getLogo(id).get();
    }

    @GetMapping("/cover/{id}")
    public ResponseEntity<FileSystemResource> getCover(@PathVariable("id") String id)
            throws IOException, ExecutionException, InterruptedException {
        return businessManagementService.getCover(id).get();
    }


    @PatchMapping(path = "/update-logo/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> updateLogo(
            @PathVariable("userId") String userId,
            @RequestPart(value = "logo") MultipartFile logo) {

        final ApiResponse resp = businessManagementService.updateBusinessLogo(logo, userId);
        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PatchMapping(path = "/update-cover-photo/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> updateCoverPhoto(
            @PathVariable("userId") String userId,
            @RequestPart(value = "coverPhoto") MultipartFile coverPhoto) {

        final ApiResponse resp = businessManagementService.updateBusinessCoverPhoto(coverPhoto, userId);
        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }

    @PostMapping("/update-phone-request/{id}")
    ResponseEntity<ApiResponse> changePhoneReq(@PathVariable("id") String userId, @Valid @RequestBody PhoneNumberDTO phoneNumberDTO) {
        return businessManagementService.changePhoneReq(userId, phoneNumberDTO);
    }

    @PatchMapping("/resend-phone-code/{id}")
    ResponseEntity<ApiResponse> resendPhoneUpdateCode(@PathVariable("id") String userId, @Valid @RequestBody PhoneUpdateDTO phoneNumberDTO) {
        return businessManagementService.resendPhoneUpdateCode(userId, phoneNumberDTO);
    }

    @PostMapping("/verify-phone-code/{id}")
    ResponseEntity<ApiResponse> verifyPhoneCode(@PathVariable("id") String userId, @Valid @RequestBody VerifyPhoneCodeDTO verifyPhoneCodeDTO) {
        return businessManagementService.verifyPhoneCode(userId, verifyPhoneCodeDTO.getCode(), verifyPhoneCodeDTO.getPhone());
    }
}
