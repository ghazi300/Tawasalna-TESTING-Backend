package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.MaintenanceTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MaintenanceTaskRepository extends MongoRepository<MaintenanceTask,String> {


}
