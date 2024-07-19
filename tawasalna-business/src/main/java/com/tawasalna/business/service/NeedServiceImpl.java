package com.tawasalna.business.service;


import com.tawasalna.business.exceptions.InvalidServiceException;
import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.Need;
import com.tawasalna.business.models.ServiceCategory;
import com.tawasalna.business.models.enums.NeedStatus;
import com.tawasalna.business.payload.request.CreateNeedRequest;
import com.tawasalna.business.payload.request.NeedUpdateDTO;
import com.tawasalna.business.repository.BusinessServiceRepository;
import com.tawasalna.business.repository.NeedRepository;
import com.tawasalna.business.repository.ServiceCategoryRepository;
import com.tawasalna.business.repository.UserRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class NeedServiceImpl implements INeedService {

    private final NeedRepository needRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final BusinessServiceRepository businessServiceRepository;
    private final UserRepository userRepository;

    @Override
    public Need addNeed(CreateNeedRequest needDTO) {
        Users client = userRepository.findById(needDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        List<ServiceCategory> chosenCategories = getChosenCategories(needDTO.getCategories());

        if (chosenCategories.isEmpty()) {
            throw new EntityNotFoundException("need category", "category id", "No Category selected for the need");
        }

        final Need need = new Need();
        need.setInfo(needDTO.getInfo());
        need.setReservationDayStart(needDTO.getReservationDayStart());
        need.setReservationDayEnd(needDTO.getReservationDayEnd());
        need.setCreatedAt(new Date());
        need.setClientId(client);
        need.setCategories(chosenCategories);
        need.setNeedStatus(NeedStatus.SEARCHING);
        need.setIsActive(true);

        return needRepository.save(need);
    }

    @Override
    public List<Need> getNeedsByBusinessId(String businessId) {
        // Fetch all needs
        List<Need> allNeeds = needRepository.findAll();

        // Filter needs based on the provided business ID
        return allNeeds.stream()
                .filter(need ->
                        need
                                .getQuotes()
                                .stream()
                                .anyMatch(quote -> quote
                                        .getBusinessId()
                                        .getId()
                                        .equals(businessId)
                                )
                )
                .toList();
    }

    @Override
    public ResponseEntity<Page<Need>> getNeedsPaged(String businessId, Integer pageNumber) {
        if (pageNumber <= 0) pageNumber = 1;

        final Users business = userRepository.findById(businessId).orElseThrow(() -> new RuntimeException("User not found"));

        List<ObjectId> serviceCategories = businessServiceRepository.findBusinessServicesByOwnerAndIsArchivedFalse(business)
                .stream()
                .map(BusinessService::getServiceCategory)
                .map(ServiceCategory::getId)
                .map(ObjectId::new)
                .toList();

        Page<Need> needs = needRepository
                .findAllByNeedStatusSearchingAndIsActiveTrueAnOrderByCreatedAtDesc(
                        serviceCategories,
                        PageRequest.of(
                                pageNumber - 1,
                                10
                        )
                );

        return ResponseEntity.ok(needs);
    }


    private List<ServiceCategory> getChosenCategories(List<String> chosenCategoriesIds) {
        List<ServiceCategory> chosenCategories = new ArrayList<>();

        if (chosenCategoriesIds != null) {
            for (String categoryId : chosenCategoriesIds) {
                ServiceCategory category = serviceCategoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found: ", categoryId, "not Found"));
                chosenCategories.add(category);
            }
        }

        return chosenCategories;
    }

    @Override
    public ResponseEntity<List<Need>> getActiveNeedByClientIdSearching(String clientId) {
        final Users user = userRepository.findById(clientId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                clientId,
                                Consts.USER_NOT_FOUND
                        )
                );
        return ResponseEntity.ok(needRepository.findNeedByClientIdAndIsActiveTrueOrderByCreatedAtDesc(user));
    }

    @Override
    public ResponseEntity<List<Need>> getArchivedNeedByClientIdSearching(String clientId) {
        final Users user = userRepository.findById(clientId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                clientId,
                                Consts.USER_NOT_FOUND
                        )
                );
        return ResponseEntity.ok(needRepository.findNeedByClientIdAndIsActiveFalseOrderByCreatedAtDesc(user));
    }

    @Override
    public ResponseEntity<List<Need>> getNeedsSearching() {
        return ResponseEntity.ok(needRepository
                .findNeedByNeedStatusSearchingAndIsActiveTrueOrderByCreatedAtDesc()
                .stream()
                .filter(quote -> quote.getNeedStatus() == NeedStatus.SEARCHING)
                .toList());
    }

    @Override
    public ResponseEntity<Need> getNeedById(String needId) {
        return ResponseEntity.ok(
                needRepository.findById(needId)
                        .orElseThrow(
                                () -> new InvalidEntityBaseException(needId, Consts.NEED_NOT_FOUND, "Quote"

                                )
                        )
        );
    }

    @Override
    public ResponseEntity<ApiResponse> updateNeed(NeedUpdateDTO updateDto, String needId) {
        List<ServiceCategory> chosenCategories = getChosenCategories(updateDto.getCategories());
        if (chosenCategories.isEmpty()) {
            throw new EntityNotFoundException("need category", "category id", "No Category selected for the quote");
        }

        final Need updated = needRepository
                .findById(needId)
                .map(q -> {
                    q.setInfo(updateDto.getInfo());
                    q.setCategories(chosenCategories);
                    q.setReservationDayEnd(updateDto.getReservationDayEnd());
                    q.setReservationDayStart(updateDto.getReservationDayStart());


                    return needRepository.save(q);
                })
                .orElseThrow(() -> new InvalidEntityBaseException(needId, "Need not found", "Need"));

        log.info(updated.toString());

        return ResponseEntity.ok(new ApiResponse("Need updated successfully!", null, 200));
    }

    @Override
    public ResponseEntity<ApiResponse> archive(String id) {
        final Need need = needRepository.findById(id)
                .orElseThrow(
                        () -> new InvalidServiceException(id, "Service Not Found"

                        )
                );
        need.setIsActive(false);
        needRepository.save(need);
        return ResponseEntity.ok(new ApiResponse("need archived", null, 200));
    }

    @Override
    public ResponseEntity<ApiResponse> unArchive(String id) {
        final Need need = needRepository.findById(id)
                .orElseThrow(
                        () -> new InvalidServiceException(id, "need Not Found"

                        )
                );
        need.setIsActive(true);
        needRepository.save(need);
        return ResponseEntity.ok(new ApiResponse("need unarchived", null, 200));
    }

    @Override
    public ResponseEntity<List<BusinessService>> getMatchingServices(String needId, String businessId) {

        final String business = userRepository
                .findById(businessId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                businessId,
                                Consts.USER_NOT_FOUND
                        )
                )
                .getId();

        final List<ObjectId> categoryIds = needRepository
                .findOneByIdAndIsActiveTrueAndNeedStatusSearching(needId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                businessId,
                                Consts.USER_NOT_FOUND
                        )
                )
                .getCategories()
                .parallelStream()
                .map(ServiceCategory::getId)
                .map(ObjectId::new)
                .toList();

        return ResponseEntity.ok(businessServiceRepository
                .findActiveServicesOfBusinessOwner(business, categoryIds));
    }
}
