package com.tawasalna.shared.userapi.service;

import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.utils.Consts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserConsumerServiceImpl implements IUserConsumerService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_ENDPOINT = "http://194.146.13.51:8070/tawasalna-user/user";
    private final RestClient restClient = RestClient.create();
    private final HttpServletRequest request;

    @Value("${app.userConsumer.api}")
    private String authEndpoint;

    @Autowired
    public UserConsumerServiceImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Optional<Users> getUserById(String userId) {
        log.debug("User id: {}", userId);

        final Users user = restClient
                .get()
                .uri(authEndpoint + "/" + userId)
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Users.class);

        return Optional.ofNullable(user);
    }

    @Override
    public List<Users> getUsersByCommunityId(String communityId) {
        log.debug("Fetching users for community id: {}", communityId);

        return restClient
                .get()
                .uri(authEndpoint + "/community/" + communityId)
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public void addRCA(Users user) {
////////// Call for Adding Community Admin Role to a user
        restClient
                .put()
                .uri(authEndpoint + "/setRCA/" + user.getId()) // PUT
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve();
    }

    @Override
    public void removeRCA(Users user) {
////////// Call for Removing Community Admin Role from a user
        restClient
                .put()
                .uri(authEndpoint + "/removeRCA/" + user.getId()) // PUT
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve();
    }

    @Override
    public void addUserToCommunity(Users user, String communityId) {
////////// Call for Adding Community Admin Role to a user
        restClient
                .put()
                .uri(authEndpoint + "/setC/" + user.getId() + "/" + communityId) // PUT
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve();
    }

    @Override
    public void removeUserFromCommunity(Users user, String communityId) {
////////// Call for Removing Community Admin Role from a user
        restClient
                .put()
                .uri(authEndpoint + "/removeC/" + user.getId() + "/" + communityId) // PUT
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve();
    }
}
