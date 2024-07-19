package com.tawasalna.shared.userapi.service;

import com.tawasalna.shared.userapi.model.Users;

import java.util.List;
import java.util.Optional;

public interface IUserConsumerService {

    Optional<Users> getUserById(String id);

    List<Users> getUsersByCommunityId(String communityId);

    void addRCA(Users user);

    void removeRCA(Users user);

    void addUserToCommunity(Users user, String communityId);

    void removeUserFromCommunity(Users user, String communityId);
}
