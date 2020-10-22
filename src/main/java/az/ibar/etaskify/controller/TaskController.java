package az.ibar.etaskify.controller;

import az.ibar.etaskify.payload.TaskPayload;
import az.ibar.etaskify.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody TaskPayload taskPayload) {
        return ResponseEntity.ok(taskService.create(taskPayload));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMyOrganizationTasks() {
        return ResponseEntity.ok(taskService.getMyOrganizationTasks());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getMyTasks() {
        return ResponseEntity.ok(taskService.getMyTasks());
    }

}