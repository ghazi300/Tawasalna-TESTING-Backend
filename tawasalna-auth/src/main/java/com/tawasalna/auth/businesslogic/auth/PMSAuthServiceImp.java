package com.tawasalna.auth.businesslogic.auth;

import com.tawasalna.auth.businesslogic.staffsignuprequest.IstaffSignUpReqService;
import com.tawasalna.auth.businesslogic.usermanagement.UserManagementService;
import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidRoleException;
import com.tawasalna.auth.models.*;
import com.tawasalna.auth.models.enums.AccountStatus;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import com.tawasalna.auth.models.enums.RolesEnum;
import com.tawasalna.auth.models.enums.StaffSignupStatus;
import com.tawasalna.auth.payload.request.AssistanceDTO;
import com.tawasalna.auth.payload.request.RegisterPmsDTO;
import com.tawasalna.auth.payload.request.StaffSignupRequestDTO;
import com.tawasalna.auth.payload.response.RegisterResp;
import com.tawasalna.auth.repository.*;
import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.mail.IMailingService;
import com.tawasalna.shared.mail.TemplateVariable;
import com.tawasalna.shared.utils.Consts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PMSAuthServiceImp implements IPMSAuth {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserSettingsRepository userSettingsRepo;
    private final IAuthUtilsService authUtilsService;
    private final AssistanceRepository assistanceRepository;
    private final IMailingService mailingService;
    private final CommunityRepository communityRepository;
    private final UserManagementService userManagementService;
    private final IstaffSignUpReqService istaffSignUpReqService;
    private final StaffSignupRequestRepo staffSignupRequestRepo;

    String htmlLink = "http://localhost:4200/agent/prospect-requests";
    String htmlLink2 = "http://localhost:4200/pmsAdmin/inquiries";


    @Override
    public RegisterResp CreateAccount(RegisterPmsDTO registerPmsDTO) throws MessagingException {
        String htmlLink = "http://localhost:4200/authentification/login";

        if (userRepository.existsByEmail(registerPmsDTO.getEmail()))
            throw new InvalidUserException(registerPmsDTO.getRole(), "User already exists");

        final Set<Role> roles = new HashSet<>();

        final Role chosenRole = roleRepository
                .findById(registerPmsDTO.getRole())
                .orElseThrow(() -> new InvalidRoleException(registerPmsDTO.getRole(), "Invalid role"));
        if (chosenRole == null || chosenRole.getName() == null) {
            throw new InvalidRoleException(registerPmsDTO.getRole(), "Role name is null");
        }
        roles.add(chosenRole);

        if (registerPmsDTO.getCommunityId() == null) {
            throw new IllegalArgumentException("Community ID cannot be null");
        }
        final Community community = communityRepository
                .findById(registerPmsDTO.getCommunityId())
                .orElseThrow(() -> new EntityNotFoundException("community", registerPmsDTO.getCommunityId(), "Community not found"));

        final Users user = authUtilsService.makePmsUser(registerPmsDTO,
                roles,
                null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_PROSPECT.name()) ? new ProspectProfile(null, null, null, null, null, null, null, null) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_AGENT.name()) ? new AgentProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), null, null, null, null, null, null, null, null) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_BROKER.name()) ? new BrokerProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), null, null, null, null, registerPmsDTO.getProfessionalEmail(), registerPmsDTO.getProfessionalPhone(), null, null,null) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_ADMIN.name()) ? new AdminProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), registerPmsDTO.getProfessionalEmail(), registerPmsDTO.getProfessionalPhone(), null, null, null, null, null) : null,
                community);

        user.setAccountStatus(AccountStatus.ENABLED);
        user.setFirstLoggedIn(true);

        user.setPassword(authUtilsService.encodePwd(registerPmsDTO.getPassword()));
        userRepository.save(user);
        userSettingsRepo.save(new UserSettings(null, null, "en-Us", true, new PrivacySettings(true, true), user));

        final List<TemplateVariable> variables = new ArrayList<>();

        variables.add(new TemplateVariable("password", registerPmsDTO.getPassword()));
        variables.add(new TemplateVariable("htmlLink", htmlLink));
        mailingService.sendEmail(registerPmsDTO.getEmail(), "Register", "AccountCredentials.html", variables);

        return new RegisterResp(user.getId(), null);
    }

    @Override
    public RegisterResp SignupRequest(RegisterPmsDTO registerPmsDTO) throws MessagingException {

        if (userRepository.existsByEmail(registerPmsDTO.getEmail()))
            throw new InvalidUserException(registerPmsDTO.getRole(), "User already exists");

        final Set<Role> roles = new HashSet<>();

        final Role chosenRole = roleRepository
                .findById(registerPmsDTO.getRole())
                .orElseThrow(() -> new InvalidRoleException(registerPmsDTO.getRole(), "Invalid role"));
        roles.add(chosenRole);

        final Users user = authUtilsService.makePmsUser(
                registerPmsDTO,
                roles,
                null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_PROSPECT.name()) ? new ProspectProfile(
                        registerPmsDTO.getAssistentMail(),
                        registerPmsDTO.getFirstname(),
                        registerPmsDTO.getLastname(),
                        null, null,
                        null,
                        null,
                        null) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_AGENT.name()) ? new AgentProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), null, null, null, null, registerPmsDTO.getProfessionalEmail(), registerPmsDTO.getProfessionalPhone(), null,null) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_BROKER.name()) ? new BrokerProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), null, null, null, null, registerPmsDTO.getProfessionalEmail(), registerPmsDTO.getProfessionalPhone(), null, null,null) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_ADMIN.name()) ? new AdminProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), registerPmsDTO.getProfessionalEmail(), registerPmsDTO.getProfessionalPhone(), null, null, null, null, null) : null,
                null) ;


        user.setAccountStatus(AccountStatus.ENABLED);
        user.setFirstLoggedIn(true);
        userRepository.save(user);
        userSettingsRepo.save(new UserSettings(null, null, "en-Us", true, new PrivacySettings(true, true), user));

        final List<TemplateVariable> variables = new ArrayList<>();

        variables.add(new TemplateVariable("firstname", registerPmsDTO.getFirstname()));
        variables.add(new TemplateVariable("lastname", registerPmsDTO.getLastname()));
        variables.add(new TemplateVariable("email", registerPmsDTO.getEmail()));

        Optional<Users> optionalAssistantUser = userRepository.findByEmail(registerPmsDTO.getAssistentMail());

        if (optionalAssistantUser.isEmpty()) {
            throw new EntityNotFoundException("assistant", registerPmsDTO.getAssistentMail(), Consts.USER_NOT_FOUND);
        }

        final Users assistantUser = optionalAssistantUser.get();
        final Set<Role> userRoles = assistantUser.getRoles();

        if (userRoles.isEmpty()) {
            log.info("L'utilisateur avec l'email " + registerPmsDTO.getAssistentMail() + " n'a pas été trouvé.");
            throw new EntityNotFoundException("role", "empty roles", "roles empty");
        }

        final Role role = userRoles.iterator().next();
        final String roleName = role.getName();

        if (RolesEnum.ROLE_PMS_AGENT.name().equals(roleName) || RolesEnum.ROLE_PMS_BROKER.name().equals(roleName)) {

            AssistanceDTO assistanceDTO = null;

            if (RolesEnum.ROLE_PMS_AGENT.name().equals(roleName)) {
                assistanceDTO = new AssistanceDTO(null, assistantUser, null, user, null, AssistanceStatus.WAITING, new Date(), null, null);
                htmlLink = "http://localhost:4200/agent/prospect-requests";

            } else if (RolesEnum.ROLE_PMS_BROKER.name().equals(roleName)) {
                assistanceDTO = new AssistanceDTO(null, null, assistantUser, user, null, AssistanceStatus.WAITING, new Date(), null, null);
                htmlLink = "http://localhost:4200/broker/prospect-requests";
            }

            else if (RolesEnum.ROLE_PMS_ADMIN.name().equals(roleName)) {
                assistanceDTO = new AssistanceDTO(null, null, null, user, assistantUser, AssistanceStatus.WAITING, new Date(), null, null);
                htmlLink = "http://localhost:4200/admin/prospect-requests";
            }

            Assistance assistance = makeAssistance(assistanceDTO);
            assistanceRepository.save(assistance);
            variables.add(new TemplateVariable("htmlLink", htmlLink));

            mailingService.sendEmail(registerPmsDTO.getAssistentMail(), "New register request", "RegisterRequest.html", variables);
        }

        return new RegisterResp(user.getId(), null);
    }

    @Override
    public RegisterResp SignupStaff(RegisterPmsDTO registerPmsDTO) throws MessagingException {
        if (userRepository.existsByEmail(registerPmsDTO.getEmail()))
            throw new InvalidUserException(registerPmsDTO.getRole(), "User already exists");

        final Set<Role> roles = new HashSet<>();

        final Role chosenRole = roleRepository
                .findById(registerPmsDTO.getRole())
                .orElseThrow(() -> new InvalidRoleException(registerPmsDTO.getRole(), "Invalid role"));
        roles.add(chosenRole);

        final Community community= communityRepository
                .findById(registerPmsDTO.getCommunityId())
                .orElseThrow(() -> new EntityNotFoundException("community", registerPmsDTO.getCommunityId(), "not found"));

        final Users user = authUtilsService.makePmsUser(
                registerPmsDTO,
                roles,
                null,
                null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_AGENT.name()) ? new AgentProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), null, null, null, null, registerPmsDTO.getProfessionalEmail(), registerPmsDTO.getProfessionalPhone(),null, registerPmsDTO.getTelnumber()) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_PMS_BROKER.name()) ? new BrokerProfile(registerPmsDTO.getFirstname(), registerPmsDTO.getLastname(), null, null, null, null, registerPmsDTO.getProfessionalEmail(), registerPmsDTO.getProfessionalPhone(), null,null, registerPmsDTO.getTelnumber()) : null,
                null,
                community);

        user.setAccountStatus(AccountStatus.ENABLED);
        user.setFirstLoggedIn(true);

        userRepository.save(user);

        userSettingsRepo.save(new UserSettings(null, null, "en-Us", true, new PrivacySettings(true, true), user));

        final List<TemplateVariable> variables = new ArrayList<>();

        variables.add(new TemplateVariable("firstname", registerPmsDTO.getFirstname()));
        variables.add(new TemplateVariable("lastname", registerPmsDTO.getLastname()));
        variables.add(new TemplateVariable("email", registerPmsDTO.getEmail()));

        StaffSignupRequestDTO requestDTO= null;
        List<Users> admins= userManagementService.getPmsAdminsPerCommunity(community);
        List<String> adminEmails = new ArrayList<>();
        for (Users admin : admins) {
            adminEmails.add(admin.getEmail());
        }

        if(chosenRole.getName().equals(RolesEnum.ROLE_PMS_AGENT.name())){
            requestDTO = new StaffSignupRequestDTO(null,user,null,admins,new Date(), StaffSignupStatus.WAITING,"");
        }else if (chosenRole.getName().equals(RolesEnum.ROLE_PMS_BROKER.name())){
            requestDTO = new StaffSignupRequestDTO(null,null,user,admins,new Date(), StaffSignupStatus.WAITING,"");
        }
        StaffSignupRequest signupRequest = istaffSignUpReqService.makesignupRequest(requestDTO);
        staffSignupRequestRepo.save(signupRequest);
        variables.add(new TemplateVariable("htmlLink2", htmlLink2));

        if (!adminEmails.isEmpty()) {
            mailingService.sendEmailtoMany(adminEmails, "New staff register request", "StaffSignupRequest.html", variables);
        } else {
             log.warn("No admin emails found to notify about the new staff registration");
        }

        return new RegisterResp(user.getId(), null);
    }

    private Assistance makeAssistance(AssistanceDTO instance) {
        Assistance assistance = new Assistance(
                instance.getId(),
                instance.getAgentID(), // GET AUTO
                instance.getBrokerID(), //GET AUTO
                instance.getProspectID(), // GET AUTO
                AssistanceStatus.WAITING, // GET AUTO
                new Date(), //GET AUTO,
                instance.getDesiredDate(),
                instance.getDescription()
        );
        return assistanceRepository.save(assistance);
    }


    @Override
    public ApiResponse updatePassword(String email, String newPassword) {
        final Users user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidUserException(email, "User not found"));

        user.setFirstLoggedIn(false);

        user.setPassword(authUtilsService.encodePwd(newPassword));

        userRepository.save(user);

        return new ApiResponse("Success", null, 200);
    }
}
