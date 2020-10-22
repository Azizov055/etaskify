package az.ibar.etaskify.component;

import az.ibar.etaskify.exception.ResourceNotFoundException;
import az.ibar.etaskify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GivenUsersExistChecker {

    @Autowired
    private UserRepository userRepository;

    public void check(List<Long> userIds) {
        for (long userId : userIds) {
            if (!userRepository.existsById(userId)) {
                throw new ResourceNotFoundException("User not found with such id: " + userId);
            }
        }
    }

}
