package com.tawasalna.shared.communityapi.service;

import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.utils.Consts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
@Slf4j
public class CommunityConsumerServiceImpl implements ICommunityConsumerService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_ENDPOINT = "http://194.146.13.51:8093/tawasalna-community/community";

    private final RestClient restClient = RestClient.create();
    private final HttpServletRequest request;

    @Value("${app.communityConsumer.api}")
    private String communityEndpoint;

    @Autowired
    public CommunityConsumerServiceImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Optional<Community> getCommunityById(String communityId) {
        log.debug("Community id: {}", communityId);


        final Community community = restClient
                .get()
                .uri(communityEndpoint + "/" + communityId)
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Community.class);

        return Optional.ofNullable(community);
    }

    @Override
    public void save(String communityId, String userId) {
    ////////// Call for Removing Community Admin Role from a user
        restClient
                .put()
                .uri(communityEndpoint + "/usersRemove/" + userId + "/" + communityId) // PUT
                .header(Consts.AUTHORIZATION_HEADER, request.getHeader(Consts.AUTHORIZATION_HEADER))
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve();
    }
}
