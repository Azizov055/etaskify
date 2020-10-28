package az.ibar.etaskify.service.task;

import az.ibar.etaskify.component.GivenUsersExistChecker;
import az.ibar.etaskify.dto.TaskDTO;
import az.ibar.etaskify.exception.ResourceNotFoundException;
import az.ibar.etaskify.model.Organization;
import az.ibar.etaskify.model.Task;
import az.ibar.etaskify.model.TaskStatus;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.payload.TaskPayload;
import az.ibar.etaskify.repository.TaskRepository;
import az.ibar.etaskify.service.organization.OrganizationService;
import az.ibar.etaskify.service.user.UserPrincipalService;
import az.ibar.etaskify.service.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private UserPrincipalService userPrincipalService;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private GivenUsersExistChecker givenUsersExistChecker;

    private TaskPayload taskPayload = new TaskPayload();
    private List<User> users = new ArrayList<>();
    private List<Organization> organizations = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Organization organization1 = new Organization();
        organization1.setId(1L);
        organization1.setName("Test 1 organization");
        organization1.setAddress("Test 1 address");
        organization1.setPhoneNumber("+994501111111");

        Organization organization2 = new Organization();
        organization2.setId(2L);
        organization2.setName("Test 2 organization");
        organization2.setAddress("Test 2 address");
        organization2.setPhoneNumber("+994502222222");

        organizations.add(organization1);
        organizations.add(organization2);

        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Task 1 description");
        task1.setOrganization(organization1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Task 2 description");
        task2.setOrganization(organization1);

        Task task3 = new Task();
        task3.setTitle("Task 3");
        task3.setDescription("Task 3 description");
        task3.setOrganization(organization1);

        Task task4 = new Task();
        task4.setTitle("Task 4");
        task4.setDescription("Task 4 description");
        task4.setOrganization(organization2);

        Task task5 = new Task();
        task5.setTitle("Task 5");
        task5.setDescription("Task 5 description");
        task5.setOrganization(organization2);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);

        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1 name");
        user1.setSurname("User 1 surname");
        user1.setOrganization(organization1);
        user1.setTasks(tasks.subList(0, 2));

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2 name");
        user2.setSurname("User 2 surname");
        user2.setOrganization(organization1);
        user2.setTasks(tasks.subList(2, 4));

        users.add(user1);
        users.add(user2);

        taskPayload.setTitle("Test task");
        taskPayload.setDescription("Test task description");
        taskPayload.setDeadline(LocalDateTime.now().plusDays(10));
        taskPayload.setUserIds(users.stream().map(u -> u.getId()).collect(Collectors.toList()));
    }

    @Test
    void create_success() {
        Mockito.doNothing().when(givenUsersExistChecker).check(
                users.stream().map(u -> u.getId()).collect(Collectors.toList()));

        Mockito.doReturn(organizations.get(0)).when(organizationService).getMyOrganization();
        Mockito.doNothing().when(userService).assignTaskToUsers(ArgumentMatchers.any(), ArgumentMatchers.any());

        TaskDTO taskDTO = taskService.create(taskPayload);
        Assert.assertNotNull(taskDTO);
        Assert.assertEquals(taskDTO.getTitle(), taskPayload.getTitle());
        Assert.assertEquals(taskDTO.getDescription(), taskPayload.getDescription());
        Assert.assertEquals(taskDTO.getDeadline(), taskPayload.getDeadline());
        Assert.assertEquals(TaskStatus.NEW, taskDTO.getStatus());
    }

    @Test
    void creationFailure_becauseOfGivenUsersNotExist() {
        Mockito.doThrow(ResourceNotFoundException.class).when(givenUsersExistChecker).check(
                users.stream().map(u -> u.getId()).collect(Collectors.toList())
        );

        Assert.assertThrows(ResourceNotFoundException.class, () -> taskService.create(taskPayload));
    }

    @Test
    void getMyTasks() {
        Mockito.doReturn(users.get(0)).when(userPrincipalService).getUser();
        List<TaskDTO> myTasks = taskService.getMyTasks();

        Assert.assertEquals(2, myTasks.size());
        Assert.assertEquals(tasks.get(0).getId(), myTasks.get(0).getId());
        Assert.assertEquals(tasks.get(1).getId(), myTasks.get(1).getId());
    }

    @Test
    void getMyOrganizationTasks() {
        Organization myOrganization = organizations.get(0);
        Mockito.doReturn(myOrganization).when(organizationService).getMyOrganization();
        Mockito.doReturn(tasks.stream().filter(t -> t.getOrganization().equals(myOrganization))
                    .collect(Collectors.toList()))
                .when(taskRepository).findAllByOrganization(myOrganization);

        List<TaskDTO> myOrganizationTasks = taskService.getMyOrganizationTasks();
        Assert.assertEquals(3, myOrganizationTasks.size());
        Assert.assertEquals(tasks.get(0).getId(), myOrganizationTasks.get(0).getId());
        Assert.assertEquals(tasks.get(1).getId(), myOrganizationTasks.get(1).getId());
        Assert.assertEquals(tasks.get(2).getId(), myOrganizationTasks.get(2).getId());
    }

}