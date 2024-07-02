package com.tawasalna.business.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productCategories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCategory {

    @Id
    private String id;

    private String name;

    private String description;

    private String cover;

    private Boolean isActive;

    private Integer productsCount;
}
