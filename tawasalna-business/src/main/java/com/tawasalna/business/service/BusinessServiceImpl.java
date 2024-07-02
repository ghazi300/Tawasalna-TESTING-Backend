package com.tawasalna.business.service;

import com.tawasalna.business.exceptions.InvalidServiceException;
import com.tawasalna.business.models.*;
import com.tawasalna.business.payload.request.AvailabilityRequest;
import com.tawasalna.business.payload.request.CreateServiceRequest;
import com.tawasalna.business.payload.request.FeatureRequest;
import com.tawasalna.business.payload.request.UpdateServiceRequest;
import com.tawasalna.business.payload.response.CreateServiceResp;
import com.tawasalna.business.repository.AvailabilityRepository;
import com.tawasalna.business.repository.BusinessServiceRepository;
import com.tawasalna.business.repository.ServiceCategoryRepository;
import com.tawasalna.business.repository.ServiceFeatureRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.userapi.service.IUserConsumerService;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BusinessServiceImpl implements IBusinessService {
    private final BusinessServiceRepository businessServiceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceFeatureRepository serviceFeatureRepository;
    private final AvailabilityRepository availabilityRepository;
    private final IUserConsumerService userConsumerService;
    private final IFileManagerService fileManager;

    @Override
    public List<BusinessService> getAllNotArchived() {
        return businessServiceRepository.findAllByIsArchivedFalse();
    }

    @Override
    public List<BusinessService> getAllArchived() {
        return businessServiceRepository.findAllByIsArchivedTrue();
    }

    @Override
    public ResponseEntity<BusinessService> getServiceById(String id) {

        return ResponseEntity.ok(
                businessServiceRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidServiceException(id, "Service Not Found"

                                )));

    }

    public BusinessService createService(CreateServiceRequest request, MultipartFile file) {
        // Check for empty files

        BusinessService newService = new BusinessService();

        newService.setTitle(request.getTitle());
        newService.setDescription(request.getDescription());
        newService.setBasePrice(request.getBasePrice());
        newService.setDeliveryTimeInHours(request.getDeliveryTimeInHours());
        newService.setIsArchived(false);
        newService.setTotalReviews(0);
        newService.setAverageStars(0.0f);

        final String fileName = saveFileToDisk(file);
        newService.setServiceImage(fileName);

        // Initialize availability list
        newService.setAvailability(new ArrayList<>());

        // Get owner (user)
        final Users owner = userConsumerService
                .getUserById(request.getOwnerId())
                .orElseThrow(() -> new InvalidUserException(request.getOwnerId(), Consts.USER_NOT_FOUND));
        newService.setOwner(owner);

        final ServiceCategory category = serviceCategoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("category",
                        request.getCategoryId(),
                        "Category Not Found"));

        category.setServiceCount(category.getServiceCount() + 1);
        serviceCategoryRepository.save(category);
        newService.setServiceCategory(category);

        // Check if detailed features are provided
        if (!request.getFeatures().isEmpty()) {
            // Decompose service into features
            final List<ServiceFeature> features = new ArrayList<>();
            double totalFeaturePrice = 0.0;

            for (FeatureRequest featureRequest : request.getFeatures()) {
                ServiceFeature feature = mapFeatureRequestToEntity(featureRequest);

                // Save the feature in the database
                serviceFeatureRepository.save(feature);

                // Add the feature to the list of additional features in the new service
                features.add(feature);

                // Accumulate total feature price for calculating base price
                totalFeaturePrice += featureRequest.getPrice();
            }

            // Set additional features and calculate base price from feature prices
            newService.setAdditionalFeatures(features);
            newService.setBasePrice(totalFeaturePrice);
        } else {
            // Use provided base price directly
            List<ServiceFeature> features = new ArrayList<>();
            ServiceFeature feat = new ServiceFeature();
            feat.setDescription(newService.getDescription());
            feat.setTitle(newService.getTitle());
            feat.setPrice(request.getBasePrice());
            feat.setBase(true);
            serviceFeatureRepository.save(feat);
            features.add(feat);
            newService.setAdditionalFeatures(features);
            newService.setBasePrice(request.getBasePrice());
        }

        // Save availability for each day
        for (int dayIndex = 0; dayIndex < 7; dayIndex++) {
            AvailabilityRequest availabilityRequest = request.getAvailability().get(dayIndex);
            Availability availability = mapAvailabilityRequestToEntity(availabilityRequest);
            availability.setDayOfWeek(getDayOfWeek(dayIndex));
            availabilityRepository.save(availability);
            newService.getAvailability().add(availability);
        }

        // Save the new service
        return businessServiceRepository.save(newService);
    }

    @Override
    public ResponseEntity<List<BusinessService>> getServiceByOwnerId(String ownerId) {
        final Users user = userConsumerService
                .getUserById(ownerId)
                .orElseThrow(() -> new InvalidUserException(
                        ownerId,
                        Consts.COMMUNITY_NOT_FOUND));
        return ResponseEntity.ok(
                businessServiceRepository.findBusinessServicesByOwnerAndIsArchivedFalse(user));
    }

    @Override
    public ResponseEntity<Page<BusinessService>> getServicesOfOwnerPaginated(int pageNumber, String ownerId) {
        if (pageNumber <= 0)
            pageNumber = 1;
        final Users user = userConsumerService
                .getUserById(ownerId)
                .orElseThrow(() -> new InvalidUserException(
                        ownerId,
                        Consts.USER_NOT_FOUND));
        return ResponseEntity.ok(businessServiceRepository
                .findAllByOwnerAndIsArchivedFalse(user, PageRequest.of(pageNumber - 1, 10)));
    }

    @Override
    public Page<BusinessService> searchServices(String keyword, int page) {
        return null;
    }

    @Override
    public ResponseEntity<Page<BusinessService>> getArchivedServicesOfOwnerPaginated(int pageNumber, String ownerId) {
        if (pageNumber <= 0)
            pageNumber = 1;
        final Users user = userConsumerService
                .getUserById(ownerId)
                .orElseThrow(() -> new InvalidUserException(
                        ownerId,
                        Consts.USER_NOT_FOUND));
        return ResponseEntity.ok(businessServiceRepository
                .findAllByOwnerAndIsArchivedTrue(user, PageRequest.of(pageNumber - 1, 10)));
    }

    @Override
    public ResponseEntity<ApiResponse> archive(String id) {
        final BusinessService service = businessServiceRepository.findById(id)
                .orElseThrow(
                        () -> new InvalidServiceException(id, "Service Not Found"

                        ));
        service.setIsArchived(true);
        businessServiceRepository.save(service);
        return ResponseEntity.ok(new ApiResponse("Service archived", null, 200));
    }

    @Override
    public ResponseEntity<ApiResponse> unArchive(String id) {
        final BusinessService service = businessServiceRepository.findById(id)
                .orElseThrow(
                        () -> new InvalidServiceException(id, "Service Not Found"

                        ));
        service.setIsArchived(false);
        businessServiceRepository.save(service);
        return ResponseEntity.ok(new ApiResponse("Service removed from archive", null, 200));
    }

    @Override
    public ResponseEntity<List<BusinessService>> getServiceByOwnerIdArchived(String ownerId) {
        final Users user = userConsumerService
                .getUserById(ownerId)
                .orElseThrow(() -> new InvalidUserException(
                        ownerId,
                        Consts.COMMUNITY_NOT_FOUND));
        return ResponseEntity.ok(
                businessServiceRepository.findBusinessServicesByOwnerAndIsArchivedTrue(user));
    }

    @Override
    public ResponseEntity<Page<BusinessService>> getServicesActivePaginated(Integer page) {
        if (page <= 1)
            page = 1;

        return ResponseEntity.ok(
                businessServiceRepository.findAll(
                        PageRequest.of(page - 1, 9)));
    }
