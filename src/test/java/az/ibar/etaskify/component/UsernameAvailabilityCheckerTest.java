package az.ibar.etaskify.component;

import az.ibar.etaskify.exception.BadRequestException;
import az.ibar.etaskify.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UsernameAvailabilityCheckerTest {

    @Autowired
    private UsernameAvailabilityChecker usernameAvailabilityChecker;

    @MockBean
    private UserRepository userRepository;

    @Test
    void check_available() {
        Mockito.doReturn(false).when(userRepository).existsByUsername(ArgumentMatchers.anyString());
        usernameAvailabilityChecker.check(ArgumentMatchers.anyString());
    }

    @Test
    void check_notAvailable() {
        Mockito.doReturn(true).when(userRepository).existsByUsername(ArgumentMatchers.anyString());
        Assert.assertThrows(BadRequestException.class, () -> usernameAvailabilityChecker.check(ArgumentMatchers.anyString()));
    }
}