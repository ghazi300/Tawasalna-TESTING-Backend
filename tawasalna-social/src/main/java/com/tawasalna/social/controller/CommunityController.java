package com.tawasalna.social.controller;

import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.payload.request.CommunityDTO;
import com.tawasalna.social.service.ICommunityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/community")
@AllArgsConstructor
@CrossOrigin("*")
public class CommunityController {

    private final ICommunityService communityService;

    @PutMapping("/{communityId}/userAdd/{userId}")
    public ResponseEntity<ApiResponse> addUserToCommunity(
            @PathVariable String communityId,
            @PathVariable String userId) {

        return communityService.addUserToCommunity(communityId, userId);
    }

    @PutMapping("/{communityId}/userRemove/{userId}")
    public ResponseEntity<ApiResponse> removeUserFromCommunity(
            @PathVariable String communityId,
            @PathVariable String userId) {

        return communityService.removeUserFromCommunity(communityId, userId);
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<Community> getCommunityById(
            @PathVariable String communityId) {

        return communityService.getCommunityById(communityId);
    }

    @GetMapping("/getAdmins/{communityId}")
    public ResponseEntity<Set<Users>> getCommunityAdminsbyId(
            @PathVariable String communityId) {

        return communityService.getCommunityAdminsbyid(communityId);
    }

    @GetMapping("/getCommunities/{userId}")
    public ResponseEntity<List<Community>> getCommunitiesByAdmin(
            @PathVariable String userId) {

        return communityService.getCommunitiesByAdmin(userId);
    }

    @PutMapping("/{communityId}/addAdmin/{userId}")
    public ResponseEntity<ApiResponse> addAdminToCommunity(
            @PathVariable String communityId,
            @PathVariable String userId) {

        return communityService.addAdminToCommunity(communityId, userId);
    }

    @PutMapping("/{communityId}/removeAdmin/{userId}")
    public ResponseEntity<ApiResponse> removeAdminFromCommunity(
            @PathVariable String communityId,
            @PathVariable String userId) {

        return communityService.removeAdminFromCommunity(communityId, userId);
    }

    @PostMapping("/")
    public Community createCommunity(@RequestBody CommunityDTO request) {
        Community newCommunity = new Community();
        newCommunity.setName(request.getName());
        newCommunity.setDescription(request.getDescription());
        return communityService.createCommunity(newCommunity);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Community>> getAllCommunity() {
        return new ResponseEntity<>(communityService.getAll(), HttpStatus.OK);
    }
}
