package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.usersettings.IUserSettingsService;
import com.tawasalna.auth.models.UserSettings;
import com.tawasalna.auth.payload.request.*;
import com.tawasalna.shared.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
@Tag(name = "User settings Controller", description = "Controller for managing user settings")
public class UserSettingsController {

    private final IUserSettingsService userSettingsService;

    @Operation(summary = "Get user settings by user id")
    @GetMapping("/get")
    public ResponseEntity<UserSettings> getByUserId(@RequestParam("userId") String userId) {
        return userSettingsService.getOfUser(userId);
    }

    @PatchMapping("/update")
    @Operation(summary = "Update user settings by user id")
    public ResponseEntity<UserSettings> updateUserSettings(
            @RequestParam("userId") String userId,
            @RequestBody UserSettingsUpdateDto userSettingsUpdateDto
    ) {
        return userSettingsService.updateUserSettingsOfUser(userId, userSettingsUpdateDto);
    }

    @PatchMapping("/update-password/{userId}")
    public ResponseEntity<ApiResponse> updateUserPassword(
            @PathVariable("userId") String userId,
            @RequestBody PasswordUpdateDTO passwordUpdateDto) {
        return this.userSettingsService.updatePassword(userId, passwordUpdateDto);
    }


    @PatchMapping("/update-username/{id}")
    ResponseEntity<ApiResponse> updateUsername(@PathVariable("id") String userId, @Valid @RequestBody UpdateUsernameDTO updateUsernameDTO) {
        return userSettingsService.updateUsername(userId, updateUsernameDTO);
    }

    @PatchMapping("/disable-account/{id}")
    ResponseEntity<ApiResponse> disableAccount(@PathVariable("id") String userId) {
        return userSettingsService.disableAccount(userId);
    }
}
