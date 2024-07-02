package com.tawasalna.pms.repos;

import com.tawasalna.pms.models.Property;
import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;

import java.util.List;

public interface PropertyRepositoryCustom {
    List<Property>findPropertiesByFilters(String communityId, PropertyStatus status, int bedrooms, int bathrooms, Long minPrice, Long maxPrice, PropertyType propertyType, String sortBy);

}
