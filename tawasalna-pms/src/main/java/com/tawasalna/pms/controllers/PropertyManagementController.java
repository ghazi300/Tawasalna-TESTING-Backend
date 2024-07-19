package com.tawasalna.pms.controllers;

import com.tawasalna.pms.buisnesslogic.IPropertyManagementService;
import com.tawasalna.pms.exceptions.InvalidPropertyException;
import com.tawasalna.pms.models.Property;
import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;
import com.tawasalna.pms.payload.request.PropertyDTO;
import com.tawasalna.pms.payload.request.PropertyInfoDTO;
import com.tawasalna.pms.payload.response.PropertyImageResponse;
import com.tawasalna.pms.repos.PropertyRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/propertyManagement")
@RequiredArgsConstructor
@Slf4j
public class PropertyManagementController {
    private final IPropertyManagementService propertyService;
    private final PropertyRepository propertyRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> addProperty(@ModelAttribute PropertyDTO propertydto,
                                                   @RequestPart("images") List<MultipartFile> images) throws ExecutionException, InterruptedException {
        return propertyService.addProperty(propertydto, images);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProperty(@ModelAttribute PropertyDTO propertydto,
                                                      @PathVariable("id") String id
    ) {
        return propertyService.updateProperty(propertydto, id);
    }

    @PutMapping("/archive/{id}")
    public ResponseEntity<ApiResponse> archiveProperty(@PathVariable("id") String id) {
        return propertyService.archivePropertie(id);
    }

    @PutMapping("/unarchive/{id}")
    public ResponseEntity<ApiResponse> unarchiveProperty(@PathVariable("id") String id) {
        return propertyService.unarchivePropertie(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProperty(@PathVariable("id") String id) {
        propertyService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable("id") String id) {
        return propertyService.getPropertyById(id);
    }

    @GetMapping("/allbyAgent/{id}")
    public ResponseEntity<List<PropertyInfoDTO>> getAllPropertiesInfoByAgent(@PathVariable("id") String id) {
        return propertyService.getAllPropertiesInfoByAgent(id);
    }

    @GetMapping("/properties/{propertyId}/images")
    public ResponseEntity<?> getPropertyImages(@PathVariable("propertyId") String propertyId) {
        Property property = propertyRepository
                .findById(propertyId)
                .orElseThrow(() -> new InvalidPropertyException(
                                propertyId,
                                "Property not found"
                        )
                );
        if (property != null) {
            String[] imageUrls = property.getMyimages().split(",");
            return ResponseEntity.ok(imageUrls);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{propertyId}/images")
    public List<ResponseEntity<byte[]>> getPropertyImagess(@PathVariable("propertyId") String propertyId) {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        List<ResponseEntity<byte[]>> imageResponses = new ArrayList<>();

        if (optionalProperty.isPresent()) {
            Property property = optionalProperty.get();
            List<String> imageNames = Arrays.asList(property.getMyimages().split(",")); // Assuming image names are stored as comma-separated string

            for (String imageName : imageNames) {
                Path imagePath = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\PropertyImages\\" + property.getRef() + "\\" + imageName);
                try {
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type according to your image type
                    ResponseEntity<byte[]> imageResponse = new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
                    imageResponses.add(imageResponse);
                } catch (IOException e) {
                    // Handle IO exception if necessary
                    // You can choose to skip the image or return an error response for this particular image
                    // For simplicity, I'll just skip the image in this example
                    continue;
                }
            }
            return imageResponses;
        } else {
            // Property not found
            return Collections.emptyList();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Property>> allProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/properties/{id}")
    public ResponseEntity<Map<String, Object>> getAllPropertiesPage(
            @RequestParam(required = false) String buildingName,
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<PropertyInfoDTO> properties = new ArrayList<PropertyInfoDTO>();
            Pageable paging = PageRequest.of(page, size);

            Page<PropertyInfoDTO> pagePropts;
            if (buildingName == null)
                pagePropts = propertyRepository.findAllByAgent_Id(id, paging);
            else
                pagePropts = propertyRepository.findByBuildingNameAndAgent_Id(buildingName, id, paging);

            properties = pagePropts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("properties", properties);
            response.put("currentPage", pagePropts.getNumber());
            response.put("totalItems", pagePropts.getTotalElements());
            response.put("totalPages", pagePropts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/archivedByAgent/{id}")
    public ResponseEntity<Map<String, Object>> getAllArchivedPropertiesPages(
            @RequestParam(required = false) String buildingName,
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<PropertyInfoDTO> properties = new ArrayList<PropertyInfoDTO>();
            Pageable paging = PageRequest.of(page, size);

            Page<PropertyInfoDTO> pagePropts;
            if (buildingName == null)
                pagePropts = propertyRepository.findAllByAgent_IdAndArchivedTrue(id, paging);
            else
                pagePropts = propertyRepository.findAllByAgent_IdAndArchivedTrueAndBuildingName(id, buildingName, paging);

            properties = pagePropts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("archived", properties);
            response.put("currentPage", pagePropts.getNumber());
            response.put("totalItems", pagePropts.getTotalElements());
            response.put("totalPages", pagePropts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unarchivedByAgent/{id}")
    public ResponseEntity<Map<String, Object>> getAllUnArchivedPropertiesPages(
            @RequestParam(required = false) String buildingName,
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<PropertyInfoDTO> properties = new ArrayList<PropertyInfoDTO>();
            Pageable paging = PageRequest.of(page, size);

            Page<PropertyInfoDTO> pagePropts;
            if (buildingName == null)
                pagePropts = propertyRepository.findAllByAgent_IdAndArchivedFalse(id, paging);
            else
                pagePropts = propertyRepository.findAllByAgent_IdAndArchivedFalseAndBuildingName(id, buildingName, paging);

            properties = pagePropts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("unarchived", properties);
            response.put("currentPage", pagePropts.getNumber());
            response.put("totalItems", pagePropts.getTotalElements());
            response.put("totalPages", pagePropts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/properties/images")
    public List<ResponseEntity<PropertyImageResponse>> getAllPropertiesWithImages() {
        List<Property> properties = propertyRepository.findAll();
        List<ResponseEntity<PropertyImageResponse>> propertyImageResponses = new ArrayList<>();

        for (Property property : properties) {
            List<String> imageNames = Arrays.asList(property.getMyimages().split(",")); // Assuming image names are stored as comma-separated string
            List<byte[]> images = new ArrayList<>();

            for (String imageName : imageNames) {
                Path imagePath = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\PropertyImages\\" + property.getRef() + "\\" + imageName);
                try {
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    images.add(imageBytes);
                } catch (IOException e) {
                    // Handle IO exception if necessary
                    // You can choose to skip the image or return an error response for this particular image
                    // For simplicity, I'll just skip the image in this example
                    continue;
                }
            }

            // Create PropertyImageResponse containing property information and its images
            PropertyImageResponse propertyImageResponse = new PropertyImageResponse(property, images);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // Adjust content type according to your response type
            ResponseEntity<PropertyImageResponse> responseEntity = new ResponseEntity<>(propertyImageResponse, headers, HttpStatus.OK);
            propertyImageResponses.add(responseEntity);
        }

        return propertyImageResponses;
    }

    @GetMapping("/properties/images/withPagination")
    public ResponseEntity<Map<String, Object>> getAllPropertiesWithImages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Property> pageProperties = propertyRepository.findAll(paging);

            List<ResponseEntity<PropertyImageResponse>> propertyImageResponses = new ArrayList<>();

            for (Property property : pageProperties.getContent()) {
                List<String> imageNames = Arrays.asList(property.getMyimages().split(","));
                List<byte[]> images = new ArrayList<>();

                for (String imageName : imageNames) {
                    Path imagePath = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\PropertyImages\\" + property.getRef() + "\\" + imageName);
                    try {
                        byte[] imageBytes = Files.readAllBytes(imagePath);
                        images.add(imageBytes);
                    } catch (IOException e) {
                        continue;
                    }
                }

                PropertyImageResponse propertyImageResponse = new PropertyImageResponse(property, images);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                ResponseEntity<PropertyImageResponse> responseEntity = new ResponseEntity<>(propertyImageResponse, headers, HttpStatus.OK);
                propertyImageResponses.add(responseEntity);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("propertieslist", propertyImageResponses);
            response.put("currentPage", pageProperties.getNumber());
            response.put("totalItems", pageProperties.getTotalElements());
            response.put("totalPages", pageProperties.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter")
    public List<Property>  getPropertiesByFilters(
            @RequestParam(required = false) String communityId,
            @RequestParam(required = false) PropertyStatus status,
            @RequestParam(required = false, defaultValue = "0") int bedrooms,
            @RequestParam(required = false, defaultValue = "0") int bathrooms,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) String sortBy) {
        return propertyService.findPropertiesByFilters(communityId, status, bedrooms, bathrooms, minPrice, maxPrice, propertyType, sortBy);
    }

}