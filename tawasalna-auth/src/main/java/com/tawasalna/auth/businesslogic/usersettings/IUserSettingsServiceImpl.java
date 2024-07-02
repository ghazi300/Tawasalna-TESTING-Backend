package com.tawasalna.auth.businesslogic.usersettings;

import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidUserSettingsException;
import com.tawasalna.auth.models.PrivacySettings;
import com.tawasalna.auth.models.UserSettings;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.AccountStatus;
import com.tawasalna.auth.models.enums.AppTheme;
import com.tawasalna.auth.payload.request.PasswordUpdateDTO;
import com.tawasalna.auth.payload.request.UpdateUsernameDTO;
import com.tawasalna.auth.payload.request.UserSettingsUpdateDto;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.auth.repository.UserSettingsRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class IUserSettingsServiceImpl implements IUserSettingsService {

    private final UserSettingsRepository userSettingsRepository;
    private final UserRepository userRepository;
    private final IAuthUtilsService authUtilsService;

    @Override
    public ResponseEntity<UserSettings> getOfUser(String id) {

        final Users user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new InvalidUserException(
                                id,
                                "User not found"
                        )
                );

        final UserSettings body = userSettingsRepository
                .findUserSettingsByUser(user)
                .orElseThrow(() ->
                        new InvalidUserSettingsException(
                                id,
                                "Invalid user entity"
                        )
                );

        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<UserSettings> updateUserSettingsOfUser(
            String id,
            UserSettingsUpdateDto settings
    ) {

        final Users user = userRepository
                .findById(id)
                .orElseThrow(() -> new InvalidUserException(
                                id,
                                "User not found"
                        )
                );

        return ResponseEntity.ok(userSettingsRepository
                .findUserSettingsByUser(user)
                .map(s -> {
                    s.setPrivacySettings(new PrivacySettings(
                            settings.getIsEmailVis(),
                            settings.getIsFullNameVis()
                    ));
                    s.setPreferredLocale(settings.getPreferredLocale());
                    s.setPreferredTheme(AppTheme.valueOf(
                            settings.getPreferredTheme()));

                    s.setIsNotificationsActive(
                            settings.getIsNotificationsActive());

                    return userSettingsRepository.save(s);
                })
                .orElseThrow(
                        () -> new InvalidUserSettingsException(
                                id,
                                "Invalid user entity"
                        )
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse> updatePassword(String userId,
                                                      PasswordUpdateDTO passwordUpdateDto) {

        final Users user = userRepository
                .findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        if (!authUtilsService.comparePwd(passwordUpdateDto.getOldPassword(), user.getPassword()))
            return ResponseEntity.badRequest().body(ApiResponse.ofError("Incorrect password", 400));

        if (passwordUpdateDto.getOldPassword().equals(passwordUpdateDto.getNewPassword()))
            return ResponseEntity.badRequest().body(ApiResponse.ofError("You used the same password again", 400));

        user.setPassword(authUtilsService.encodePwd(passwordUpdateDto.getNewPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Password updated successfully", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> updateUsername(String userId, UpdateUsernameDTO updateUsernameDTO) {

        final Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND)
                );

        final boolean exists = userRepository.findUsersByUsername(updateUsernameDTO.getNewUsername()).isPresent();

        if (exists)
            throw new InvalidUserException(updateUsernameDTO.getNewUsername(), "Username already in use.");


        user.setUsername(updateUsernameDTO.getNewUsername());

        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.ofSuccess("Username updated successfully!", 200));
    }

    @Override
    public ResponseEntity<ApiResponse> disableAccount(String userId) {
        final Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND)
                );

        user.setAccountStatus(AccountStatus.DISABLED);

        userRepository.save(user);

        // TODO: email the community admin about the change
        return ResponseEntity.ok(ApiResponse.ofSuccess("Account disabled, goodbye!", 200));
    }
}
