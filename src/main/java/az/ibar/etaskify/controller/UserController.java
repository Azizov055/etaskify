package az.ibar.etaskify.controller;

import az.ibar.etaskify.payload.UserCreationPayload;
import az.ibar.etaskify.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUsersOfOrganization() {
        return ResponseEntity.ok(userService.getUsersOfMyOrganization());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreationPayload userCreationPayload) {
        userService.create(userCreationPayload);
        return ResponseEntity.ok("User created successfully!");
    }

}
