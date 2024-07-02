package com.tawasalna.pms.repos;

import com.tawasalna.pms.models.Property;
import com.tawasalna.pms.models.enums.PropertyStatus;
import com.tawasalna.pms.models.enums.PropertyType;
import com.tawasalna.pms.payload.request.PropertyDTO;
import com.tawasalna.pms.payload.request.PropertyInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends MongoRepository<Property,String>, PropertyRepositoryCustom  {
    List<Property>findAllByAgent_IdAndArchivedFalse(String agentId);
    List<Property>findAllByAgent_IdAndArchivedTrue(String agentId);
    List<Property>findAllByAgent_Id(String agentId);

    Page<PropertyInfoDTO>findAllByAgent_IdAndArchivedFalseAndBuildingName(String agentId, String buildingName, Pageable pageable);
    Page<PropertyInfoDTO>findAllByAgent_IdAndArchivedFalse(String agentId, Pageable pageable);
    Page<PropertyInfoDTO>findAllByAgent_IdAndArchivedTrueAndBuildingName(String agentId, String buildingName, Pageable pageable);
    Page<PropertyInfoDTO>findAllByAgent_IdAndArchivedTrue(String agentId, Pageable pageable);
    Page<PropertyInfoDTO>findAllByAgent_Id(String agentId, Pageable pageable);
    Page<PropertyInfoDTO> findByBuildingNameAndAgent_Id(String buildingName, String id ,Pageable pageable);



}
