package com.tawasalna.social.payload.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDTO {

    private String id;
    private String name;
    private String description;
    private Set<String> adminIds;
}