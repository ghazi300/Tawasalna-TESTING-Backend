package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.EnergieConsumption;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Map;

public interface EnergieConsumptionRepository extends MongoRepository<EnergieConsumption,String> {
    List<EnergieConsumption> findByLocation(String location);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$location', totalAmount: { $sum: '$amount' } } }",
            "{ $sort: { totalAmount: -1 } }"
    })
    List<Map<String, Object>> groupAndSortByLocation();
}
