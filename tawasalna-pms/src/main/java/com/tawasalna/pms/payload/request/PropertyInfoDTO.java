package com.tawasalna.pms.payload.request;

import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyInfoDTO {
    private String id;
    private Long price;
    private String pricePostfix;
    private String buildingName;
    private String ownerName;
    private String ownerContact;
    private PropertyStatus status;
    private int bedrooms;
    private int bathrooms;
    @DocumentReference
    private Community community;
    private boolean archived;

    @DocumentReference
    private Users agent;

}
