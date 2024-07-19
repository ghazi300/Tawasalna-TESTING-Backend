package com.tawasalna.business.models;

import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    private String id;

    private String title;

    private String description;

    private String image;

    private Float price;

    private Boolean isArchived;

    @DocumentReference
    private Users publisher;

    @DocumentReference
    private ProductCategory productCategory;

    private Integer totalReviews;

    private Float averageStars;
}
