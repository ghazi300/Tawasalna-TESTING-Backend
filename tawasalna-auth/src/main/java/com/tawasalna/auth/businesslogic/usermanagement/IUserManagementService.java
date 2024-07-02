package com.tawasalna.auth.businesslogic.usermanagement;

import com.tawasalna.auth.models.Users;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.auth.payload.request.UserDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserManagementService {

    ResponseEntity<Users> getUserById(String id);

    ResponseEntity<ApiResponse> enableUser(String id);

    ResponseEntity<Users> getUserByEmail(String email);

    ResponseEntity<ApiResponse> banUser(String id);

    ResponseEntity<ApiResponse> disableUser(String id);

    ResponseEntity<List<Users>> getAll();

    List<Users> getAgents();

    List<Users> getBrokers();

    List<Users>findBrokersWithAcceptedSignupRequest();
    List<Users>findAgentsWithAcceptedSignupRequest();

    List<Users> getCommunityAdmins();

    List<Users> getPMSAdmins();
  
    ResponseEntity<List<Users>>  getUsersByCommunity(String communityId);
  
    List<Users> getProspects();

    List<Users> getPmsAdminsPerCommunity(Community community);

    ResponseEntity<Page<Users>> getUsersByCommunityPage(int pageNumber, String communityId);

    ResponseEntity<ApiResponse> addCommunityAdminRole(String userId);

    ResponseEntity<ApiResponse> removeRoleCommunityAdmin(String userId);

    ResponseEntity<ApiResponse> addToCommunity(String userid, String communityId);

    ResponseEntity<ApiResponse> RemoveFromCommunity(String userid, String communityId);

}
