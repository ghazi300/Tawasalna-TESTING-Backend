package com.tawasalna.pms.payload.request;

import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
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
    private Date yearOfBuild;

    //Address info
    private  String propertyAddress;
    private String buildingName;

    //Propert images
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
    private String communityId;
    private String agentId;
    private String ownerId;
    private String tenantId;
    private String description;
    private PropertyType propertyType;
    private boolean archived;
    private boolean propertyAvailable;


}
