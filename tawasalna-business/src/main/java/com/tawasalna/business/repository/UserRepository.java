package com.tawasalna.business.repository;


import com.tawasalna.shared.userapi.model.AccountStatus;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    @Query(
            value = "{'accountStatus': ?0, 'businessProfile.verified': ?1, 'community':  ObjectId('?2')}",
            fields = "{ 'businessProfile.businessName': 1, '_id': 1}"
    )
    Page<Users> findAllByAccountStatusAndBusinessProfile_VerifiedAndCommunity
            (AccountStatus accountStatus, Boolean verified, String community, Pageable pageable);


    @Query(
            value = """ 
                    {
                      'accountStatus': ?0,
                      '$or':
                        [
                            {
                                'residentProfile.fullName': {$regex : ?3, $options: 'i'}
                            },
                            {
                                'businessProfile.businessName': {$regex : ?2, $options: 'i'},
                                'businessProfile.verified': ?1
                            }
                        ]
                    }
    """
    )
    List<Users> searchUsersOfCommunity(
            AccountStatus accountStatus,
            Boolean verified,
            String businessName,
            String fullName
    );
}
