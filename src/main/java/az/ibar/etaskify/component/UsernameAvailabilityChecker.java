package az.ibar.etaskify.component;

import az.ibar.etaskify.exception.BadRequestException;
import az.ibar.etaskify.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsernameAvailabilityChecker {

    @Autowired
    private UserRepository userRepository;

    public void check(String userName) {
        log.info("Checking if user is already registered with such username: {}", userName);
        if (userRepository.existsByUsername(userName)) {
            throw new BadRequestException("User is already registered with such username");
        }
    }

}
