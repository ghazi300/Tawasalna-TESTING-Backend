package com.tawasalna.administration.controllers;

import com.tawasalna.administration.payload.response.VerifResponse;
import com.tawasalna.administration.services.IUserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class WSBusinessVerifController {

    private final IUserManagementService userManagementService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/approve-business/{requestId}")
    public VerifResponse approveBusinessRequest(
            final @DestinationVariable("requestId") String requestId
    ) {
        final Map<String, Object> result = new HashMap<>();
        result.put("message", "Business approved");
        result.put("success", true);
        result.put("url", "LoadingScreen");

        template.
                convertAndSend("/topic/send/%s".
                                formatted(
                                        userManagementService
                                                .approveBusinessRequestWS(requestId)),
                        result
                );

        return new VerifResponse("Business approved", true);
    }

    @MessageMapping("/reject-business/{requestId}")
    public VerifResponse rejectBusinessRequest(
            final @DestinationVariable("requestId") String requestId
    ) {
        final Map<String, Object> result = new HashMap<>();
        result.put("message", "Business rejected");
        result.put("success", true);
        result.put("url", "LoadingScreen");

        template.
                convertAndSend("/topic/send/%s".
                                formatted(userManagementService
                                        .rejectBusinessRequestWS(requestId)),
                        result
                );

        return new VerifResponse("Business rejected", false);
    }
}
