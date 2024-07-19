package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.pmsusersmanagement.IpmsUsersManagementService;
import com.tawasalna.auth.payload.request.AdminProfileDTO;
import com.tawasalna.auth.payload.request.AgentProfileDTO;
import com.tawasalna.auth.payload.request.BrokerProfileDTO;
import com.tawasalna.auth.payload.request.ProspectProfileDTO;
import com.tawasalna.shared.dtos.ApiResponse;
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
@RequestMapping("/profile")
@RequiredArgsConstructor
public class PMSUsersController {
    private final IpmsUsersManagementService ipmsUsersManagementService;

    @PutMapping("/updateAgent/{userId}")
    public ResponseEntity<ApiResponse> updateAgentProfile(
            @RequestBody AgentProfileDTO agentProfileDTO,
            @PathVariable("userId") String userId
    ) {
        return ipmsUsersManagementService.updateAgentProfile(agentProfileDTO, userId);
    }
    @PutMapping("/updateAdmin/{userId}")
    public ResponseEntity<ApiResponse> updateAdminProfile(
            @RequestBody AdminProfileDTO adminProfileDTO,
            @PathVariable("userId") String userId
    ) {
        return ipmsUsersManagementService.updateAdminProfile(adminProfileDTO, userId);
    }
    @PutMapping("/updateBroker/{userId}")
    public ResponseEntity<ApiResponse> updateBrokerProfile(
            @RequestBody BrokerProfileDTO brokerProfileDTO,
            @PathVariable("userId") String userId
    ) {
        return ipmsUsersManagementService.updateBrokerProfile(brokerProfileDTO, userId);
    }
    @PutMapping("/updateProspect/{userId}")
    public ResponseEntity<ApiResponse> updateProspectProfile(
            @RequestBody ProspectProfileDTO prospectProfileDTO,
            @PathVariable("userId") String userId
    ) {
        return ipmsUsersManagementService.updateProspectProfile(prospectProfileDTO, userId);
    }

    @PatchMapping(path = "/update-profileImage/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> updateLogo(
            @PathVariable("userId") String userId,
            @RequestPart(value = "logo") MultipartFile logo) {

        final ApiResponse resp = ipmsUsersManagementService.updateProfileImage(logo, userId);
        return new ResponseEntity<>(resp, HttpStatusCode.valueOf(resp.getStatus()));
    }
    @GetMapping("/getProfileImg/{id}")
    public ResponseEntity<FileSystemResource> getProfileImage(@PathVariable("id") String id)
            throws IOException, ExecutionException, InterruptedException {
        return ipmsUsersManagementService.getProfileImage(id).get();
    }
}
