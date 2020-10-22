package az.ibar.etaskify.mapper;


import az.ibar.etaskify.dto.EmailMessage;
import az.ibar.etaskify.model.Task;

public class EmailMessageMapper {

    public static EmailMessage createForNotifyingNewTask(String email, Task task) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setEmail(email);
        emailMessage.setSubject("You are assigned a new task");
        emailMessage.setText(
                String.format("Task id: %d, title: %s, description: %s",
                        task.getId(), task.getTitle(), task.getDescription()));
        return emailMessage;
    }

}
