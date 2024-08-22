package com.tawasalna.tawasalnacrisis.repositories;

import com.tawasalna.tawasalnacrisis.models.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<File,String> {
    Optional<File> findByFileName(String filename);
}
