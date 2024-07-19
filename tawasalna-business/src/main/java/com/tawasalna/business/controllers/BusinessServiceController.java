package com.tawasalna.business.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasalna.business.models.Availability;
import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.payload.request.CreateServiceRequest;
import com.tawasalna.business.payload.request.FeatureRequest;
import com.tawasalna.business.payload.request.ServiceUpdateDTO;
import com.tawasalna.business.payload.response.CreateServiceResp;
import com.tawasalna.business.service.IBusinessService;
import com.tawasalna.business.service.IServiceFeature;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
@CrossOrigin("*")
public class BusinessServiceController {

    private final IBusinessService service;
    private final IServiceFeature iServiceFeature;

    @GetMapping("/findAll")
    public ResponseEntity<List<BusinessService>> getAllServicesNotArchived() {
        return new ResponseEntity<>(service.getAllNotArchived(), HttpStatus.OK);
    }

    @GetMapping("/findAll/archived")
    public ResponseEntity<List<BusinessService>> getAllServicesArchived() {
        return new ResponseEntity<>(service.getAllArchived(), HttpStatus.OK);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<BusinessService> getServiceById(
            @PathVariable String serviceId) {

        return service.getServiceById(serviceId);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BusinessService>> searchServices(
            @RequestParam(required = true) String keyword,

            @RequestParam(defaultValue = "0") int page) {

        Page<BusinessService> result = service.searchServices(keyword, page);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findAll/{ownerId}")
    public ResponseEntity<List<BusinessService>> getServiceByOwnerId(
            @PathVariable String ownerId) {

        return service.getServiceByOwnerId(ownerId);
    }

    @GetMapping("/findAll/archived/{ownerId}")
    public ResponseEntity<List<BusinessService>> getServiceByOwnerIdArchived(
            @PathVariable String ownerId) {

        return service.getServiceByOwnerIdArchived(ownerId);
    }

    @GetMapping("/findAll/paginated")
    public ResponseEntity<Page<BusinessService>> getServicesActivePaged(
            @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return service.getServicesActivePaginated(page);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BusinessService createService(@RequestPart("request") String requestJson,
            @RequestPart("file") MultipartFile file) throws JsonProcessingException {

        CreateServiceRequest request = new ObjectMapper().readValue(requestJson, CreateServiceRequest.class);
        return service.createService(request, file);
    }

    @PostMapping("/create-mobile")
    public ResponseEntity<CreateServiceResp> addService(@RequestBody CreateServiceRequest body) {
        return service.addService(body);
    }

    @PatchMapping(path = "/update-photo/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateServicePhoto(@PathVariable("id") String id,
            @RequestPart("cover") MultipartFile cover) {
        return this.service.updateServicePhoto(id, cover);
    }

    @PatchMapping("/archive/{id}")
    public ResponseEntity<ApiResponse> archive(@PathVariable("id") String id) {
        return this.service.archive(id);
    }

    @PatchMapping("/unArchive/{id}")
    public ResponseEntity<ApiResponse> unArchive(@PathVariable("id") String id) {
        return this.service.unArchive(id);
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<FileSystemResource> getPhoto(@PathVariable("id") String id)
            throws IOException, ExecutionException, InterruptedException {
        return service.getPhoto(id).get();
    }

    //// Still in progress don't use below
    @PutMapping("/updateData/{serviceId}")
    public ResponseEntity<ApiResponse> updateBusinessServiceNameAndDescription(

            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "deliveryInHours") int deliveryInHours,
            @RequestParam(value = "categoryId") String categoryId,
            @PathVariable("serviceId") String serviceId) {
        return service.updateServiceData(serviceId, name, description, deliveryInHours, categoryId);
    }

    @PutMapping("/update-data-mobile/{serviceId}")
    public ResponseEntity<ApiResponse> updateServiceMobile(
            @RequestBody ServiceUpdateDTO dto,
            @PathVariable("serviceId") String serviceId) {
        return service.updateServiceData(serviceId, dto.getTitle(), dto.getDescription(), dto.getDeliveryTimeInHours(),
                dto.getCategoryId());
    }

    @PutMapping("/availabilityUpdate/{serviceId}")
    public ResponseEntity<ApiResponse> updateServiceAvailability(
            @PathVariable String serviceId,
            @RequestBody List<Availability> updatedAvailabilities) {

        return service.updateServiceAvailability(serviceId, updatedAvailabilities);
    }

    @GetMapping("/get-by-owner/{ownerId}")
    public ResponseEntity<Page<BusinessService>> getServicesOfOwnerPaginated(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @PathVariable("ownerId") String ownerId) {
        return service.getServicesOfOwnerPaginated(pageNumber, ownerId);
    }

    @GetMapping("/get-by-owner/archived/{ownerId}")
    public ResponseEntity<Page<BusinessService>> getArchivedServicesOfOwnerPaginated(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @PathVariable("ownerId") String ownerId) {
        return service.getArchivedServicesOfOwnerPaginated(pageNumber, ownerId);
    }

    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<Page<BusinessService>> getServicesOfCategoryPaginated(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @PathVariable("categoryId") String categoryId) {
        return service.getServicesOfCategoryPaginated(pageNumber, categoryId);
    }

    @GetMapping("/filter")
    public Page<BusinessService> filterServices(
            @RequestParam(required = false, defaultValue = "") String searchString,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) List<String> daysOfWeek,
            @RequestParam(required = false) String serviceCategoryId,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Boolean availableNow,
            @RequestParam(required = false) Boolean isArchived,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        return service.filterServices(searchString, minPrice, maxPrice, daysOfWeek, serviceCategoryId, minRating,
                availableNow, isArchived, page);
    }

    @PutMapping("/disable")
    public ResponseEntity<ApiResponse> disableFeature(@RequestParam("featureId") String featureId,
            @RequestParam("serviceId") String serviceId) {
        return iServiceFeature.disableFeature(featureId, serviceId);
    }

    @PostMapping("/{serviceId}/add")
    public ResponseEntity<ApiResponse> addFeatureToService(@PathVariable("serviceId") String serviceId,
            @RequestBody FeatureRequest featureRequest) {
        return iServiceFeature.addFeatureToService(serviceId, featureRequest);
    }

    @PutMapping("/{featureId}/enable")
    public ResponseEntity<ApiResponse> enableFeature(@PathVariable String featureId, @RequestParam String serviceId) {
        return iServiceFeature.enableFeature(featureId, serviceId);
    }

}
