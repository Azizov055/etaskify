package az.ibar.etaskify.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationPayload {
    @NotBlank(message = "Username may not be blank")
    private String username;

    @NotBlank(message = "Email may not be blank")
    private String email;

    @NotBlank(message = "Password may not be blank")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;

    @NotBlank(message = "Organization name may not be blank")
    private String organization;

    @NotBlank(message = "Phone number may not be blank")
    private String phoneNumber;

    @NotBlank(message = "Address may not be blank")
    private String address;
}