package com.tawasalna.pms.buisnesslogic;

import com.tawasalna.pms.exceptions.InvalidPropertyException;
import com.tawasalna.pms.models.Property;
import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;
import com.tawasalna.pms.payload.request.PropertyDTO;
import com.tawasalna.pms.payload.request.PropertyInfoDTO;
import com.tawasalna.pms.repos.CommunityRepository;
import com.tawasalna.pms.repos.PropertyRepository;
import com.tawasalna.pms.repos.UserRepository;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class PropertyManagementServiceImpl implements IPropertyManagementService {

    private final PropertyRepository propertyRepository;
    private final IFileManagerService fileManager;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<ApiResponse> addProperty(PropertyDTO propertyDTO, List<MultipartFile> images) throws ExecutionException, InterruptedException {

        final Community community = communityRepository
                .findById(propertyDTO.getCommunityId())
                .orElseThrow(() -> new EntityNotFoundException("community", propertyDTO.getCommunityId(), "not found"));

        final Users agent = userRepository
                .findById(propertyDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("agent", propertyDTO.getAgentId(), "not found"));

       /* final Users tenant = userRepository
                .findById(propertyDTO.getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("tenant", propertyDTO.getTenantId(), "not found"));

        final Users owner = userRepository
                .findById(propertyDTO.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("owner", propertyDTO.getOwnerId(), "not found"));
*/

        Property p = new Property();
        p.setRef(propertyDTO.getRef());
        p.setPrice(propertyDTO.getPrice());
        p.setPricePostfix(propertyDTO.getPricePostfix());
        p.setOldPrice(propertyDTO.getOldPrice());
        p.setAreaSize(propertyDTO.getAreaSize());
        p.setAreaSizePostfix(propertyDTO.getAreaSizePostfix());
        p.setLotSize(propertyDTO.getLotSize());
        p.setBedrooms(propertyDTO.getBedrooms());
        p.setBathrooms(propertyDTO.getBathrooms());
        p.setGarageSize(propertyDTO.getGarageSize());
        p.setGarageSizePostfix(propertyDTO.getGarageSizePostfix());
        p.setYearOfBuild(propertyDTO.getYearOfBuild());
        p.setPropertyAddress(propertyDTO.getPropertyAddress());
        p.setBuildingName(propertyDTO.getBuildingName());
        p.setFloorname(propertyDTO.getFloorname());
        p.setFloorDescription(propertyDTO.getFloorDescription());
        p.setFloorPrice(propertyDTO.getFloorPrice());
        p.setFloorPricePostfix(propertyDTO.getFloorPricePostfix());
        p.setOwnerName(propertyDTO.getOwnerName());
        p.setOwnerAddress(propertyDTO.getOwnerAddress());
        p.setOwnerContact(propertyDTO.getOwnerContact());
        p.setCommunity(community);
        p.setAgent(agent);
        p.setDescription(propertyDTO.getDescription());
        p.setPropertyType(propertyDTO.getPropertyType());
        p.setArchived(false);
        p.setPropertyAvailable(true);
        //p.setOwner(owner);
        //p.setTenant(tenant);
        p.setStatus(PropertyStatus.valueOf(String.valueOf(propertyDTO.getStatus())));
        CompletableFuture<List<String>> propertyImages = fileManager.uploadManyToLocalFileSystem(images, propertyDTO.getRef());

        List<String> imageUrls = propertyImages.get();

        // Set the resolved image URLs
        p.setMyimages(String.join(",", imageUrls)); // Assuming you want a comma-separated string of URLs
        try {
            propertyRepository.save(p);
            return ResponseEntity.ok(new ApiResponse("Property successfully added", null, 200));
        } catch (org.springframework.dao.DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Duplicate key found: " + e.getMessage(), null, 409));
        }
    }

    @Override
    public void delete(String id) {
        Property property = propertyRepository
                .findById(id).orElseThrow(() -> new InvalidPropertyException(id, Consts.PROPERTY_NOT_FOUND));
        propertyRepository.delete(property);
    }

    @Override
    public List<Property> findPropertiesByFilters(String communityId, PropertyStatus status, int bedrooms, int bathrooms, Long minPrice, Long maxPrice, PropertyType propertyType, String sortBy) {
       return propertyRepository.findPropertiesByFilters(communityId, status, bedrooms, bathrooms, minPrice, maxPrice, propertyType, sortBy);
    }

    @Override
    public ResponseEntity<List<Property>> getAllProperties() {
        return new ResponseEntity<>(propertyRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PropertyInfoDTO>> getAllArchivedPropertiesByAgent(String id) {
        List<Property> properties = propertyRepository.findAllByAgent_IdAndArchivedTrue(id);

        List<PropertyInfoDTO> propertiesInfo = new ArrayList<>();

        for (Property property : properties) {
            PropertyInfoDTO propertyInfoDTO = new PropertyInfoDTO();
            propertyInfoDTO.setId(property.getId());
            propertyInfoDTO.setPrice(property.getPrice());
            propertyInfoDTO.setPricePostfix(property.getPricePostfix());
            propertyInfoDTO.setBuildingName(property.getBuildingName());
            propertyInfoDTO.setCommunity(property.getCommunity());
            propertyInfoDTO.setStatus(property.getStatus());
            propertyInfoDTO.setOwnerContact(property.getOwnerContact());
            propertyInfoDTO.setOwnerName(property.getOwnerName());
            propertiesInfo.add(propertyInfoDTO);
        }
        return new ResponseEntity<>(propertiesInfo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PropertyInfoDTO>> getAllUnarchivedPropertiesByAgent(String id) {
        List<Property> properties = propertyRepository.findAllByAgent_IdAndArchivedFalse(id);

        List<PropertyInfoDTO> propertiesInfo = new ArrayList<>();

        for (Property property : properties) {
            PropertyInfoDTO propertyInfoDTO = new PropertyInfoDTO();
            propertyInfoDTO.setId(property.getId());
            propertyInfoDTO.setPrice(property.getPrice());
            propertyInfoDTO.setPricePostfix(property.getPricePostfix());
            propertyInfoDTO.setBuildingName(property.getBuildingName());
            propertyInfoDTO.setCommunity(property.getCommunity());
            propertyInfoDTO.setStatus(property.getStatus());
            propertyInfoDTO.setOwnerContact(property.getOwnerContact());
            propertyInfoDTO.setOwnerName(property.getOwnerName());
            propertiesInfo.add(propertyInfoDTO);
        }
        return new ResponseEntity<>(propertiesInfo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PropertyInfoDTO>> getAllPropertiesInfoByAgent(String id) {
        List<Property> properties = propertyRepository.findAllByAgent_Id(id);
        List<PropertyInfoDTO> propertiesInfo = new ArrayList<>();

        for (Property property : properties) {
            PropertyInfoDTO propertyInfoDTO = new PropertyInfoDTO();
            propertyInfoDTO.setId(property.getId());
            propertyInfoDTO.setPrice(property.getPrice());
            propertyInfoDTO.setPricePostfix(property.getPricePostfix());
            propertyInfoDTO.setBuildingName(property.getBuildingName());
            propertyInfoDTO.setCommunity(property.getCommunity());
            propertyInfoDTO.setStatus(property.getStatus());
            propertyInfoDTO.setOwnerContact(property.getOwnerContact());
            propertyInfoDTO.setOwnerName(property.getOwnerName());
            propertiesInfo.add(propertyInfoDTO);
        }
        return new ResponseEntity<>(propertiesInfo, HttpStatus.OK);
    }

    @Override
    public Page<Property> findPropertiesWithPaginationAndSorting(int offset, int pagesize, String field) {
        return propertyRepository.findAll(PageRequest.of(offset, pagesize).withSort(Sort.by(field)));
    }

    @Override
    public ResponseEntity<ApiResponse> updateProperty(PropertyDTO propertyDTO, String id) {
        propertyRepository.findById(id)
                .map(p -> {
                    p.setPrice(propertyDTO.getPrice());
                    p.setPricePostfix(propertyDTO.getPricePostfix());
                    p.setOldPrice(propertyDTO.getOldPrice());
                    p.setAreaSize(propertyDTO.getAreaSize());
                    p.setAreaSizePostfix(propertyDTO.getAreaSizePostfix());
                    p.setLotSize(propertyDTO.getLotSize());
                    p.setBedrooms(propertyDTO.getBedrooms());
                    p.setBathrooms(propertyDTO.getBathrooms());
                    p.setGarageSize(propertyDTO.getGarageSize());
                    p.setGarageSizePostfix(propertyDTO.getGarageSizePostfix());
                    //p.setYearOfBuild(propertyDTO.getYearOfBuild());
                    p.setPropertyAddress(propertyDTO.getPropertyAddress());
                    //p.setMyimages(propertyDTO.getMyimages());
                    p.setFloorname(propertyDTO.getFloorname());
                    p.setFloorDescription(propertyDTO.getFloorDescription());
                    p.setFloorPrice(propertyDTO.getFloorPrice());
                    p.setFloorPricePostfix(propertyDTO.getFloorPricePostfix());
                    p.setOwnerName(propertyDTO.getOwnerName());
                    p.setOwnerAddress(propertyDTO.getOwnerAddress());
                    p.setOwnerContact(propertyDTO.getOwnerContact());
                    p.setPropertyType(propertyDTO.getPropertyType());
                    p.setStatus(propertyDTO.getStatus());
                    return propertyRepository.save(p);
                }).orElseThrow(() -> new InvalidPropertyException(id, "property not found"));
        return ResponseEntity.ok(new ApiResponse("Property updated successfully!", null, 200));
    }

    @Override
    public ResponseEntity<Property> getPropertyById(String id) {
        return ResponseEntity.ok(
                propertyRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidPropertyException(
                                        id,
                                        "Property not found"
                                )
                        )
        );
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<FileSystemResource>> getimages(String id)
            throws IOException {

        final Property property = propertyRepository
                .findById(id)
                .orElseThrow(() -> new InvalidPropertyException(
                                id,
                                Consts.PROPERTY_NOT_FOUND
                        )
                );

        return getPhotoByName(property.getMyimages(), id);
    }

    @Override
    public ResponseEntity<ApiResponse> archivePropertie(String id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new InvalidPropertyException(id, "property not found"));
        property.setArchived(true);
        property.setPropertyAvailable(false);
        propertyRepository.save(property);
        return ResponseEntity.ok(new ApiResponse("property successfully archived", null, 200));
    }

    @Override
    public ResponseEntity<ApiResponse> unarchivePropertie(String id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new InvalidPropertyException(id, "property not found"));
        property.setArchived(false);
        property.setPropertyAvailable(true);
        propertyRepository.save(property);
        return ResponseEntity.ok(new ApiResponse("property successfully unarchived", null, 200));
    }

    @NotNull
    protected CompletableFuture<ResponseEntity<FileSystemResource>>
    getPhotoByName(String images, String id)
            throws IOException {

        if (images == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        List<String> imageUrls = Arrays.asList(images.split(","));
        Property property = propertyRepository.findById(id).orElseThrow(() -> new InvalidPropertyException(id, "Property introuvable"));

        final FileSystemResource fileSystemResource = fileManager
                .getFileWithMediaType(imageUrls.toString(), "PropertyImages\\" + property.getRef());

        if (fileSystemResource == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        final String mediaType = Files
                .probeContentType(
                        fileSystemResource.getFile()
                                .toPath()
                );

        final ResponseEntity<FileSystemResource> resp = ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .body(fileSystemResource);

        return CompletableFuture.completedFuture(resp);
    }
}
