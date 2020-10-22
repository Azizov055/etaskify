package az.ibar.etaskify.service.task;

import az.ibar.etaskify.dto.TaskDTO;
import az.ibar.etaskify.payload.TaskPayload;

import java.util.List;

public interface TaskService {
    TaskDTO create(TaskPayload taskPayload);
    List<TaskDTO> getMyTasks();
    List<TaskDTO> getMyOrganizationTasks();
}