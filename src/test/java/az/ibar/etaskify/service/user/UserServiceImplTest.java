package az.ibar.etaskify.service.user;

import az.ibar.etaskify.component.EmailAvailabilityChecker;
import az.ibar.etaskify.component.UsernameAvailabilityChecker;
import az.ibar.etaskify.dto.UserDTO;
import az.ibar.etaskify.exception.BadRequestException;
import az.ibar.etaskify.mapper.UserMapper;
import az.ibar.etaskify.model.Organization;
import az.ibar.etaskify.model.RoleName;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.payload.UserCreationPayload;
import az.ibar.etaskify.repository.UserRepository;
import az.ibar.etaskify.service.email.EmailService;
import az.ibar.etaskify.service.organization.OrganizationService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private UsernameAvailabilityChecker usernameAvailabilityChecker;

    @MockBean
    private EmailAvailabilityChecker emailAvailabilityChecker;

    private Organization organization = new Organization();
    private UserCreationPayload userCreationPayload = new UserCreationPayload();
    private List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        organization = new Organization();
        organization.setId(1L);
        organization.setName("Test 1 organization");
        organization.setAddress("Test 1 address");
        organization.setPhoneNumber("+994501111111");

        userCreationPayload.setName("New user name");
        userCreationPayload.setUsername("New user surname");
        userCreationPayload.setUsername("new_user");
        userCreationPayload.setEmail("new_user@test.com");
        userCreationPayload.setPassword("123456");

        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1 name");
        user1.setSurname("User 1 surname");
        user1.setOrganization(organization);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2 name");
        user2.setSurname("User 2 surname");
        user2.setOrganization(organization);

        users.add(user1);
        users.add(user2);
    }

    @Test
    void create_success() {
        Mockito.doNothing().when(emailAvailabilityChecker).check(userCreationPayload.getEmail());
        Mockito.doNothing().when(usernameAvailabilityChecker).check(userCreationPayload.getUsername());
        Mockito.doReturn(organization).when(organizationService).getMyOrganization();
        Mockito.doReturn(UserMapper.mapUserFromUserCreationPayload(userCreationPayload))
                .when(userRepository).save(ArgumentMatchers.any());

        User user = userService.create(userCreationPayload);

        Assert.assertNotNull(user);
        Assert.assertEquals(userCreationPayload.getEmail(), user.getEmail());
        Assert.assertEquals(userCreationPayload.getUsername(), user.getUsername());
        Assert.assertEquals(userCreationPayload.getName(), user.getName());
        Assert.assertEquals(userCreationPayload.getSurname(), user.getSurname());
        Assert.assertEquals(organization, user.getOrganization());
    }

    @Test
    void creationFail_becauseOfEmailIsAlreadyTaken() {
        Mockito.doThrow(BadRequestException.class).when(emailAvailabilityChecker).check(ArgumentMatchers.anyString());
        Assert.assertThrows(BadRequestException.class, () -> userService.create(userCreationPayload));
    }

    @Test
    void creationFail_becauseOfUsernameIsAlreadyTaken() {
        Mockito.doThrow(BadRequestException.class).when(usernameAvailabilityChecker).check(userCreationPayload.getUsername());
        Assert.assertThrows(BadRequestException.class, () -> userService.create(userCreationPayload));
    }

    @Test
    void getUsersOfMyOrganization() {
        Mockito.doReturn(organization).when(organizationService).getMyOrganization();
        Mockito.doReturn(users).when(userRepository).findAllByOrganizationAndRoleIs(organization, RoleName.ROLE_USER);

        List<UserDTO> myOrganizationUsers = userService.getUsersOfMyOrganization();
        Assert.assertEquals(2, myOrganizationUsers.size());
    }
}