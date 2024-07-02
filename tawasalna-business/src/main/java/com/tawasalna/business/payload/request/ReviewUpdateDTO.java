package com.tawasalna.business.payload.request;

import lombok.Data;

@Data
public class ReviewUpdateDTO {
    private String content;
    private Integer numberOfStars;
}
