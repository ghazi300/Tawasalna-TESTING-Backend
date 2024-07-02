package com.tawasalna.social.repository;

import com.tawasalna.social.models.ResidentPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentPostRepository extends MongoRepository<ResidentPost,String> {

    @Query("{ 'user.id' : ?0, 'photos.0' : { $exists : true } }")
    List<ResidentPost> findPostsByUserIdAndPhotosNotNull(String userId);

    @Query("{ 'user.id' : ?0, 'video' : { $ne : null } }")
    List<ResidentPost> findPostsByUserIdAndVideoNotNull(String userId);

    @Query("{ 'user.id' : ?0, 'photos' : { $size : 0 }, 'video' : { $exists : false } }")
    List<ResidentPost> findByUserIdAndPhotosEmptyAndVideoIsNull(String userId);



}
