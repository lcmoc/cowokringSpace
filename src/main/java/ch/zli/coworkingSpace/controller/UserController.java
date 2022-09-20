package ch.zli.coworkingSpace.controller;

import ch.zli.coworkingSpace.model.UserEntity;
import ch.zli.coworkingSpace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all Users",
            description = "Loads all user from database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.loadAll());
    }

    @Operation(
            summary = "Get one specific user by id",
            description = "Loads one specific user by id from database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<UserEntity>>
    getUser(@PathVariable Long id) {
        Optional<UserEntity> user = userService.loadOne(id);

        if (user.isPresent()) {
            System.out.println("Accessing single date, HTTP: 200");
            return ResponseEntity
                    .status(HttpStatus.OK)  // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(user);
        } else {
            System.out.println("Accessing single user, HTTP: 404");
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<UserEntity>
    addUser(@RequestBody UserEntity user) {
        System.out.println("booking created");

        userService.create(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @Operation(
            summary = "Delete an existing user",
            description = "Deletes one specific and existing user in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?>
    deleteUser(@PathVariable Long id) {
        Optional<UserEntity> user = userService.loadOne(id);

        if (user.isPresent()) {
            System.out.println("removed user");
            userService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @Operation(
            summary = "Update an existing user",
            description = "Updates one specific and existing user in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserEntity>
    updateUser(@RequestBody UserEntity user) {

        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }
}
