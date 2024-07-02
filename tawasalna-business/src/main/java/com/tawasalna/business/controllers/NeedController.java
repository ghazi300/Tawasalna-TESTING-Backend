package com.tawasalna.business.controllers;


import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Need;
import com.tawasalna.business.payload.request.CreateNeedRequest;
import com.tawasalna.business.payload.request.NeedUpdateDTO;
import com.tawasalna.business.service.INeedService;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/need")
@AllArgsConstructor
@CrossOrigin("*")
public class NeedController {

    private final INeedService needService;

    @PostMapping("/add")
    public ResponseEntity<Need> addNeed(@RequestBody CreateNeedRequest needDTO) {
        return new ResponseEntity<>(
                needService.addNeed(needDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/needs/business/{businessId}")
    public List<Need> getNeedsByBusinessId(@PathVariable("businessId") String businessId) {
        return needService.getNeedsByBusinessId(businessId);
    }

    @GetMapping("/paged/{businessId}")
    public ResponseEntity<Page<Need>> getNeedsPaged(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @PathVariable("businessId") String businessId
    ) {
        return this.needService.getNeedsPaged(businessId, pageNumber);
    }

    @GetMapping("/client/searching/{clientId}")
    public ResponseEntity<List<Need>> getNeedByClientIdSearching(
            @PathVariable("clientId") String clientId) {
        return needService.getActiveNeedByClientIdSearching(clientId);
    }

    @GetMapping("/client/archived/{clientId}")
    public ResponseEntity<List<Need>> getArchivedNeedByClientIdSearching(
            @PathVariable("clientId") String clientId) {
        return needService.getArchivedNeedByClientIdSearching(clientId);
    }

    @GetMapping("/{needId}")
    public ResponseEntity<Need> getNeedById(
            @PathVariable("needId") String needId) {
        return needService.getNeedById(needId);
    }

    @GetMapping("/searching")
    public ResponseEntity<List<Need>> getSearchingNeeds() {
        return needService.getNeedsSearching();
    }

    @PutMapping("/update/{needId}")
    public ResponseEntity<ApiResponse> updateQuoteDetails(
            @RequestBody NeedUpdateDTO needDto,
            @PathVariable("needId") String needId
    ) {
        return needService.updateNeed(needDto, needId);
    }

    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse> archive(@PathVariable("id") String id) {
        return needService.archive(id);
    }

    @PatchMapping("/unArchive/{id}")
    public ResponseEntity<ApiResponse> unArchive(@PathVariable("id") String id) {
        return needService.unArchive(id);
    }

    @GetMapping("/get-matching-services/{needId}/{businessId}")
    public ResponseEntity<List<BusinessService>> getMatchingServices(
            @PathVariable("needId") String needId,
            @PathVariable("businessId") String businessId
    ) {
        return this.needService.getMatchingServices(needId, businessId);
    }
}
