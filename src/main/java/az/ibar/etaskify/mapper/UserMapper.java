package az.ibar.etaskify.mapper;

import az.ibar.etaskify.model.RoleName;
import az.ibar.etaskify.model.User;
import az.ibar.etaskify.payload.RegistrationPayload;
import az.ibar.etaskify.payload.UserCreationPayload;

public class UserMapper {

    public static User mapUserFromRegistrationPayload(RegistrationPayload payload) {
        return new User(
                payload.getUsername(),
                payload.getEmail(),
                RoleName.ROLE_ADMIN
        );
    }

    public static User mapUserFromUserCreationPayload(UserCreationPayload payload) {
        return new User(
                payload.getUsername(),
                payload.getEmail(),
                payload.getName(),
                payload.getSurname(),
                RoleName.ROLE_USER
        );
    }

}
