package az.ibar.etaskify.repository;

import az.ibar.etaskify.model.Organization;
import az.ibar.etaskify.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrganization(Organization organization);
}
