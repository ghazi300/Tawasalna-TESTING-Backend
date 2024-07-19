package com.tawasalna.auth.repository;

import com.tawasalna.auth.models.UserSettings;
import com.tawasalna.auth.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSettingsRepository extends MongoRepository<UserSettings, String> {

    Optional<UserSettings> findUserSettingsByUser(Users user);
}
