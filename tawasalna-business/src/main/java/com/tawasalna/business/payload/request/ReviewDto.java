package com.tawasalna.business.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDto {

    private String id;

    private String content;

    private Integer numberOfStars;

    private Date reviewedAt;

    private String serviceId;

    private String productId;

    private String ownerId;
}
