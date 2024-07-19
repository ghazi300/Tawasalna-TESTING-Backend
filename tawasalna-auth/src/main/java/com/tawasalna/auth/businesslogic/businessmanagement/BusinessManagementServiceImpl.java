package com.tawasalna.auth.businesslogic.businessmanagement;

import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidUserVerifCodeException;
import com.tawasalna.auth.models.UserVerifCode;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.payload.request.BusinessProfileDTO;
import com.tawasalna.auth.payload.request.PhoneNumberDTO;
import com.tawasalna.auth.payload.request.PhoneUpdateDTO;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.auth.repository.UserVerifCodeRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.sms.ISmsService;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@AllArgsConstructor
public class BusinessManagementServiceImpl implements IBusinessManagementService {

    private final UserRepository userRepository;
    private final IFileManagerService fileManager;
    private final ISmsService smsService;
    private final UserVerifCodeRepository userVerifCodeRepository;
    private final IAuthUtilsService authUtilsService;

    @Override
    public ResponseEntity<ApiResponse> updateBusinessProfile(BusinessProfileDTO businessProfileDTO, String userId) {

        final String proEmail = businessProfileDTO.getProfessionalEmail();

        userRepository
                .findById(userId)
                .map(u -> {
                    u.setAddress(businessProfileDTO.getAddress());
                    u.getBusinessProfile().setBusinessName(businessProfileDTO.getBusinessName());
                    u.getBusinessProfile().setCity(businessProfileDTO.getCity());
                    u.getBusinessProfile().setCountry(businessProfileDTO.getCountry());
                    u.getBusinessProfile().setProfessionalEmail(proEmail);
                    u.getBusinessProfile().setDomain(proEmail.substring(proEmail.indexOf('@')));
                    u.getBusinessProfile().setWebsite(businessProfileDTO.getWebsite());
                    u.getBusinessProfile().setPostalCode(businessProfileDTO.getPostalCode());
                    u.getBusinessProfile().setLinkedin(businessProfileDTO.getLinkedin());
                    u.getBusinessProfile().setMatriculate(businessProfileDTO.getMatriculate());
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new InvalidUserException(userId, "User not found"));

        return ResponseEntity.ok(new ApiResponse("Profile updated successfully!", null, 200));
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<FileSystemResource>> getLogo(String id)
            throws IOException {

        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(
                                id,
                                Consts.USER_NOT_FOUND
                        )
                );


        String businessLogoName = user.getBusinessProfile().getLogoName();
        return businessLogoName == null ? getLocalLogo() : getPhotoByName(businessLogoName);
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<FileSystemResource>> getCover(String id)
            throws IOException {

        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(
                                id,
                                Consts.USER_NOT_FOUND
                        )
                );

        String coverPhotoName = user.getBusinessProfile().getCoverPhotoName();
        return coverPhotoName == null ? getLocalPlaceholder() : getPhotoByName(coverPhotoName);
    }

    @NotNull
    private CompletableFuture<ResponseEntity<FileSystemResource>> getLocalPlaceholder() throws IOException {

        final ClassPathResource res = new ClassPathResource("static/cover_placeholder.jpg");

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
                .getFileWithMediaType(name, "business");

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

    @Override
    public ApiResponse updateBusinessLogo(MultipartFile logo, String id) {
        final String fileName = saveFileToDisk(logo);

        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(id, Consts.USER_NOT_FOUND));

        user.getBusinessProfile().setLogoName(fileName);
        userRepository.save(user);

        return ApiResponse.ofSuccess("Success", 200);
    }

    @Override
    public ApiResponse updateBusinessCoverPhoto(MultipartFile coverPhoto, String id) {
        final String fileName = saveFileToDisk(coverPhoto);

        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(id, Consts.USER_NOT_FOUND));

        user.getBusinessProfile().setCoverPhotoName(fileName);
        userRepository.save(user);

        return ApiResponse.ofSuccess("Success", 200);
    }


    @Override
    public ResponseEntity<ApiResponse> changePhoneReq(String userId, PhoneNumberDTO phoneNumberDTO) {
        final Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND)
                );

        final String code = authUtilsService.generateCode();
        final UserVerifCode uvc = new UserVerifCode(null, code, user.getEmail(), LocalDateTime.now().plusMinutes(15));

        user.setUserVerifCode(userVerifCodeRepository.save(uvc));

        final boolean delivered = smsService.deliverMessage(
                phoneNumberDTO.getPhoneNumber(),
                "Your verification code:\n" + code
        );

        if (!delivered) return ResponseEntity
                .badRequest()
                .body(
                        ApiResponse.ofError(
                                "Unable to process your request.",
                                400
                        )
                );

        return ResponseEntity.ok(ApiResponse.ofSuccess("An SMS with a code has been delivered to your phone number", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> resendPhoneUpdateCode(String userId, PhoneUpdateDTO phoneNumberDTO) {

        final Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND)
                );

        final String code = authUtilsService.generateCode();
        final UserVerifCode uvc = userVerifCodeRepository
                .findByCodeAndEmail(phoneNumberDTO.getCode(), user.getEmail())
                .orElseGet(UserVerifCode::new);

        uvc.setCode(code);
        uvc.setExpiredAt(LocalDateTime.now().plusMinutes(15));

        user.setUserVerifCode(userVerifCodeRepository.save(uvc));


        if (!smsService.deliverMessage(
                user.getBusinessProfile().getProfessionalPhone(),
                "Your new verification code:\n" + code
        )) return ResponseEntity
                .badRequest()
                .body(
                        ApiResponse.ofError(
                                "Unable to process your request, please try again later.",
                                400
                        )
                );

        return ResponseEntity.ok(ApiResponse.ofSuccess("An SMS has been sent containing your new code", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> verifyPhoneCode(String userId, String code, String phone) {

        final Users user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new InvalidUserException(userId, Consts.USER_NOT_FOUND)
                );

        final UserVerifCode uvc = userVerifCodeRepository
                .findByCodeAndEmail(code, user.getEmail())
                .orElseThrow(() ->
                        new InvalidUserVerifCodeException(code, "Invalid code")
                );

        if (!authUtilsService.verifyCodeValidity(uvc))
            throw new InvalidUserVerifCodeException(uvc.getCode(), "Expired code");

        userVerifCodeRepository.delete(uvc);

        user.getBusinessProfile().setProfessionalPhone(phone);

        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Phone number updated successfully.", 200));
    }

    private String saveFileToDisk(MultipartFile file) {
        try {
            if (file == null || file.isEmpty())
                throw new InvalidEntityBaseException("file", "null", "invalid file");

            final String fileName = fileManager.uploadToLocalFileSystem(file, "business").get();

            if (fileName == null)
                throw new InvalidEntityBaseException("file", "null", "no file name");

            return fileName;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }
}
