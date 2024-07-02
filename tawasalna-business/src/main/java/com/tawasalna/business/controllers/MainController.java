package com.tawasalna.business.controllers;

import com.tawasalna.business.payload.request.SearchType;
import com.tawasalna.business.payload.response.CommunityResp;
import com.tawasalna.business.payload.response.SearchRes;
import com.tawasalna.business.service.IMainService;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/main")
@AllArgsConstructor
@CrossOrigin("*")
public class MainController {

    private final IMainService mainService;

    @GetMapping("/ping")
    public ResponseEntity<Boolean> ping() {
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/send-verif-req/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse>
    sendVerifReq(@RequestPart(value = "document") MultipartFile document,
                 @PathVariable("id") String id,
                 @RequestParam("communityId") String communityId
    ) {
        return this.mainService.requestBusinessVerification(id, communityId, document);
    }

    @GetMapping("/{requestId}/file")
    public ResponseEntity<FileSystemResource> getFile(@PathVariable String requestId) {
        FileSystemResource file = mainService.getFileByRequestId(requestId);

        // Determine the file extension based on the file name or request
        String extension = determineFileExtension(file);

        // Set the appropriate content type based on the file extension
        MediaType mediaType = determineMediaType(extension);

        // Build response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("filename", file.getFilename());

        // Return the file with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(file);
    }

    private String determineFileExtension(FileSystemResource file) {
        // Extract the file name from the path
        String fileName = file.getFilename();

        // Extract the file extension from the file name
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private MediaType determineMediaType(String extension) {
        // Define MIME types for common file extensions
        switch (extension.toLowerCase()) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            default:
                // Set a generic content type for other file types
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @GetMapping("/get-shops/{communityId}")
    public ResponseEntity<Page<Users>> getShops(
            @PathVariable("communityId") String communityId,
            @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        return this.mainService.getShopsForCommunity(communityId, page);
    }

    @GetMapping("/communities")
    public ResponseEntity<List<CommunityResp>> fetchCommunities() {
        return this.mainService.fetchCommunities();
    }

    @GetMapping("/communityOfUser/{userId}")
    public ResponseEntity<CommunityResp> getCommunityOfUser(@PathVariable("userId") String userId) {
        return this.mainService.getCommunityOfUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchRes>> search(
            @RequestParam("query") String query,
            @RequestParam("type") SearchType searchType
    ) {
        return this.mainService.search(query, searchType);
    }
}
