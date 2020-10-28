package az.ibar.etaskify.service.organization;

import az.ibar.etaskify.model.Organization;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.repository.OrganizationRepository;
import az.ibar.etaskify.service.user.UserPrincipalService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrganizationServiceImplTest {

    @Autowired
    private OrganizationService organizationService;

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private UserPrincipalService userPrincipalService;

    private List<User> users = new ArrayList<>();
    private List<Organization> organizations = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Organization organization1 = new Organization();
        organization1.setId(1L);
        organization1.setName("Test 1 organization");
        organization1.setAddress("Test 1 address");
        organization1.setPhoneNumber("+994501111111");

        Organization organization2 = new Organization();
        organization2.setId(2L);
        organization2.setName("Test 2 organization");
        organization2.setAddress("Test 2 address");
        organization2.setPhoneNumber("+994502222222");

        organizations.add(organization1);
        organizations.add(organization2);

        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1 name");
        user1.setSurname("User 1 surname");
        user1.setOrganization(organization1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2 name");
        user2.setSurname("User 2 surname");
        user2.setOrganization(organization2);

        users.add(user1);
        users.add(user2);
    }

    @Test
    void getMyOrganization() {
        Mockito.doReturn(users.get(1)).when(userPrincipalService).getUser();
        Organization organization = organizationService.getMyOrganization();

        Assert.assertNotNull(organization);
        Assert.assertEquals(users.get(1).getOrganization(), organization);
    }

}