package com.tawasalna.auth.businesslogic.businessmanagement;

import com.tawasalna.auth.payload.request.BusinessProfileDTO;
import com.tawasalna.auth.payload.request.PhoneNumberDTO;
import com.tawasalna.auth.payload.request.PhoneUpdateDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

// needs testing
public interface IBusinessManagementService {

    ResponseEntity<ApiResponse> updateBusinessProfile(BusinessProfileDTO businessProfileDTO, String userId);

    ApiResponse updateBusinessLogo(MultipartFile logo, String id);

    ApiResponse updateBusinessCoverPhoto(MultipartFile coverPhoto, String id);

    CompletableFuture<ResponseEntity<FileSystemResource>> getLogo(String id) throws IOException;

    CompletableFuture<ResponseEntity<FileSystemResource>> getCover(String id) throws IOException;

    ResponseEntity<ApiResponse> changePhoneReq(String userId, PhoneNumberDTO phoneNumberDTO);

    ResponseEntity<ApiResponse> resendPhoneUpdateCode(String userId, PhoneUpdateDTO phoneNumberDTO);

    ResponseEntity<ApiResponse> verifyPhoneCode(String userId, String code, String phone);
}
