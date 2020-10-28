package az.ibar.etaskify.component;

import az.ibar.etaskify.exception.ResourceNotFoundException;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.repository.UserRepository;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
class GivenUsersExistCheckerTest {

    @Autowired
    private GivenUsersExistChecker givenUsersExistChecker;

    @MockBean
    private UserRepository userRepository;

    private List<Long> userIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userIds.add(1L);
        userIds.add(2L);
        userIds.add(3L);
    }

    @Test
    void users_exist() {
        Mockito.doReturn(true).when(userRepository).existsById(ArgumentMatchers.anyLong());
        givenUsersExistChecker.check(userIds);
    }

    @Test
    void user_not_exist() {
        Mockito.doReturn(false).when(userRepository).existsById(ArgumentMatchers.anyLong());
        Assert.assertThrows(ResourceNotFoundException.class, () -> givenUsersExistChecker.check(userIds));
    }
}