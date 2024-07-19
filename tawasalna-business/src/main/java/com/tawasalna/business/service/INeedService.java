package com.tawasalna.business.service;

import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Need;
import com.tawasalna.business.payload.request.CreateNeedRequest;
import com.tawasalna.business.payload.request.NeedUpdateDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INeedService {
    Need addNeed(CreateNeedRequest needDTO);

    List<Need> getNeedsByBusinessId(String businessId);

    ResponseEntity<Page<Need>> getNeedsPaged(String businessId, Integer pageNumber);

    ResponseEntity<List<Need>> getActiveNeedByClientIdSearching(String clientId);

    ResponseEntity<List<Need>> getArchivedNeedByClientIdSearching(String clientId);

    ResponseEntity<List<Need>> getNeedsSearching();

    ResponseEntity<Need> getNeedById(String needId);

    ResponseEntity<ApiResponse> updateNeed(NeedUpdateDTO updateDto, String needId);

    ResponseEntity<ApiResponse> archive(String id);

    ResponseEntity<ApiResponse> unArchive(String id);

    ResponseEntity<List<BusinessService>> getMatchingServices(String needId, String businessId);
}
