package com.tawasalna.pms.buisnesslogic;

import com.tawasalna.pms.models.Property;
import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;
import com.tawasalna.pms.payload.request.PropertyDTO;
import com.tawasalna.pms.payload.request.PropertyInfoDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface IPropertyManagementService {
    ResponseEntity<ApiResponse> addProperty(PropertyDTO propertyDTO, List<MultipartFile> images) throws ExecutionException, InterruptedException;

    ResponseEntity<List<Property>> getAllProperties();

    ResponseEntity<List<PropertyInfoDTO>> getAllArchivedPropertiesByAgent(String id);

    ResponseEntity<List<PropertyInfoDTO>> getAllUnarchivedPropertiesByAgent(String id);

    ResponseEntity<List<PropertyInfoDTO>> getAllPropertiesInfoByAgent(String id);

    Page<Property> findPropertiesWithPaginationAndSorting(int offset, int pagesize, String field);

    ResponseEntity<ApiResponse> updateProperty(PropertyDTO propertyDTO, String id);

    ResponseEntity<Property> getPropertyById(String id);

    CompletableFuture<ResponseEntity<FileSystemResource>> getimages(String id) throws IOException;

    ResponseEntity<ApiResponse> archivePropertie(String id);

    ResponseEntity<ApiResponse> unarchivePropertie(String id);

    void delete(String id);
    List<Property> findPropertiesByFilters(String communityId, PropertyStatus status, int bedrooms, int bathrooms, Long minPrice, Long maxPrice, PropertyType propertyType, String sortBy);
}
