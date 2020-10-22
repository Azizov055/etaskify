package az.ibar.etaskify.service.auth;

import az.ibar.etaskify.payload.LoginPayload;
import az.ibar.etaskify.payload.RegistrationPayload;

public interface AuthService {
    void register(RegistrationPayload payload);
    String login(LoginPayload payload);
}
