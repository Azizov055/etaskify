package az.ibar.etaskify.service.user;

import az.ibar.etaskify.component.EmailAvailabilityChecker;
import az.ibar.etaskify.component.GivenUsersExistChecker;
import az.ibar.etaskify.component.UsernameAvailabilityChecker;
import az.ibar.etaskify.dto.EmailMessage;
import az.ibar.etaskify.dto.UserDTO;
import az.ibar.etaskify.mapper.EmailMessageMapper;
import az.ibar.etaskify.mapper.UserDTOMapper;
import az.ibar.etaskify.mapper.UserMapper;
import az.ibar.etaskify.model.RoleName;
import az.ibar.etaskify.model.Task;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.payload.UserCreationPayload;
import az.ibar.etaskify.repository.UserRepository;
import az.ibar.etaskify.service.email.EmailService;
import az.ibar.etaskify.service.organization.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private OrganizationService organizationService;
    private EmailService emailService;

    private UsernameAvailabilityChecker usernameAvailabilityChecker;
    private EmailAvailabilityChecker emailAvailabilityChecker;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UsernameAvailabilityChecker usernameAvailabilityChecker, EmailAvailabilityChecker emailAvailabilityChecker, OrganizationService organizationService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.usernameAvailabilityChecker = usernameAvailabilityChecker;
        this.emailAvailabilityChecker = emailAvailabilityChecker;
        this.organizationService = organizationService;
        this.emailService = emailService;
    }

    @Override
    public User create(UserCreationPayload userCreationPayload) {
        log.info("Creating a user: {}", userCreationPayload);

        usernameAvailabilityChecker.check(userCreationPayload.getUsername());
        emailAvailabilityChecker.check(userCreationPayload.getEmail());

        User user = UserMapper.mapUserFromUserCreationPayload(userCreationPayload);
        user.setPassword(passwordEncoder.encode(userCreationPayload.getPassword()));
        user.setOrganization(organizationService.getMyOrganization());

        return userRepository.save(user);
    }

    @Override
    public void assignTaskToUsers(Task task, List<Long> userIds) {
        log.info("Assign task to users: {}", userIds);

        List<User> users = userRepository.findAllById(userIds);
        for (User user : users) {
            user.getTasks().add(task);
            userRepository.save(user);
        }

        log.info("Task assigned to users.");

        this.notifyUsersAboutNewTask(users, task);
    }

    @Override
    public List<UserDTO> getUsersOfMyOrganization() {
        log.info("Getting users of my organization");
        return userRepository.findAllByOrganizationAndRoleIs(
                    organizationService.getMyOrganization(),
                    RoleName.ROLE_USER)
                .stream()
                .map(UserDTOMapper::mapFromUser)
                .collect(Collectors.toList());
    }

    private void notifyUsersAboutNewTask(List<User> users, Task task) {
        for (User user : users) {
            log.info("Sending email to user: {}", user.getEmail());
            EmailMessage emailMessage = EmailMessageMapper.createForNotifyingNewTask(user.getEmail(), task);
            emailService.send(emailMessage);
        }
    }

}