// Service Data Update
    @Override
    public ResponseEntity<ApiResponse> updateServiceData(String serviceId, String name, String description,int deliveryInHours,String categoryId) {
        BusinessService service = businessServiceRepository.findById(serviceId)
                .orElseThrow(() -> new InvalidServiceException(serviceId, "Service not found"));
        ServiceCategory newCategory = serviceCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("serviceCategory", categoryId,"Service not found"));
        // Update service properties
        service.setTitle(name);
        service.setDescription(description);
        service.setDeliveryTimeInHours(deliveryInHours);
        service.setServiceCategory(newCategory);
        BusinessService updatedService = businessServiceRepository.save(service);

        // Log
        log.info("Updated Service: {}", updatedService);

        // success response
        return ResponseEntity.ok(new ApiResponse("Service updated successfully!", null, 200));
    }


    // Service // use  this to update Availabilities of services
    @Override
    public ResponseEntity<ApiResponse> updateServiceAvailability(String serviceId, List<Availability> updatedAvailabilities) {
        BusinessService service = businessServiceRepository.findById(serviceId)
                .orElseThrow(() -> new InvalidServiceException(serviceId, "Service not found"));

        for (Availability updatedAvailability : updatedAvailabilities) {
            Optional<Availability> existingAvailabilityOpt = availabilityRepository.findById(updatedAvailability.getId());
            if (existingAvailabilityOpt.isPresent()) {
                Availability existingAvailability = existingAvailabilityOpt.get();
                existingAvailability.setDayOfWeek(updatedAvailability.getDayOfWeek());
                existingAvailability.setAvailable(updatedAvailability.isAvailable());
                existingAvailability.setStartTime(updatedAvailability.getStartTime());
                existingAvailability.setEndTime(updatedAvailability.getEndTime());
                availabilityRepository.save(existingAvailability);
            } else {
                availabilityRepository.save(updatedAvailability);
            }
        }

        // Update the reference to the availabilities in the service
        service.setAvailability(updatedAvailabilities);
        businessServiceRepository.save(service);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Availabilities updated successfully", 200));
    }




    public static LocalTime getCurrentLocalTime() {
        // Get the current time in the local time zone
        return LocalTime.now(ZoneId.systemDefault());
    }

    public Page<BusinessService> filterServices(String searchString, Double minPrice, Double maxPrice,
            List<String> daysOfWeek, String serviceCategoryId, Double minRating, Boolean availableNow,
            Boolean isArchived, int page) {
        // Define static page size

        int pageSize = 9;

        // Retrieve page of objects from the database
        List<BusinessService> allServices = businessServiceRepository.findAll();

        // Log input parameters
        System.out.println("Filtering services with parameters: searchString=" + searchString + ", minPrice=" + minPrice
                + ", maxPrice=" + maxPrice + ", daysOfWeek=" + daysOfWeek + ", serviceCategoryId=" + serviceCategoryId
                + ", minRating=" + minRating + ", availableNow=" + availableNow + ", isArchived=" + isArchived
                + ", page=" + page);

        // Apply filtering conditions based on parameters
        List<BusinessService> filteredServices = allServices.stream()
                .filter(service -> {
                    boolean match = searchString.isEmpty() ||
                            service.getTitle().toLowerCase().contains(searchString.toLowerCase()) ||
                            service.getDescription().toLowerCase().contains(searchString.toLowerCase()) ||
                            service.getAdditionalFeatures().stream().anyMatch(feature -> feature.getTitle()
                                    .toLowerCase().contains(searchString.toLowerCase()) ||
                                    feature.getDescription().toLowerCase().contains(searchString.toLowerCase()));
                    System.out.println("Service " + service.getTitle() + " matched searchString: " + match);
                    return match;
                })
                .filter(service -> {
                    boolean match = minPrice == null || service.getBasePrice() >= minPrice;
                    System.out.println("Service " + service.getTitle() + " matched minPrice: " + match);
                    return match;
                })
                .filter(service -> {
                    boolean match = maxPrice == null || service.getBasePrice() <= maxPrice;
                    System.out.println("Service " + service.getTitle() + " matched maxPrice: " + match);
                    return match;
                })
                .filter(service -> {
                    boolean match = daysOfWeek == null || daysOfWeek.isEmpty() ||
                            daysOfWeek.stream().allMatch(day -> service.getAvailability().stream()
                                    .anyMatch(av -> av.getDayOfWeek().equalsIgnoreCase(day) && av.isAvailable()));
                    System.out.println("Service " + service.getTitle() + " matched daysOfWeek: " + match);
                    return match;
                })

                .filter(service -> {
                    boolean match = serviceCategoryId == null
                            || service.getServiceCategory().getId().equals(serviceCategoryId);
                    System.out.println("Service " + service.getTitle() + " matched serviceCategoryId: " + match);
                    return match;
                })

                .filter(service -> {
                    boolean match = minRating == null || service.getAverageStars() >= minRating;
                    System.out.println("Service " + service.getTitle() + " matched minRating: " + match);
                    return match;
                })

                .filter(service -> {
                    boolean match = availableNow == null || !availableNow ||
                            service.getAvailability().stream()
                                    .filter(av -> av.getDayOfWeek().equalsIgnoreCase(getCurrentDayOfWeek())
                                            && av.isAvailable())
                                    .anyMatch(av -> {
                                        LocalTime startTime = LocalTime.parse(av.getStartTime());
                                        LocalTime endTime = LocalTime.parse(av.getEndTime());
                                        return getCurrentLocalTime().isAfter(startTime)
                                                && getCurrentLocalTime().isBefore(endTime);
                                    });
                    System.out.println("Service " + service.getTitle() + " matched availableNow: " + match);
                    return match;
                })
                .filter(service -> {
                    boolean match = isArchived == null || service.getIsArchived().equals(isArchived);
                    System.out.println("Service " + service.getTitle() + " matched isArchived: " + match);
                    return match;
                })
                .collect(Collectors.toList());

        // Log the number of filtered services
        System.out.println("Number of filtered services: " + filteredServices.size());
        int totalPages = (int) Math.ceil((double) filteredServices.size() / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, filteredServices.size());
        List<BusinessService> pageContent = filteredServices.subList(startIndex, endIndex);

        // Create a pageable object for the current page
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // Return the paginated list as a page object with correct pagination
        // information
        return new PageImpl<>(pageContent, pageable, filteredServices.size());
    }

    public List<String> getSuggestions(String keyword) {
        List<BusinessService> serviceTitles = businessServiceRepository.findTitleSuggestions(keyword);
        List<ServiceFeature> featureTitles = businessServiceRepository.findFeatureTitleSuggestions(keyword);

        // Combine and deduplicate suggestions
        Set<String> suggestions = new HashSet<>();
        for (BusinessService service : serviceTitles) {
            suggestions.add(service.getTitle());
        }
        for (ServiceFeature feature : featureTitles) {
            suggestions.add(feature.getTitle());
        }
        return new ArrayList<>(suggestions);
    }

    @Override
    public ResponseEntity<Page<BusinessService>> getServicesOfCategoryPaginated(int pageNumber, String categoryId) {
        if (pageNumber <= 0)
            pageNumber = 1;
        final ServiceCategory category = serviceCategoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new InvalidUserException(
                        categoryId,
                        "Category Not Found"));

        return ResponseEntity.ok(businessServiceRepository
                .findAllByServiceCategoryAndIsArchivedFalse(category, PageRequest.of(pageNumber - 1, 10)));
    }

    private Availability mapAvailabilityRequestToEntity(AvailabilityRequest availabilityRequest) {
        Availability availability = new Availability();
        availability.setDayOfWeek(availabilityRequest.getDayOfWeek());
        availability.setAvailable(availabilityRequest.isAvailable());
        availability.setStartTime(availabilityRequest.getStartTime());
        availability.setEndTime(availabilityRequest.getEndTime());
        return availability;
    }

    private ServiceFeature mapFeatureRequestToEntity(FeatureRequest featureRequest) {
        ServiceFeature serviceFeature = new ServiceFeature();
        serviceFeature.setTitle(featureRequest.getTitle());
        serviceFeature.setDescription(featureRequest.getDescription());
        serviceFeature.setPrice(featureRequest.getPrice());
        serviceFeature.setBase(Boolean.parseBoolean(featureRequest.getIsBase())); // Use isBase() method to set the
                                                                                  // 'base' property

        return serviceFeature;
    }

    private List<Availability> saveAvailabilities(List<AvailabilityRequest> availabilityRequests) {
        List<Availability> availabilities = new ArrayList<>();
        for (AvailabilityRequest availabilityRequest : availabilityRequests) {
            Availability availability = mapAvailabilityRequestToEntity(availabilityRequest);
            availabilities.add(availability);
        }
        return availabilityRepository.saveAll(availabilities);
    }

    private String getDayOfWeek(int dayIndex) {
        final String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        return daysOfWeek[dayIndex];
    }

    private String saveFileToDisk(MultipartFile file) {
        try {
            if (file == null || file.isEmpty())
                throw new InvalidEntityBaseException("file", "null", "invalid file");

            final String fileName = fileManager.uploadToLocalFileSystem(file, "services").get();

            if (fileName == null)
                throw new InvalidEntityBaseException("file", "null", "no file name");

            return fileName;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    @NotNull
    private CompletableFuture<ResponseEntity<FileSystemResource>> getPhotoByName(String name)
            throws IOException {

        if (name == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build());

        final FileSystemResource fileSystemResource = fileManager
                .getFileWithMediaType(name, "services");

        if (fileSystemResource == null)
            return CompletableFuture
                    .completedFuture(
                            ResponseEntity
                                    .notFound()
                                    .build());

        final String mediaType = Files
                .probeContentType(
                        fileSystemResource.getFile()
                                .toPath());

        final ResponseEntity<FileSystemResource> resp = ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .body(fileSystemResource);

        return CompletableFuture.completedFuture(resp);
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<FileSystemResource>> getPhoto(String id)
            throws IOException {

        final BusinessService service = businessServiceRepository
                .findById(id)
                .orElseThrow(() -> new InvalidServiceException(
                        id,
                        "Service Not Found"));

        String coverPhotoName = service.getServiceImage();
        return coverPhotoName == null ? getLocalPlaceholder() : getPhotoByName(coverPhotoName);
    }

    @Override
    public ResponseEntity<ApiResponse> updateService(CreateServiceRequest createServiceRequest, String serviceId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateServiceWithChanges(String serviceId, UpdateServiceRequest updateRequest) {
        BusinessService service = businessServiceRepository.findById(serviceId)
                .orElseThrow(() -> new InvalidServiceException(serviceId, "Service not found"));

        // Update service properties update request
        service.setTitle(updateRequest.getTitle());
        service.setDescription(updateRequest.getDescription());
        service.setBasePrice(updateRequest.getBasePrice());
        service.setDeliveryTimeInHours(updateRequest.getDeliveryTimeInHours());
        service.setIsArchived(false);
        // Update other service properties as needed...

        // Update or add new features
        if (!updateRequest.getFeaturesToAdd().isEmpty()) {
            List<ServiceFeature> newFeatures = updateRequest.getFeaturesToAdd().stream()
                    .map(this::mapFeatureRequestToEntity)
                    .toList();
            service.getAdditionalFeatures().addAll(newFeatures);
        }

        // Remove features
        if (!updateRequest.getFeaturesToRemove().isEmpty()) {
            service.getAdditionalFeatures()
                    .removeIf(feature -> updateRequest.getFeaturesToRemove().contains(feature.getId()));
        }

        // Update availability for specified days
        Map<String, AvailabilityRequest> updatedAvailabilities = updateRequest.getUpdatedAvailabilities();
        if (!updatedAvailabilities.isEmpty()) {
            for (Map.Entry<String, AvailabilityRequest> entry : updatedAvailabilities.entrySet()) {
                String dayOfWeek = entry.getKey();
                AvailabilityRequest availabilityRequest = entry.getValue();

                Optional<Availability> existingAvailability = service.getAvailability().stream()
                        .filter(availability -> availability.getDayOfWeek().equalsIgnoreCase(dayOfWeek))
                        .findFirst();

                if (existingAvailability.isPresent()) {
                    // Update existing availability
                    Availability availability = existingAvailability.get();
                    availability.setAvailable(availabilityRequest.isAvailable());
                    availability.setStartTime(availabilityRequest.getStartTime());
                    availability.setEndTime(availabilityRequest.getEndTime());
                } else {
                    // Add new availability if not found
                    Availability newAvailability = mapAvailabilityRequestToEntity(availabilityRequest);
                    newAvailability.setDayOfWeek(dayOfWeek);
                    service.getAvailability().add(newAvailability);
                }
            }
        }

        // Save the updated service
        BusinessService updatedService = businessServiceRepository.save(service);

        // Log
        log.info("Updated Service: {}", updatedService);

        // success response
        return ResponseEntity.ok(new ApiResponse("Service updated successfully!", null, 200));
    }

    @Override
    public ResponseEntity<CreateServiceResp> addService(CreateServiceRequest request) {

        BusinessService newService = new BusinessService();

        newService.setTitle(request.getTitle());
        newService.setDescription(request.getDescription());
        newService.setBasePrice(request.getBasePrice());
        newService.setIsArchived(false);
        newService.setAverageStars(0.0f);
        newService.setTotalReviews(0);
        newService.setDeliveryTimeInHours(request.getDeliveryTimeInHours());

        // Initialize availability list
        newService.setAvailability(new ArrayList<>(0));

        // Get owner (user)
        newService.setOwner(userConsumerService
                .getUserById(request.getOwnerId())
                .orElseThrow(() -> new InvalidUserException(request.getOwnerId(), Consts.USER_NOT_FOUND)));

        final ServiceCategory category = serviceCategoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(Consts.CATEGORY,
                        request.getCategoryId(),
                        Consts.CATEGORY_NOT_FOUND));

        category.setServiceCount(category.getServiceCount() + 1);
        serviceCategoryRepository.save(category);
        newService.setServiceCategory(category);

        // Check if detailed features are provided
        if (!request.getFeatures().isEmpty()) {
            // Decompose service into features
            final List<ServiceFeature> features = new ArrayList<>();
            double totalFeaturePrice = 0.0;

            for (FeatureRequest featureRequest : request.getFeatures()) {
                ServiceFeature feature = mapFeatureRequestToEntity(featureRequest);

                // Save the feature in the database
                serviceFeatureRepository.save(feature);

                // Add the feature to the list of additional features in the new service
                features.add(feature);

                // Accumulate total feature price for calculating base price
                totalFeaturePrice += featureRequest.getPrice();
            }

            // Set additional features and calculate base price from feature prices
            newService.setAdditionalFeatures(features);
            newService.setBasePrice(totalFeaturePrice);
        } else {
            // Use provided base price directly
            List<ServiceFeature> features = new ArrayList<>();
            ServiceFeature feat = new ServiceFeature();
            feat.setDescription(newService.getDescription());
            feat.setTitle(newService.getTitle());
            feat.setPrice(request.getBasePrice());
            feat.setBase(true);
            serviceFeatureRepository.save(feat);
            features.add(feat);
            newService.setAdditionalFeatures(features);
            newService.setBasePrice(request.getBasePrice());
        }

        // Save availability for each day
        for (int dayIndex = 0; dayIndex < 7; dayIndex++) {
            AvailabilityRequest availabilityRequest = request.getAvailability().get(dayIndex);
            Availability availability = mapAvailabilityRequestToEntity(availabilityRequest);
            availability.setDayOfWeek(getDayOfWeek(dayIndex));
            availabilityRepository.save(availability);
            newService.getAvailability().add(availability);
        }

        // Save the new service
        return new ResponseEntity<>(new CreateServiceResp(businessServiceRepository.save(newService).getId()),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse> updateServicePhoto(String id, MultipartFile cover) {
        BusinessService service = businessServiceRepository.findById(id)
                .orElseThrow(() -> new InvalidServiceException(id, "Service not found"));

        service.setServiceImage(saveFileToDisk(cover));

        businessServiceRepository.save(service);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Service updated", 200));
    }

    @NotNull
    private CompletableFuture<ResponseEntity<FileSystemResource>> getLocalPlaceholder() throws IOException {

        final ClassPathResource res = new ClassPathResource("static/service.jpg");

        FileSystemResource fileSystemResource = new FileSystemResource(res.getFile());

        final String mediaType = Files
                .probeContentType(
                        fileSystemResource.getFile()
                                .toPath());

        final ResponseEntity<FileSystemResource> resp = ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .body(fileSystemResource);

        return CompletableFuture.completedFuture(resp);
    }

    public List<BusinessService> filterServices(Double minPrice, Double maxPrice, String dayOfWeek, Boolean isOpenNow,
            String keyword) {
        // Fetch all services from the repository
        List<BusinessService> allServices = businessServiceRepository.findAll();

        // Apply filters based on parameters
        List<BusinessService> filteredServices = new ArrayList<>(allServices);

        // Filter by price range
        if (minPrice != null && maxPrice != null) {
            filteredServices = filteredServices.stream()
                    .filter(service -> service.getBasePrice() >= minPrice && service.getBasePrice() <= maxPrice)
                    .collect(Collectors.toList());
        }

        // Filter by availability for a specific day
        if (dayOfWeek != null) {
            filteredServices = filteredServices.stream()
                    .filter(service -> service.getAvailability().stream()
                            .anyMatch(availability -> availability.getDayOfWeek().equalsIgnoreCase(dayOfWeek)))
                    .collect(Collectors.toList());
        }

        // Filter by open now or closed now
        if (isOpenNow != null) {
            // Implement logic to determine if the service is open or closed now
            // Compare current time with the service's availability schedule
            // For simplicity, let's assume isOpenNow is based on whether the service has
            // availability for the current day
            filteredServices = filteredServices.stream()
                    .filter(service -> service.getAvailability().stream()
                            .anyMatch(availability -> availability.getDayOfWeek()
                                    .equalsIgnoreCase(getCurrentDayOfWeek())))
                    .collect(Collectors.toList());
        }

        // Filter by keyword (search)
        if (keyword != null && !keyword.isEmpty()) {
            filteredServices = filteredServices.stream()
                    .filter(service -> service.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return filteredServices;
    }

    // Helper method to get the current day of the week (e.g., "Monday", "Tuesday",
    // etc.)
    private String getCurrentDayOfWeek() {
        return LocalDate.now().getDayOfWeek().name();
    }

}
