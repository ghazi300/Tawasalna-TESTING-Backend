package com.tawasalna.social.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddResidentPostDTO {

    private String residentId;
    private String caption;
    private MultipartFile video;
    private List<MultipartFile> photos;
}

