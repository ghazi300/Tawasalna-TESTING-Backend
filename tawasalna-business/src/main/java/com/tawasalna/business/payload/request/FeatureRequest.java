package com.tawasalna.business.payload.request;


import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureRequest{
    private String id;
    private String title;
    private String description;
    private Double price;
    private String isBase;
}
