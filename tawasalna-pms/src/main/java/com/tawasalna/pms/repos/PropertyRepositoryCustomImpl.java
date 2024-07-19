package com.tawasalna.pms.repos;

import com.tawasalna.pms.models.Property;
import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PropertyRepositoryCustomImpl implements PropertyRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Property> findPropertiesByFilters(String communityId, PropertyStatus status, int bedrooms, int bathrooms, Long minPrice, Long maxPrice, PropertyType propertyType, String sortBy) {
        Query query = new Query();


        if (communityId != null && !communityId.isEmpty()) {
            query.addCriteria(Criteria.where("community.id").is(communityId));
        }

        if (status != null) {
            query.addCriteria(Criteria.where("status").is(status));
        }

        if (bedrooms > 0) {
            query.addCriteria(Criteria.where("bedrooms").is(bedrooms));
        }

        if (bathrooms > 0) {
            query.addCriteria(Criteria.where("bathrooms").is(bathrooms));
        }

        if (minPrice != null && maxPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice).lte(maxPrice));
        } else if (minPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice));
        } else if (maxPrice != null) {
            query.addCriteria(Criteria.where("price").lte(maxPrice));
        }

        if (propertyType != null) {
            query.addCriteria(Criteria.where("propertyType").is(propertyType));
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            query.with(Sort.by(Sort.Direction.DESC, sortBy));
        }

        /*query.fields().include("id")
                .include("price")
                .include("pricePostfix")
                .include("buildingName")
                .include("ownerName")
                .include("ownerContact")
                .include("status")
                .include("communityId")
                .include("bedrooms")
                .include("bathrooms")
                .include("propertyType")
                .include("propertyAddress");*/


        return mongoTemplate.find(query, Property.class);
    }
}
