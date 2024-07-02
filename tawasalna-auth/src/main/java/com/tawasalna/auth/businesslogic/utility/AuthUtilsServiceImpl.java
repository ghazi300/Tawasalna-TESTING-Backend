package com.tawasalna.auth.businesslogic.utility;

import com.tawasalna.auth.models.*;
import com.tawasalna.auth.models.enums.AccountStatus;
import com.tawasalna.auth.payload.request.RegisterDTO;
import com.tawasalna.auth.payload.request.RegisterPmsDTO;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.communityapi.model.Community;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class AuthUtilsServiceImpl implements IAuthUtilsService {

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;
    private final Random rand = new Random();

    public String generateCode() {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            builder.append(rand.nextInt(10));
        }

        return builder.toString();
    }

    @Override
    public String encodePwd(String newPwd) {
        return encoder.encode(newPwd);
    }

    @Override
    public boolean comparePwd(String plain, String encoded) {
        return encoder.matches(plain, encoded);
    }

    @Override
    public boolean verifyCodeValidity(UserVerifCode codeV) {
        return codeV.getExpiredAt().isAfter(LocalDateTime.now());
    }

    @Override
    public String generateRandomPassword() {
        // Define the characters allowed in the random password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;:',<.>/?";

        // Generate a random password of length 12
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            password.append(characters.charAt(rand.nextInt(characters.length())));
        }

        return password.toString();

    }

    @Override
    public Users makeUser(
            RegisterDTO registerDTO,
            Set<Role> roles,
            UserVerifCode userVerifCode,
            BusinessProfile businessProfile,
            ResidentProfile residentProfile,
            ProspectProfile prospectProfile,
            AgentProfile agentProfile,
            BrokerProfile brokerProfile,
            AdminProfile adminProfile
    ) {
        return userRepository.save(
                new Users(
                        null,
                        registerDTO.getEmail(),
                        null,
                        "user" + rand.nextInt(100),
                        registerDTO.getAddress(),
                        this.encodePwd(registerDTO.getPassword()),
                        roles,
                        AccountStatus.DISABLED,
                        null,
                        null,
                        null,
                        userVerifCode,
                        businessProfile,
                        residentProfile,
                        prospectProfile,
                        agentProfile,
                        brokerProfile,
                        adminProfile,
                        true,
                        null
                )
        );

    }

    @Override
    public Users makePmsUser(
            RegisterPmsDTO registerPmsDTO,
            Set<Role> roles,
            UserVerifCode userVerifCode,
            ProspectProfile prospectProfile,
            AgentProfile agentProfile,
            BrokerProfile brokerProfile,
            AdminProfile adminProfile,
            Community community
    ) {
        return userRepository.save(
                new Users(
                        null,
                        registerPmsDTO.getEmail(),
                        null,
                        "user" + rand.nextInt(100),
                        null,
                        null,
                        roles,
                        AccountStatus.DISABLED,
                        null,
                        null,
                        null,
                        userVerifCode,
                        null,
                        null,
                        prospectProfile,
                        agentProfile,
                        brokerProfile,
                        adminProfile,
                        true,
                        community
                )
        );
    }
}
