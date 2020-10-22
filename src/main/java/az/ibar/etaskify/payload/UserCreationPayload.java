package az.ibar.etaskify.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserCreationPayload {
    @NotBlank(message = "Username may not be blank")
    private String username;

    @NotBlank(message = "Email may not be blank")
    private String email;

    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "Surname may not be blank")
    private String surname;

    @NotBlank(message = "Password may not be blank")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;
}
