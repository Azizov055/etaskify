package az.ibar.etaskify.dto;

import az.ibar.etaskify.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
}
