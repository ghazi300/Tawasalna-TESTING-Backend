package com.tawasalna.business.repository;

import com.tawasalna.business.models.BusinessService;
import com.tawasalna.business.models.ServiceCategory;
import com.tawasalna.business.models.ServiceFeature;
import com.tawasalna.shared.userapi.model.Users;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessServiceRepository extends MongoRepository<BusinessService, String> {
    List<BusinessService> findBusinessServicesByOwnerAndIsArchivedFalse(Users user);

    List<BusinessService> findBusinessServicesByOwnerAndIsArchivedTrue(Users user);

    List<BusinessService> findAllByIsArchivedFalse();

    List<BusinessService> findAllByIsArchivedTrue();

    Page<BusinessService> findAllByOwnerAndIsArchivedFalse(Users user, Pageable pageable);

    Page<BusinessService> findAllByOwnerAndIsArchivedTrue(Users user, Pageable pageable);

    Page<BusinessService> findAllByServiceCategoryAndIsArchivedFalse(ServiceCategory category, Pageable pageable);

    List<BusinessService> findAllByTitleLikeIgnoreCaseAndIsArchivedFalse(String title);

    @Query("{ $and: [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +

            "]}")
    Page<BusinessService> search(String keyword, Pageable pageable);


    @Query(value = "{ 'title': { $regex: ?0, $options: 'i' } }", fields = "{ 'title': 1 }")
    List<BusinessService> findTitleSuggestions(String keyword);

    @Query(value = "{ 'additionalFeatures.title': { $regex: ?0, $options: 'i' } }", fields = "{ 'additionalFeatures.title': 1 }")
    List<ServiceFeature> findFeatureTitleSuggestions(String keyword);


    @Query(value = """
                        {
                            'isArchived': false,
                            'owner': ObjectId('?0'),
                            'serviceCategory': { $in: ?1  }
                        }
            """,
            fields = """
                        {
                            '_id': 1,
                            'title': 1
                        }
                    """
    )
    List<BusinessService> findActiveServicesOfBusinessOwner(String businessId, List<ObjectId> categories);
}
