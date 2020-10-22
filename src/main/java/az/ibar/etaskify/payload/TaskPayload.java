package az.ibar.etaskify.payload;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskPayload {
    @NotBlank(message = "Title may not be blank")
    private String title;

    private String description;

    @NotNull(message = "Deadline must be set")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime deadline;

    private List<Long> userIds;
}
