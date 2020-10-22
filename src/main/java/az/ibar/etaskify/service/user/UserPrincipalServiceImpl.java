package az.ibar.etaskify.service.user;

import az.ibar.etaskify.model.User;
import az.ibar.etaskify.repository.UserRepository;
import az.ibar.etaskify.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserPrincipalServiceImpl implements UserPrincipalService {

    private UserRepository userRepository;

    @Autowired
    public UserPrincipalServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser() {
        log.info("Getting user from user principal");
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userPrincipal.getId()).get();
    }

}
