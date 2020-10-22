package az.ibar.etaskify.service.task;

import az.ibar.etaskify.component.GivenUsersExistChecker;
import az.ibar.etaskify.dto.TaskDTO;
import az.ibar.etaskify.mapper.TaskDTOMapper;
import az.ibar.etaskify.mapper.TaskMapper;
import az.ibar.etaskify.model.Task;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.payload.TaskPayload;
import az.ibar.etaskify.repository.TaskRepository;
import az.ibar.etaskify.service.organization.OrganizationService;
import az.ibar.etaskify.service.user.UserPrincipalService;
import az.ibar.etaskify.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserService userService;
    private UserPrincipalService userPrincipalService;
    private OrganizationService organizationService;

    private GivenUsersExistChecker givenUsersExistChecker;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, UserPrincipalService userPrincipalService, OrganizationService organizationService, GivenUsersExistChecker givenUsersExistChecker) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userPrincipalService = userPrincipalService;
        this.organizationService = organizationService;
        this.givenUsersExistChecker = givenUsersExistChecker;
    }

    @Override
    public TaskDTO create(TaskPayload taskPayload) {
        log.info("Create task: {}", taskPayload);

        givenUsersExistChecker.check(taskPayload.getUserIds());

        Task task = TaskMapper.mapFromPayload(taskPayload);
        task.setOrganization(organizationService.getMyOrganization());
        taskRepository.save(task);
        userService.assignTaskToUsers(task, taskPayload.getUserIds());

        return TaskDTOMapper.mapFromTask(task);
    }

    @Override
    public List<TaskDTO> getMyTasks() {
        log.info("Getting my tasks");
        User user = userPrincipalService.getUser();
        return user.getTasks().stream()
                .map(TaskDTOMapper::mapFromTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getMyOrganizationTasks() {
        log.info("Getting my organization tasks");
        return taskRepository.findAllByOrganization(organizationService.getMyOrganization())
                .stream()
                .map(TaskDTOMapper::mapFromTask)
                .collect(Collectors.toList());
    }
}
