package com.tawasalna.social.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResidentPostDTO {
    private String id;
    private String residentId;
    private String caption;
    private Date postDateTime;
    private List<String> photos;
    private String video;
    private Set<String> likedBy = new HashSet<>();
    private List<String> comments = new ArrayList<>();
    private String userName;
    private String businessId;
}
