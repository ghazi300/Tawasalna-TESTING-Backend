package com.tawasalna.auth.businesslogic.ResidentManagement;


import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidUserVerifCodeException;
import com.tawasalna.auth.models.UserVerifCode;
import com.tawasalna.auth.models.Users;

import com.tawasalna.auth.payload.request.AddSecondaryEmailDTO;
import com.tawasalna.auth.payload.request.CheckPasswordMatchDTO;
import com.tawasalna.auth.payload.request.UpdatePasswordDTO;
import com.tawasalna.auth.payload.request.VerifySecondaryEmailCodeDTO;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.auth.repository.UserVerifCodeRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.mail.IMailingService;
import com.tawasalna.shared.mail.TemplateVariable;
import com.tawasalna.shared.utils.Consts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResidentManagementServiceImpl implements IResidentManagementService {
    private final UserRepository userRepository;
    private final IAuthUtilsService authUtilsService;
    private final UserVerifCodeRepository userVerifCodeRepository;
    private final IMailingService mailingService;

    private boolean verifyCodeValidity(UserVerifCode codeV) {
        return codeV.getExpiredAt().isAfter(LocalDateTime.now());
    }

    @Override
    public ApiResponse updatePasswordById(String userId, UpdatePasswordDTO updatePasswordDTO) {
        final Users user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                userId,
                                Consts.USER_NOT_FOUND
                        )
                );

        final String newPassword = updatePasswordDTO.getNewpassword();
        final String confirmPassword = updatePasswordDTO.getConfirmpassword();
        final String currentPassword = updatePasswordDTO.getCurrentpassword();

        if (newPassword == null || newPassword.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty() ||
                currentPassword == null || currentPassword.isEmpty()) {
            throw new InvalidUserException(
                    userId,
                    "Passwords cannot be empty."
            );
        }
        if (!authUtilsService.comparePwd(currentPassword, user.getPassword())) {
            throw new InvalidUserException(
                    userId,
                    "Current Password is wrong."
            );
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidUserException(
                    userId,
                    "New password and confirm password do not match."
            );
        }

        if (authUtilsService.comparePwd(newPassword, user.getPassword())) {
            throw new InvalidUserException(
                    userId,
                    "New password cannot be the same as the last password."
            );
        }

        user.setPassword(authUtilsService.encodePwd(newPassword));

        userRepository.save(user);

        return new ApiResponse("Password updated successfully", null, 200);
    }

    @Override
    public ApiResponse isPasswordMatch(String userId, CheckPasswordMatchDTO checkPasswordMatchDTO) throws MessagingException {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));
        final String enteredPassword = checkPasswordMatchDTO.getEnteredpassword();

        if (enteredPassword == null || enteredPassword.isEmpty()) {
            throw new InvalidUserException(
                    userId,
                    "Entred Password  is  empty."
            );
        }
        System.out.println("User Password: " + user.getPassword());

        if (!authUtilsService.comparePwd(enteredPassword, user.getPassword())) {
            throw new InvalidUserException(
                    userId,
                    "Entred Password  is  not equal to your password."
            );
        }

        return new ApiResponse("Password Verification done!", null, 200);
    }


    @Override
    public ApiResponse switchMainEmail(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(userId, Consts.USER_NOT_FOUND));

        String currentMainEmail = user.getEmail();
        String currentSecondaryEmail = user.getSecondaryemail();

        user.setEmail(currentSecondaryEmail);
        user.setSecondaryemail(currentMainEmail);

        userRepository.save(user);
        return new ApiResponse("Success", null, 200);
    }

    @Override
    public ApiResponse verifySecondaryEmailCode(String userId, VerifySecondaryEmailCodeDTO verifySecondaryEmailCodeDTO) {

        Optional<Users> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new InvalidUserVerifCodeException(
                    userId, "User not found!");
        }

        final String email = verifySecondaryEmailCodeDTO.getEmail();
        final String code = verifySecondaryEmailCodeDTO.getCode();

        final Optional<UserVerifCode> found = userVerifCodeRepository.findByCodeAndEmail(code, email);

        System.out.println("Found value: " + found);

        if (found.isEmpty()) {
            throw new InvalidUserVerifCodeException(
                    code, Consts.INVALID_CODE
            );
        }

        if (!verifyCodeValidity(found.get())) {
            throw new InvalidUserVerifCodeException(code, "Code Expired");
        }

        Users user = optionalUser.get();

        if (user.getSecondaryemail() != null && !user.getSecondaryemail().isEmpty()) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                String temp = user.getEmail();
                user.setEmail(user.getSecondaryemail());
                user.setSecondaryemail(temp);
            } else {
                user.setSecondaryemail("");
            }
        } else {
            user.setSecondaryemail(email);
        }

        userRepository.save(user);

        return new ApiResponse("Success Verifying Secondary Email Code", null, 200);
    }


    @Override
    public ApiResponse addSecondaryEmail(String userId, AddSecondaryEmailDTO secondaryEmailDTO) throws MessagingException, javax.mail.MessagingException {
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new InvalidUserVerifCodeException(
                    userId, "User not found!");
        }

        Users user = optionalUser.get();
        final String secondaryEmail = secondaryEmailDTO.getEmail();

        if (secondaryEmail == null || secondaryEmail.trim().isEmpty()) {
            throw new InvalidUserVerifCodeException(
                    userId, "Secondary email cannot be empty");
        }

        if (secondaryEmail.equals(user.getEmail())) {
            throw new InvalidUserVerifCodeException(
                    userId, "Secondary email cannot be the same as the primary email");
        }
        final UserVerifCode code = userVerifCodeRepository
                .save(
                        new UserVerifCode(
                                null,
                                authUtilsService.generateCode(),
                                secondaryEmail,
                                LocalDateTime.now().plusMinutes(15)
                        )
                );

        final List<TemplateVariable> variables = new ArrayList<>();
        variables.add(new TemplateVariable("code", code.getCode()));
        mailingService.sendEmail(secondaryEmail, "Secondary-Email OTP", "SecondaryEmail.html", variables);

        user.setUserVerifCode(code);
        userRepository.save(user);
        user.setSecondaryemail(secondaryEmail);

        return new ApiResponse("Success , Check your Email For Validation Code", null, 200);
    }

    @Override
    public ApiResponse DeleteSecondaryEmail(String userId, AddSecondaryEmailDTO secondaryEmailDTO)
            throws MessagingException, javax.mail.MessagingException {
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new InvalidUserVerifCodeException(
                    userId, "User not found!");
        }

        Users user = optionalUser.get();
        final String secondaryEmail = secondaryEmailDTO.getEmail();

        if (secondaryEmail == null || secondaryEmail.trim().isEmpty()) {
            throw new InvalidUserVerifCodeException(
                    userId, " email cannot be empty");
        }


        final UserVerifCode code = userVerifCodeRepository
                .save(
                        new UserVerifCode(
                                null,
                                authUtilsService.generateCode(),
                                secondaryEmail,
                                LocalDateTime.now().plusMinutes(15)
                        )
                );

        final List<TemplateVariable> variables = new ArrayList<>();
        variables.add(new TemplateVariable("code", code.getCode()));
        mailingService.sendEmail(secondaryEmail, "Secondary-Email OTP", "SecondaryEmail.html", variables);

        user.setUserVerifCode(code);
        userRepository.save(user);
        user.setSecondaryemail(secondaryEmail);

        return new ApiResponse("Success , Check your Email For Validation Code", null, 200);
    }


}
