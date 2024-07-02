package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.usermanagement.IUserManagementService;
import com.tawasalna.auth.models.Users;
import com.tawasalna.shared.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "User Controller", description = "Controller for user management")
public class UserController {
    private final IUserManagementService userManagementService;

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<Users> retrieveUserByEmail(
            @PathVariable("email") String email) {
        return userManagementService.getUserByEmail(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> retrieveUserById(@PathVariable("id") String id) {
        return userManagementService.getUserById(id);
    }
    @GetMapping("/community/{id}")
    public ResponseEntity<List<Users>> retrieveUserByCommunityId(@PathVariable("id") String id) {
        return userManagementService.getUsersByCommunity(id);
    }
    @GetMapping("/community/paginated/{id}")
    public ResponseEntity<Page<Users>> retrieveUserByCommunityIdPage(@PathVariable("id") String id,@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return userManagementService.getUsersByCommunityPage(page,id);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<Users>> findAll() {
        return userManagementService.getAll();
    }

    @PatchMapping("/banUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse> blockUsers(@PathVariable String id) {
        return userManagementService.banUser(id);
    }

    @PatchMapping("/disableUser/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse> disableUser(@PathVariable String id) {
        return userManagementService.disableUser(id);
    }

    @PatchMapping("/unbanUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse> unbanUser(@PathVariable String id) {
        return userManagementService.enableUser(id);
    }

    @PatchMapping("/enableUser/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse> enableUser(@PathVariable String id) {
        return userManagementService.enableUser(id);
    }

    @PutMapping("/setRCA/{userId}")
    public ResponseEntity<ApiResponse> addRoleCommunityAdmin(
            @PathVariable("userId") String userId
    ) {
        return userManagementService.addCommunityAdminRole(userId);
    }
    @PutMapping("/removeRCA/{userId}")
    public ResponseEntity<ApiResponse> removeRoleCommunityAdmin(
            @PathVariable("userId") String userId
    ) {
        return userManagementService.removeRoleCommunityAdmin(userId);
    }
    @PutMapping("/setC/{userId}/{communityId}")
    public ResponseEntity<ApiResponse> addToCommunity(

            @PathVariable("userId") String userId,
             @PathVariable("communityId") String communityId
    ) {
        return userManagementService.addToCommunity(communityId, userId);
    }
    @PutMapping("/removeC/{userId}/{communityId}")
    public ResponseEntity<ApiResponse> removeFromCommunity(
            @PathVariable("communityId") String communityId,
            @PathVariable("userId") String userId
    ) {
        return userManagementService.RemoveFromCommunity(communityId, userId);
    }

   /* @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println(" image here **********");
        Resource file = residentProfileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }*/
}
