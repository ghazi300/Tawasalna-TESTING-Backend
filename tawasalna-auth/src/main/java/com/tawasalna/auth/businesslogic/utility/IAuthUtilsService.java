package com.tawasalna.auth.businesslogic.utility;

import com.tawasalna.auth.models.*;
import com.tawasalna.auth.payload.request.RegisterDTO;
import com.tawasalna.auth.payload.request.RegisterPmsDTO;
import com.tawasalna.shared.communityapi.model.Community;

import java.util.Set;

public interface IAuthUtilsService {
    String generateCode();

    String encodePwd(String newPwd);

    boolean comparePwd(String plain, String encoded);

    boolean verifyCodeValidity(UserVerifCode codeV);

    String generateRandomPassword();

    Users makeUser(
            RegisterDTO registerDTO,
            Set<Role> roles,
            UserVerifCode userVerifCode,
            BusinessProfile businessProfile,
            ResidentProfile residentProfile,
            ProspectProfile prospectProfile,
            AgentProfile agentProfile,
            BrokerProfile brokerProfile,
            AdminProfile adminProfile
    );

    Users makePmsUser(
            RegisterPmsDTO registerPmsDTO,
            Set<Role> roles,
            UserVerifCode userVerifCode,
            ProspectProfile prospectProfile,
            AgentProfile agentProfile,
            BrokerProfile brokerProfile,
            AdminProfile adminProfile,
            Community community
    );
}
