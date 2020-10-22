package az.ibar.etaskify.mapper;

import az.ibar.etaskify.model.Task;
import az.ibar.etaskify.model.TaskStatus;
import az.ibar.etaskify.payload.TaskPayload;

public class TaskMapper {

    public static Task mapFromPayload(TaskPayload payload) {
        return new Task(
                payload.getTitle(),
                payload.getDescription(),
                payload.getDeadline(),
                TaskStatus.NEW
        );
    }

}
