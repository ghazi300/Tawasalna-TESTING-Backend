package com.tawasalna.auth.businesslogic.auth;

import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidRoleException;
import com.tawasalna.auth.exceptions.InvalidUserVerifCodeException;
import com.tawasalna.auth.models.*;
import com.tawasalna.auth.models.enums.*;
import com.tawasalna.auth.payload.request.LoginDTO;
import com.tawasalna.auth.payload.request.RegisterDTO;
import com.tawasalna.auth.payload.response.EmailVerifResp;
import com.tawasalna.auth.payload.response.JwtResponse;
import com.tawasalna.auth.payload.response.RefreshResp;
import com.tawasalna.auth.payload.response.RegisterResp;
import com.tawasalna.auth.repository.RoleRepository;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.auth.repository.UserSettingsRepository;
import com.tawasalna.auth.repository.UserVerifCodeRepository;
import com.tawasalna.auth.security.JwtUtils;
import com.tawasalna.auth.security.UserDetailsImpl;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.mail.IMailingService;
import com.tawasalna.shared.mail.TemplateVariable;
import com.tawasalna.shared.utils.Consts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;
    private final UserSettingsRepository userSettingsRepo;
    private final UserVerifCodeRepository userVerifCodeRepository;
    private final IAuthUtilsService authUtilsService;

    private final IMailingService mailingService;
    private final IFileManagerService fileManager;

    @Override
    public JwtResponse login(LoginDTO loginDTO) {

        final Users currentUser = userRepository
                .findByEmail(loginDTO.getEmail())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "user",
                                loginDTO.getEmail(),
                                Consts.USER_NOT_FOUND
                        )
                );

        if (currentUser.getAccountStatus() != AccountStatus.ENABLED)
            throw new InvalidUserException(
                    loginDTO.getEmail(),
                    "User account disabled"
            );


        try {
            final Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginDTO.getEmail(),
                                    loginDTO.getPassword()
                            )
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            final UserDetailsImpl userPrincipal =
                    (UserDetailsImpl) authentication.getPrincipal();

            final String jwt = jwtUtils.generateToken(userPrincipal.getEmail());
            final String refresh = jwtUtils.generateRefreshToken();
            final Boolean firstLoggedin = currentUser.getFirstLoggedIn();
            final List<String> roles = currentUser
                    .getRoles()
                    .stream()
                    .map(Role::getName)
                    .toList();

            return new JwtResponse(currentUser.getId(), jwt, refresh, roles, firstLoggedin);

        } catch (AuthenticationException e) {
            throw new InvalidUserException(
                    loginDTO.getEmail(),
                    "Invalid credentials"
            );
        }
    }

    @Override
    public RefreshResp refreshToken(String expired) {

        final String email = jwtUtils.getUsernameFromAccess(expired);

        if (userRepository.findByEmail(email).isEmpty())
            throw new InvalidUserException(email, Consts.USER_NOT_FOUND);

        final String newAccess = jwtUtils.generateToken(
                jwtUtils.getUsernameFromAccess(expired)
        );

        final String newRefresh = jwtUtils.generateRefreshToken();

        return new RefreshResp(newAccess, newRefresh);
    }

    @Override
    public ResponseEntity<Object> register(RegisterDTO registerDTO)
            throws MessagingException {

        final boolean existsByEmail = userRepository
                .existsByEmail(registerDTO.getEmail());

        if (existsByEmail)
            throw new InvalidUserException(registerDTO.getEmail(), "User already exists");

        final Set<Role> roles = new HashSet<>();

        final Role chosenRole = roleRepository
                .findById(registerDTO.getRole())
                .orElseThrow(() -> new InvalidRoleException(
                        registerDTO.getRole(), "Invalid role")
                );

        roles.add(chosenRole);

        final UserVerifCode userVerifCode = userVerifCodeRepository
                .save(
                        new UserVerifCode(
                                null,
                                authUtilsService.generateCode(),
                                registerDTO.getEmail(),
                                LocalDateTime.now().plusMinutes(15)
                        )
                );

        final Users user = authUtilsService.makeUser(
                registerDTO,
                roles,
                userVerifCode,
                chosenRole.getName().equals(RolesEnum.ROLE_BUSINESS.name()) ? new BusinessProfile(
                        registerDTO.getBusinessName(),
                        registerDTO.getProfessionalPhone(),
                        registerDTO.getProfessionalEmail(),
                        null,
                        registerDTO.getWebsite(),
                        registerDTO.getProfessionalEmail().substring(registerDTO.getProfessionalEmail().indexOf('@')+1),
                        registerDTO.getLinkedin(),
                        registerDTO.getCountry(),
                        registerDTO.getCity(),
                        registerDTO.getPostalCode(),
                        registerDTO.getMatriculate(),
                        null,
                        false
                ) : null,
                chosenRole.getName().equals(RolesEnum.ROLE_COMMUNITY_MEMBER.name()) ? new ResidentProfile(registerDTO.getResidentId(), registerDTO.getFullname(),"","",null, registerDTO.getDateOfBirth(),"",Gender.NONE,"","","", AccountType.PUBLIC,null,null) : null,
                null,
                null,
                null,
                null
        );

        userSettingsRepo.save(new UserSettings(null, AppTheme.LIGHT, "en-Us", true, new PrivacySettings(true, true), user));

        if (chosenRole.getName().equals(RolesEnum.ROLE_COMMUNITY_MEMBER.name())) {
            final List<TemplateVariable> variables = new ArrayList<>();
            variables.add(new TemplateVariable("code", userVerifCode.getCode()));
            mailingService.sendEmail(registerDTO.getEmail(), "Register", "RegisterEmailTemplate.html", variables);
        }
        if (chosenRole.getName().equals(RolesEnum.ROLE_BUSINESS.name())) {
            final List<TemplateVariable> variables = new ArrayList<>();
            variables.add(new TemplateVariable("code", userVerifCode.getCode()));
            mailingService.sendEmail(registerDTO.getEmail(), "Register", "RegisterEmailTemplate.html", variables);
        }
        return new ResponseEntity<>(
                chosenRole.getName().equals(RolesEnum.ROLE_COMMUNITY_MEMBER.name()) ?
                        new ApiResponse(
                                "Please check your inbox for your activation code.",
                                null,
                                201
                        )
                        : new RegisterResp(
                        user.getId(),
                        null
                ),
                HttpStatus.CREATED
        );
    }


    @Override
    public ApiResponse addLogoToBusiness(MultipartFile logo, String accId) throws ExecutionException, InterruptedException, MessagingException {

        final Users user = userRepository
                .findById(accId)
                .orElseThrow(() -> new InvalidUserException(accId, Consts.USER_NOT_FOUND));

        if (logo == null || logo.isEmpty()) {
            userRepository.delete(user);
            throw new InvalidEntityBaseException("file", "null", "invalid file");
        }
        final String fileName = fileManager.uploadToLocalFileSystem(logo, "business").get();

        if (fileName == null) {
            userRepository.delete(user);
            throw new InvalidEntityBaseException("file", "null", "no file name");
        }

        user.getBusinessProfile().setLogoName(fileName);
        userRepository.save(user);

        final List<TemplateVariable> variables = new ArrayList<>();

        UserVerifCode userVerifCode = user.getUserVerifCode();

        if (userVerifCode == null) {
            userVerifCode = userVerifCodeRepository.save(
                    new UserVerifCode(
                            null,
                            authUtilsService.generateCode(),
                            user.getEmail(),
                            LocalDateTime.now().plusMinutes(15)
                    )
            );
        }

        variables.add(new TemplateVariable("code", userVerifCode.getCode()));

        mailingService.sendEmail(user.getEmail(), "Register", "RegisterEmailTemplate.html", variables);

        return ApiResponse.ofSuccess("Please check your inbox for your activation code", 200);
    }


    @Override
    public ApiResponse verifyUserAccount(String email, String code) {

        final Users user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidUserException(
                        email, Consts.USER_NOT_FOUND)
                );

        final UserVerifCode codeV = userVerifCodeRepository
                .findByCodeAndEmail(code, email)
                .orElseThrow(() ->
                        new InvalidUserVerifCodeException(
                                code,
                                Consts.INVALID_CODE)
                );

        if (!authUtilsService.verifyCodeValidity(codeV))
            throw new InvalidUserVerifCodeException(
                    code, Consts.INVALID_CODE
            );

        user.setAccountStatus(AccountStatus.ENABLED);
        user.setUserVerifCode(null);

        userRepository.save(user);

        userVerifCodeRepository.delete(codeV);

        return new ApiResponse(
                "Account successfully activated!",
                null,
                200
        );
    }

    @Override
    public ApiResponse forgotPasswordRequest(String email) throws MessagingException {

        final Users user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidUserException(email, Consts.USER_NOT_FOUND));

        final UserVerifCode code = userVerifCodeRepository
                .save(
                        new UserVerifCode(
                                null,
                                authUtilsService.generateCode(),
                                email,
                                LocalDateTime.now().plusMinutes(15)
                        )
                );

        user.setUserVerifCode(code);

        userRepository.save(user);

        final List<TemplateVariable> variables = new ArrayList<>();

        variables.add(new TemplateVariable("code", code.getCode()));

        mailingService.sendEmail(email, "Forgot Password OTP", "ForgotPasswordEmailTemplate.html", variables);

        return new ApiResponse("Check your inbox for your code", null, 200);
    }

    @Override
    public ApiResponse updateCode(String email, String code)
            throws MessagingException {

        final Users user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new InvalidUserException(
                                email, Consts.USER_NOT_FOUND
                        )
                );

        final UserVerifCode codeV = userVerifCodeRepository
                .findByCodeAndEmail(code, email)
                .orElseGet(UserVerifCode::new);

        codeV.setEmail(email);
        codeV.setCode(authUtilsService.generateCode());
        codeV.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        user.setUserVerifCode(userVerifCodeRepository.save(codeV));
        userRepository.save(user);

        final List<TemplateVariable> variables = new ArrayList<>();
        variables.add(new TemplateVariable("code", codeV.getCode()));

        mailingService
                .sendEmail(
                        email,
                        "Forgot Password OTP",
                        "ResendCodeTemplate.html",
                        variables
                );

        return new ApiResponse(Consts.SUCCESS, null, 200);
    }

    @Override
    public ApiResponse verifyResetPasswordCode(String email, String code) {

        final Optional<UserVerifCode> found = userVerifCodeRepository
                .findByCodeAndEmail(code, email);

        if (found.isEmpty()) throw new InvalidUserVerifCodeException(
                code, Consts.INVALID_CODE
        );

        if (!authUtilsService.verifyCodeValidity(found.get()))
            throw new InvalidUserVerifCodeException(code, "Code Expired");

        return new ApiResponse("Success", null, 200);
    }

    @Override
    public ApiResponse updatePassword(String email, String newPassword, String code) throws MessagingException {

        final UserVerifCode codeV = userVerifCodeRepository
                .findByCodeAndEmail(code, email)
                .orElseThrow(() ->
                        new InvalidUserVerifCodeException(
                                code,
                                Consts.INVALID_CODE
                        )
                );

        final Users user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new InvalidUserException(
                                email,
                                Consts.USER_NOT_FOUND
                        )
                );

        if (!user.getAccountStatus().equals(AccountStatus.ENABLED))
            throw new InvalidUserException(email, "Invalid account");

        if (authUtilsService.comparePwd(newPassword, user.getPassword()))
            throw new InvalidUserException(
                    email,
                    "You've already used this password."
            );

        userVerifCodeRepository.delete(codeV);

        user.setUserVerifCode(null);

        user.setPassword(authUtilsService.encodePwd(newPassword));

        userRepository.save(user);

        mailingService
                .sendEmail(
                        email,
                        "Password Updated",
                        "ResetPassword.html",
                        new ArrayList<>()
                );

        return new ApiResponse("Success", null, 200);
    }

    @Override
    public ResponseEntity<EmailVerifResp> existsByEmail(String email) {
        final boolean existsByEmail = userRepository
                .existsByEmail(email);

        if (existsByEmail)
            return ResponseEntity.badRequest().body(new EmailVerifResp(false));

        return ResponseEntity.ok(new EmailVerifResp(true));
    }

    public String getPasswordByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
        return user.getPassword();
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository
                .findById(userId)
                .ifPresentOrElse(user -> {
                    userRepository.delete(user);
                    log.info("User with ID {} has been deleted successfully.", userId);
                }, () -> {
                    log.warn("User with ID {} not found.", userId);
                    throw new RuntimeException("User not found with ID: " + userId);
                });
    }

    @PostConstruct
    public void createSuperAdminUser() {
        final String randomPassword = "superadmin";

        // Check if a super admin user already exists
        final String mail = "superadmin@gmail.com";

        if (userRepository.existsByUsername("superadmin")) {
            log.debug("Super admin user already exists.");

            final Users users = userRepository
                    .findByEmail(mail)
                    .orElseThrow(() ->
                            new InvalidUserException(mail,
                                    Consts.USER_NOT_FOUND
                            ));

            users.setPassword(authUtilsService.encodePwd(randomPassword));
            users.setAccountStatus(AccountStatus.ENABLED);
            userRepository.save(users);

            System.out.println("Super Admin user created:");
            System.out.println("email: " + mail);
            System.out.println("Password: " + randomPassword);
            return;
        }

        // Create the superadmin role if it doesn't exist
        final Role superAdminRole = roleRepository.findByName(RolesEnum.ROLE_SUPER_ADMIN.name())
                .orElseGet(() -> roleRepository.save(new Role(null, RolesEnum.ROLE_SUPER_ADMIN.name())));

        // Create the superadmin user
        Users superAdminUser = new Users();
        superAdminUser.setUsername("superadmin");
        superAdminUser.setEmail(mail);
        superAdminUser.setPassword(authUtilsService.encodePwd(randomPassword));
        superAdminUser.setRoles(new HashSet<>(Set.of(superAdminRole)));
        superAdminUser.setAccountStatus(AccountStatus.ENABLED);

        // Save the superadmin user to the database
        userRepository.save(superAdminUser);

        // Print the username and password to the console
        System.out.println("Super admin user created:");
        System.out.println("email: " + superAdminUser.getEmail());
        System.out.println("Password: " + superAdminUser.getPassword());
    }
}