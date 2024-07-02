package com.tawasalna.auth.businesslogic.pmsusersmanagement;

import com.tawasalna.auth.payload.request.AdminProfileDTO;
import com.tawasalna.auth.payload.request.AgentProfileDTO;
import com.tawasalna.auth.payload.request.BrokerProfileDTO;
import com.tawasalna.auth.payload.request.ProspectProfileDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface IpmsUsersManagementService {
    ResponseEntity<ApiResponse> updateAgentProfile(AgentProfileDTO agentProfileDTO, String userId);
    ResponseEntity<ApiResponse> updateAdminProfile(AdminProfileDTO adminProfileDTO, String userId);
    ResponseEntity<ApiResponse> updateBrokerProfile(BrokerProfileDTO brokerProfileDTO, String userId);
    ResponseEntity<ApiResponse> updateProspectProfile(ProspectProfileDTO prospectProfileDTO, String userId);
    ApiResponse updateProfileImage(MultipartFile image, String id);
    CompletableFuture<ResponseEntity<FileSystemResource>> getProfileImage(String id) throws IOException;


}
