package com.tawasalna.business.service;

import com.tawasalna.business.models.Availability;
import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Product;
import com.tawasalna.business.payload.request.CreateServiceRequest;
import com.tawasalna.business.payload.request.UpdateServiceRequest;
import com.tawasalna.business.payload.response.CreateServiceResp;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// need testing
public interface IBusinessService {


    List<BusinessService> getAllNotArchived();

    List<BusinessService> getAllArchived();

    ResponseEntity<BusinessService> getServiceById(String id);

    BusinessService createService(CreateServiceRequest community, MultipartFile file);

    ResponseEntity<List<BusinessService>> getServiceByOwnerId(String ownerId);

    ResponseEntity<Page<BusinessService>> getServicesOfOwnerPaginated(int pageNumber, String ownerId);


    Page<BusinessService> searchServices(String keyword, int page);


    Page<BusinessService> filterServices(String searchString, Double minPrice, Double maxPrice, List<String> daysOfWeek, String serviceCategoryId, Double minRating, Boolean availableNow, Boolean isArchived, int page);

    ResponseEntity<Page<BusinessService>> getServicesOfCategoryPaginated(int pageNumber, String categoryId);

    CompletableFuture<ResponseEntity<FileSystemResource>> getPhoto(String id)
            throws IOException;

    ResponseEntity<ApiResponse> updateService(CreateServiceRequest createServiceRequest, String serviceId);

    ResponseEntity<ApiResponse> updateServiceWithChanges(String serviceId, UpdateServiceRequest updateRequest);

    ResponseEntity<CreateServiceResp> addService(CreateServiceRequest body);

    ResponseEntity<ApiResponse> updateServicePhoto(String id, MultipartFile cover);

    ResponseEntity<Page<BusinessService>> getArchivedServicesOfOwnerPaginated(int pageNumber, String ownerId);

    ResponseEntity<ApiResponse> archive(String id);

    ResponseEntity<ApiResponse> unArchive(String id);

    ResponseEntity<List<BusinessService>> getServiceByOwnerIdArchived(String ownerId);

    ResponseEntity<Page<BusinessService>> getServicesActivePaginated(Integer page);


    ResponseEntity<ApiResponse> updateServiceData(String serviceId, String name, String description,int DelivryinHours,String categoryId);

    ResponseEntity<ApiResponse> updateServiceAvailability(String serviceId, List<Availability> updatedAvailabilities);
}
