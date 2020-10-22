package az.ibar.etaskify.service.auth;

import az.ibar.etaskify.component.EmailAvailabilityChecker;
import az.ibar.etaskify.component.UsernameAvailabilityChecker;
import az.ibar.etaskify.mapper.UserMapper;
import az.ibar.etaskify.model.Organization;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.payload.LoginPayload;
import az.ibar.etaskify.payload.RegistrationPayload;
import az.ibar.etaskify.repository.UserRepository;
import az.ibar.etaskify.security.JwtTokenProvider;
import az.ibar.etaskify.service.organization.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private OrganizationService organizationService;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;
    private UsernameAvailabilityChecker usernameAvailabilityChecker;
    private EmailAvailabilityChecker emailAvailabilityChecker;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, OrganizationService organizationService, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager, UsernameAvailabilityChecker usernameAvailabilityChecker, EmailAvailabilityChecker emailAvailabilityChecker, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.usernameAvailabilityChecker = usernameAvailabilityChecker;
        this.emailAvailabilityChecker = emailAvailabilityChecker;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegistrationPayload payload) {
        usernameAvailabilityChecker.check(payload.getUsername());
        emailAvailabilityChecker.check(payload.getEmail());

        Organization organization = organizationService.create(
                new Organization(payload.getOrganization(), payload.getPhoneNumber(), payload.getAddress()));

        User user = UserMapper.mapUserFromRegistrationPayload(payload);
        user.setOrganization(organization);
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String login(LoginPayload payload) {
        log.info("Sign in by username or email: {}", payload.getUsernameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.getUsernameOrEmail(),
                        payload.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }
}
