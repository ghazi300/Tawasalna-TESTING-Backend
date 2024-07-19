package com.tawasalna.shared.communityapi.service;

import com.tawasalna.shared.communityapi.model.Community;
import java.util.Optional;

public interface ICommunityConsumerService {

    Optional<Community> getCommunityById(String id);


    void save(String communityId, String userId);
}
