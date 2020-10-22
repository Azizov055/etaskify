package az.ibar.etaskify.component;

import az.ibar.etaskify.exception.BadRequestException;
import az.ibar.etaskify.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailAvailabilityChecker {

    @Autowired
    private UserRepository userRepository;

    public void check(String email) {
        log.info("Checking if user is already registered with such email: {}", email);
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("User is already registered with such email");
        }
    }

}
