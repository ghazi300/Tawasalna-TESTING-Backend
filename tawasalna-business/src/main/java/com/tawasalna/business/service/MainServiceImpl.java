package com.tawasalna.business.service;

import com.tawasalna.business.models.ServiceFeature;
import com.tawasalna.business.payload.request.SearchType;
import com.tawasalna.business.payload.response.CommunityResp;
import com.tawasalna.business.payload.response.SearchRes;
import com.tawasalna.business.repository.*;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidCommunityException;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.mail.IMailingService;
import com.tawasalna.shared.mail.TemplateVariable;
import com.tawasalna.shared.userapi.model.AccountStatus;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.utils.Consts;
import com.tawasalna.shared.verificationrequestapi.model.VerificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
@Slf4j
@AllArgsConstructor
public class MainServiceImpl implements IMainService {

    private final IFileManagerService fileManagerService;
    private final VerificationRequestRepository verificationRequestRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final IMailingService mailingService;
    private final ProductRepository productRepository;
    private final BusinessServiceRepository serviceRepository;

    @Override
    public ResponseEntity<ApiResponse>
    requestBusinessVerification(String userId, String communityId, MultipartFile document) {

        final Users user = userRepository
                .findById(userId)
                .orElseThrow(() -> new
                                InvalidUserException(
                                userId,
                                Consts.USER_NOT_FOUND
                        )
                );

        final boolean exists = verificationRequestRepository
                .findVerificationRequestByRequesterAndIsArchivedFalseAndIsAcceptedFalse(user)
                .isPresent();

        if (exists)
            return ResponseEntity.badRequest()
                    .body(ApiResponse
                            .ofError(
                                    "Request already sent.",
                                    400)
                    );
        try {
            final String fileName = fileManagerService
                    .uploadToLocalFileSystem(
                            document,
                            Consts.BUSINESS.toLowerCase())
                    .get();

            final Community community = communityRepository
                    .findById(communityId)
                    .orElseThrow(() ->
                            new InvalidCommunityException(
                                    "community id null",
                                    Consts.COMMUNITY_NOT_FOUND
                            )
                    );

            verificationRequestRepository.save(
                    new VerificationRequest(
                            null,
                            fileName,
                            user,
                            community,
                            false,
                            null,
                            false
                    )
            );

            final List<TemplateVariable> vars = new ArrayList<>();

            vars.add(new TemplateVariable("bName", user
                    .getBusinessProfile().getBusinessName()));

            mailingService.sendEmail(community
                            .getAdmins()
                            .stream().map(Users::getEmail)
                            .toList(),
                    "New business has been added",
                    "BusinessVerifRequestEmail.html",
                    vars
            );

            return ResponseEntity.ok(ApiResponse
                    .ofSuccess(
                            "Request delivered, awaiting approval.",
                            200
                    )
            );

        } catch (ExecutionException | InterruptedException |
                 MessagingException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse
                            .ofError(
                                    "Error occurred.",
                                    500
                            )
                    );
        }
    }

    @Override
    public ResponseEntity<Page<Users>> getShopsForCommunity(String communityId,
                                                            Integer pageNumber
    ) {
        if (pageNumber <= 1) pageNumber = 1;
        return ResponseEntity.ok(userRepository
                .findAllByAccountStatusAndBusinessProfile_VerifiedAndCommunity
                        (
                                AccountStatus.ENABLED,
                                true,
                                communityId,
                                PageRequest.of(
                                        pageNumber - 1,
                                        10
                                )
                        ));
    }

    @Override
    public ResponseEntity<List<CommunityResp>> fetchCommunities() {
        return ResponseEntity
                .ok(communityRepository
                        .findAll()
                        .stream()
                        .map(c ->
                                new CommunityResp(
                                        c.getId(),
                                        c.getName()
                                )
                        )
                        .toList()
                );
    }

    @Override
    public ResponseEntity<List<SearchRes>> search(String query, SearchType searchType) {
        final List<SearchRes> results = new ArrayList<>(0);

        switch (searchType) {
            case PRODUCT -> productRepository
                    .findAllByTitleLikeIgnoreCaseAndIsArchivedFalse(query)
                    .stream()
                    .map(p -> {
                        final SearchRes res = new SearchRes();
                        res.setTitle(p.getTitle());
                        res.setContent(p.getDescription());
                        res.setSearchType(SearchType.PRODUCT);
                        res.setId(p.getId());
                        res.setPrice(p.getPrice());
                        res.setRating(p.getAverageStars());
                        return res;
                    })
                    .forEach(results::add);
            case SERVICE -> serviceRepository
                    .findAllByTitleLikeIgnoreCaseAndIsArchivedFalse(query)
                    .stream()
                    .map(s -> {
                        final SearchRes res = new SearchRes();
                        res.setTitle(s.getTitle());
                        res.setContent(s.getDescription());
                        res.setSearchType(SearchType.SERVICE);
                        res.setId(s.getId());
                        res.setPrice((float) (s.getBasePrice() + s.getAdditionalFeatures().stream().mapToDouble(ServiceFeature::getPrice).sum()));
                        res.setRating(s.getAverageStars());
                        return res;
                    })
                    .forEach(results::add);
            case USER -> userRepository
                    .searchUsersOfCommunity(
                            AccountStatus.ENABLED,
                            true,
                            query,
                            query
                    )
                    .stream()
                    .map(s -> {
                        final SearchRes res = new SearchRes();
                        res.setSearchType(SearchType.USER);
                        res.setId(s.getId());

                        s.getRoles().stream().findFirst().ifPresent(r -> {
                            switch (r.getName()) {
                                case "ROLE_BUSINESS":
                                    res.setContent("Business Owner");
                                    res.setTitle(s.getBusinessProfile().getBusinessName());
                                    break;
                                case "ROLE_COMMUNITY_MEMBER":
                                    res.setContent("Resident");
                                    res.setTitle(s.getResidentProfile().getFullName());
                                    break;
                                default:
                                    break;
                            }
                        });

                        return res;
                    })
                    .forEach(results::add);
        }

        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<CommunityResp> getCommunityOfUser(String userId) {
        final Users user = userRepository
                .findById(userId)
                .orElseThrow(() -> new
                                InvalidUserException(
                                userId,
                                Consts.USER_NOT_FOUND
                        )
                );

        return ResponseEntity.ok(new CommunityResp(
                user.getCommunity().getId(),
                user.getCommunity().getName()
        ));
    }

    @Override
    public FileSystemResource getFileByRequestId(String requestId) {
        try {
            VerificationRequest request = verificationRequestRepository.findById(requestId)
                    .orElseThrow(() -> new InvalidEntityBaseException(requestId, "Verification Request Not Found", "VerificationRequest"));

            String fileName = request.getDocumentName();
            String subDir = "business"; // Or retrieve this from the request if it varies

            return fileManagerService.getFileWithMediaType(fileName, subDir);
        } catch (IOException e) {
            throw new InvalidEntityBaseException("file", requestId, "Error retrieving file: " + e.getMessage());
        }
    }
}
