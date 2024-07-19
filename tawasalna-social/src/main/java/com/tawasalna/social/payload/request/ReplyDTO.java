package com.tawasalna.social.payload.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyDTO {
    private String residentId;
    private String text;
    private Date createdAt;
    private String userName;
}
