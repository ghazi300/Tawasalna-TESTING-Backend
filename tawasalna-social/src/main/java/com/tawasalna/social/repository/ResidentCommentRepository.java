package com.tawasalna.social.repository;

import com.tawasalna.social.models.ResidentComments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentCommentRepository extends MongoRepository<ResidentComments,String>  {
    List<ResidentComments> findByPost_Id(String postId);
    List<ResidentComments> findByPostId(String postId);
}
