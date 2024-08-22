package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Reply;
import com.ipactconsult.tawasalnabackendapp.payload.request.ReplyRequest;
import org.springframework.stereotype.Service;

@Service

public class ReplyMapper {
    public Reply toReply(ReplyRequest replyRequest) {
        return Reply.builder()
                .replyText(replyRequest.getReplyText())

                .build();
    }
}
