package az.ibar.etaskify.mapper;

import az.ibar.etaskify.dto.TaskDTO;
import az.ibar.etaskify.model.Task;

public class TaskDTOMapper {

    public static TaskDTO mapFromTask(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getStatus()
        );
    }

}
