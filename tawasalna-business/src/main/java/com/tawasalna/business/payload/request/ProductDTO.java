package com.tawasalna.business.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {


    private String title;

    private String description;

    private Float price;

    private Boolean isArchived;

    private String publisherId;

    private String categoryId;
}
