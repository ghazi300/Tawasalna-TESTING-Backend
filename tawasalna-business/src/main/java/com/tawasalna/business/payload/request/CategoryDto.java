package com.tawasalna.business.payload.request;

import lombok.Data;

@Data
public class CategoryDto {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private int serviceCount;
}
