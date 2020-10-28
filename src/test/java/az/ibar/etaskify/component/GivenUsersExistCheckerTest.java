package az.ibar.etaskify.component;

import az.ibar.etaskify.repository.UserRepository;
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
class GivenUsersExistCheckerTest {

    @Autowired
    private GivenUsersExistChecker givenUsersExistChecker;

    @MockBean
    private UserRepository userRepository;

    @Test
    void users_exist() {
        Mockito.doReturn(true).when(userRepository).existsById(ArgumentMatchers.anyLong());
        givenUsersExistChecker.check(ArgumentMatchers.anyList());
    }
}