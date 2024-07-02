package com.tawasalna.business.repository;

import com.tawasalna.business.models.Need;
import com.tawasalna.shared.userapi.model.Users;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NeedRepository extends MongoRepository<Need, String> {

    List<Need> findNeedByClientIdAndIsActiveTrueOrderByCreatedAtDesc(Users user);

    List<Need> findNeedByClientIdAndIsActiveFalseOrderByCreatedAtDesc(Users user);

    @Query("{ 'needStatus' : 'SEARCHING', '_id':  ObjectId('?0')}")
    Optional<Need> findOneByIdAndIsActiveTrueAndNeedStatusSearching(String id);

    @Query("{ 'needStatus' : 'SEARCHING' }")
    List<Need> findNeedByNeedStatusSearchingAndIsActiveTrueOrderByCreatedAtDesc();

    @Query("{ 'needStatus' : 'SEARCHING' }")
    List<Need> findNeedByNeedStatusSearchingAndIsActiveFalseOrderByCreatedAtDesc();

    @Query(
            value = """
                                {
                                 'needStatus' : 'SEARCHING',
                                  'categories':  { '$in' : ?0 }
                                }
                    """,
            fields = "{ 'quotes' : 0 }"
    )
    Page<Need> findAllByNeedStatusSearchingAndIsActiveTrueAnOrderByCreatedAtDesc(List<ObjectId> categories, Pageable pageable);
}
