package az.ibar.etaskify.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginPayload {
    @NotBlank(message = "Username may not be blank")
    private String usernameOrEmail;

    @NotBlank(message = "Password may not be blank")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;
}
