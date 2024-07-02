package com.tawasalna.pms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.userapi.model.Users;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "property")
public class Property {
    @Id
    private String id;
    //@Indexed(unique = true)
    private String ref;

    //Basic information
    private Long price;
    private String pricePostfix;
    private Long oldPrice;
    private Long areaSize;
    private String areaSizePostfix;
    private Long lotSize;
    private String lotSizePostfix;
    private int bedrooms;
    private int bathrooms;
    private Long garageSize;
    private String garageSizePostfix;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yearOfBuild;

    //Address info
    private  String propertyAddress;
    private String buildingName;

    //Property images
    private String myimages;

    //Floor plans
    private String floorname;
    private String floorDescription;
    private Long floorPrice;
    private String floorPricePostfix;

    //Owner information
    private String ownerName;
    private String ownerContact;
    private String ownerAddress;
    private PropertyStatus status;
    @DocumentReference
    private Community community;
    @DocumentReference
    private Users agent;
    @DocumentReference
    private Users owner;
    @DocumentReference
    private Users tenant;

    private String description;
    private PropertyType propertyType;
    private boolean archived;
    private boolean propertyAvailable;

    //@DocumentReference
    //private Users agent;

}
