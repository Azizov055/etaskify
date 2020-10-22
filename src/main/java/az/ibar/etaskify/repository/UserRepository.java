package az.ibar.etaskify.repository;

import az.ibar.etaskify.model.Organization;
import az.ibar.etaskify.model.RoleName;
import az.ibar.etaskify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findAllByOrganizationAndRoleIs(Organization organization, RoleName roleName);
}
