package com.tawasalna.auth.businesslogic.usersettings;

import com.tawasalna.auth.models.UserSettings;
import com.tawasalna.auth.payload.request.*;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface IUserSettingsService {

    ResponseEntity<UserSettings> getOfUser(String id);

    ResponseEntity<UserSettings> updateUserSettingsOfUser(String id, UserSettingsUpdateDto settings);

    ResponseEntity<ApiResponse> updatePassword(String userId, PasswordUpdateDTO passwordUpdateDto);

    ResponseEntity<ApiResponse> updateUsername(String userId, UpdateUsernameDTO updateUsernameDTO);

    ResponseEntity<ApiResponse> disableAccount(String userId);
}
