package com.tawasalna.auth.businesslogic.pmsusersmanagement;

import com.tawasalna.auth.models.AgentProfile;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.payload.request.AdminProfileDTO;
import com.tawasalna.auth.payload.request.AgentProfileDTO;
import com.tawasalna.auth.payload.request.BrokerProfileDTO;
import com.tawasalna.auth.payload.request.ProspectProfileDTO;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@AllArgsConstructor
public class PmsUsersManagementServiceImp implements IpmsUsersManagementService {
    private final UserRepository userRepository;
    private final IFileManagerService fileManager;


    @Override
    public ResponseEntity<ApiResponse> updateAgentProfile (AgentProfileDTO agentProfileDTO, String userId) {

        userRepository
                .findById(userId)
                .map(u -> {
                    AgentProfile agentProfile = u.getAgentProfile();
                    if (agentProfile != null) {
                        u.getAgentProfile().setFirstname(agentProfileDTO.getFirstname());
                        u.getAgentProfile().setLastname(agentProfileDTO.getLastname());
                        u.getAgentProfile().setTitle(agentProfileDTO.getTitle());
                        u.getAgentProfile().setProfessionalEmail(agentProfileDTO.getProfessionalEmail());
                        u.getAgentProfile().setProfessionalPhone(agentProfileDTO.getProfessionalPhone());
                        u.getAgentProfile().setAddress(agentProfileDTO.getAddress());
                        u.getAgentProfile().setPostalCode(agentProfileDTO.getPostalCode());
                    }else {
                        throw new InvalidUserException(userId, Consts.USER_NOT_FOUND);
                    }
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse(Consts.SUCCESS, null, 200));
    }

    @Override
    public ResponseEntity<ApiResponse> updateAdminProfile(AdminProfileDTO adminProfileDTO, String userId) {
        userRepository
                .findById(userId)
                .map(u -> {
                    u.getAdminProfile().setFirstname(adminProfileDTO.getFirstname());
                    u.getAdminProfile().setLastname(adminProfileDTO.getLastname());
                    u.getAdminProfile().setTitle(adminProfileDTO.getTitle());
                    u.getAdminProfile().setProfessionalEmail(adminProfileDTO.getProfessionalEmail());
                    u.getAdminProfile().setProfessionalPhone(adminProfileDTO.getProfessionalPhone());
                    u.getAdminProfile().setAddress(adminProfileDTO.getAddress());
                    u.getAdminProfile().setPostalCode(adminProfileDTO.getPostalCode());
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse(Consts.SUCCESS, null, 200));
    }


    @Override
    public ResponseEntity<ApiResponse> updateBrokerProfile(BrokerProfileDTO brokerProfileDTO, String userId) {
        userRepository
                .findById(userId)
                .map(u -> {
                    u.getBrokerProfile().setFirstname(brokerProfileDTO.getFirstname());
                    u.getBrokerProfile().setLastname(brokerProfileDTO.getLastname());
                    u.getBrokerProfile().setProfessionalEmail(brokerProfileDTO.getProfessionalEmail());
                    u.getBrokerProfile().setProfessionalPhone(brokerProfileDTO.getProfessionalPhone());
                    u.getBrokerProfile().setAddress(brokerProfileDTO.getAddress());
                    u.getBrokerProfile().setPostalCode(brokerProfileDTO.getPostalCode());
                    u.getBrokerProfile().setTitle(brokerProfileDTO.getTitle());
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse(Consts.SUCCESS, null, 200));
    }

    @Override
    public ResponseEntity<ApiResponse> updateProspectProfile(ProspectProfileDTO prospectProfileDTO, String userId) {
        userRepository
                .findById(userId)
                .map(u -> {
                    u.getProspectProfile().setFirstname(prospectProfileDTO.getFirstname());
                    u.getProspectProfile().setLastname(prospectProfileDTO.getLastname());
                    u.getProspectProfile().setTitle(prospectProfileDTO.getTitle());
                    u.getProspectProfile().setAddress(prospectProfileDTO.getAddress());
                    u.getProspectProfile().setPostalCode(prospectProfileDTO.getPostalCode());
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse(Consts.SUCCESS, null, 200));
    }

    @Override
    public ApiResponse updateProfileImage(MultipartFile image, String id) {
        final String fileName = saveFileToDisk(image);

        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(id, Consts.USER_NOT_FOUND));

        // Mettre à jour l'image de profil pour chaque type de profil existant
        if (user.getAgentProfile() != null) {
            user.getAgentProfile().setProfileImage(fileName);
        }
        if (user.getBrokerProfile() != null) {
            user.getBrokerProfile().setProfileImage(fileName);
        }
        if (user.getAdminProfile() != null) {
            user.getAdminProfile().setProfileImage(fileName);
        }

        userRepository.save(user);

        return ApiResponse.ofSuccess("Success", 200);
    }

    @Override
    public CompletableFuture<ResponseEntity<FileSystemResource>> getProfileImage(String id) throws IOException {
        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(
                                id,
                                Consts.USER_NOT_FOUND
                        )
                );

        String profileImage = getProfileImage(user);
        return profileImage == null ? getLocalLogo() : getPhotoByName(profileImage);
    }

    private String getProfileImage(Users user) {
        if (user.getAgentProfile() != null && user.getAgentProfile().getProfileImage() != null) {
            return user.getAgentProfile().getProfileImage();
        } else if (user.getBrokerProfile() != null && user.getBrokerProfile().getProfileImage() != null) {
            return user.getBrokerProfile().getProfileImage();
        } else if (user.getAdminProfile() != null && user.getAdminProfile().getProfileImage() != null) {
            return user.getAdminProfile().getProfileImage();
        }
        return null;
    }

    @NotNull
    private CompletableFuture<ResponseEntity<FileSystemResource>>
    getPhotoByName(String name)
            throws IOException {

        if (name == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        final FileSystemResource fileSystemResource = fileManager
                .getFileWithMediaType(name, "PMS_profile_images");

        if (fileSystemResource == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        final String mediaType = Files
                .probeContentType(
                        fileSystemResource.getFile()
                                .toPath()
                );

        final ResponseEntity<FileSystemResource> resp = ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .body(fileSystemResource);

        return CompletableFuture.completedFuture(resp);
    }

    @NotNull
    private CompletableFuture<ResponseEntity<FileSystemResource>> getLocalLogo() throws IOException {

        final ClassPathResource res = new ClassPathResource("static/default-logo.png");

        FileSystemResource fileSystemResource = new FileSystemResource(res.getFile());

        final String mediaType = Files
                .probeContentType(
                        fileSystemResource.getFile()
                                .toPath()
                );

        final ResponseEntity<FileSystemResource> resp = ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .body(fileSystemResource);

        return CompletableFuture.completedFuture(resp);
    }


    private String saveFileToDisk(MultipartFile file) {
        try {
            if (file == null || file.isEmpty())
                throw new InvalidEntityBaseException("file", "null", "invalid file");

            final String fileName = fileManager.uploadToLocalFileSystem(file, "PMS_profile_images").get();

            if (fileName == null)
                throw new InvalidEntityBaseException("file", "null", "no file name");

            return fileName;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

}
