package com.tawasalna.business.service;

import com.tawasalna.business.payload.response.SearchRes;
import com.tawasalna.business.payload.request.SearchType;
import com.tawasalna.business.payload.response.CommunityResp;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMainService {

    ResponseEntity<ApiResponse> requestBusinessVerification(String userId, String communityId, MultipartFile document);

    ResponseEntity<Page<Users>> getShopsForCommunity(String communityId, Integer pageNumber);

    ResponseEntity<List<CommunityResp>> fetchCommunities();

    ResponseEntity<List<SearchRes>> search(String query, SearchType searchType);

    ResponseEntity<CommunityResp> getCommunityOfUser(String userId);

    FileSystemResource getFileByRequestId(String requestId);
}
