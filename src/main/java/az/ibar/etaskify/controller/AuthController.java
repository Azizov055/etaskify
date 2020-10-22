package az.ibar.etaskify.controller;

import az.ibar.etaskify.payload.LoginPayload;
import az.ibar.etaskify.payload.RegistrationPayload;
import az.ibar.etaskify.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationPayload payload) {
        authService.register(payload);
        return ResponseEntity.ok("Registration completed successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginPayload payload) {
        String token = authService.login(payload);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Authorization", token);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Logged in!");
    }
}
