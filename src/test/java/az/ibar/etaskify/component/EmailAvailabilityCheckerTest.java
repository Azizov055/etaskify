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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmailAvailabilityCheckerTest {

    @Autowired
    private EmailAvailabilityChecker emailAvailabilityChecker;

    @MockBean
    private UserRepository userRepository;

    @Test
    void check_available() {
        Mockito.doReturn(false).when(userRepository).existsByEmail(ArgumentMatchers.anyString());
        emailAvailabilityChecker.check(ArgumentMatchers.anyString());
    }

    @Test
    void check_notAvailable() {
        Mockito.doReturn(true).when(userRepository).existsByEmail(ArgumentMatchers.anyString());
        Assert.assertThrows(BadRequestException.class, () -> emailAvailabilityChecker.check(ArgumentMatchers.anyString()));
    }
}