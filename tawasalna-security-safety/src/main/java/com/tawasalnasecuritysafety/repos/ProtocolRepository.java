package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.Protocol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends MongoRepository<Protocol,String> {
}
