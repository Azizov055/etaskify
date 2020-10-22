package az.ibar.etaskify.service.user;

import az.ibar.etaskify.dto.UserDTO;
import az.ibar.etaskify.model.Task;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.payload.UserCreationPayload;

import java.util.List;

public interface UserService {
    User create(UserCreationPayload userCreationPayload);
    void assignTaskToUsers(Task task, List<Long> userIds);
    List<UserDTO> getUsersOfMyOrganization();
}