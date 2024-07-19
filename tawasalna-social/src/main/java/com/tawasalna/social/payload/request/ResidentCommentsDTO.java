package com.tawasalna.social.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResidentCommentsDTO {
    private String id;
    private String postId;
    private String residentId;
    private String commenttext;
    private Date createdAt;
    private String userName;
    private String profilePhoto;
    private int replies;

}
