package com.tawasalna.social.service;

import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface ICommunityService {

    List<Users> getAllUsersInCommunity(String communityId);


    List<Community> getAll();

    ResponseEntity<ApiResponse> addAdminToCommunity(String communityId, String userId);


    ResponseEntity<Community> getCommunityById(String id);

    ResponseEntity<ApiResponse> removeAdminFromCommunity(String communityId, String userId);

    ResponseEntity<ApiResponse> updateCommunity(String communityId, String name, String description);


    ResponseEntity<ApiResponse> deleteCommunity(String communityId);

    Community createCommunity(Community community);

    ResponseEntity<ApiResponse> addUserToCommunity(String communityId, String userId);

    ResponseEntity<ApiResponse> removeUserFromCommunity(String communityId, String userId);

    ResponseEntity<Set<Users>> getCommunityAdminsbyid(String communityId);

    ResponseEntity<List<Community>> getCommunitiesByAdmin(String userId);
}
