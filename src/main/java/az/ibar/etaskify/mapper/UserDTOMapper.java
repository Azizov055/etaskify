package az.ibar.etaskify.mapper;

import az.ibar.etaskify.dto.UserDTO;
import az.ibar.etaskify.model.User;

public class UserDTOMapper {

    public static UserDTO mapFromUser(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname()
        );
    }

}
