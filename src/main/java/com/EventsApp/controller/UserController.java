package com.EventsApp.controller;

import com.EventsApp.manager.UserManager;
import com.EventsApp.model.Event;
import com.EventsApp.model.User;
import com.EventsApp.model.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userManager.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create a new user", description = "Creates a new user based on the provided user object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(
            @RequestBody(description = "User object to be created", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)))
            @org.springframework.web.bind.annotation.RequestBody User user) throws Exception {
        User newUser = userManager.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Get user by ID", description = "Returns a user based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to be retrieved") @PathVariable long id) {
        User user = userManager.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user by username", description = "Returns a user based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<User> getUserByUsername(
            @Parameter(description = "Username of the user to be retrieved") @PathVariable String username) {
        User user = userManager.findUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update an existing user", description = "Updates an existing user based on the provided user ID and user data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID of the user to be updated") @PathVariable Long id,
            @RequestBody(description = "Updated user data", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)))
            @org.springframework.web.bind.annotation.RequestBody UserDto userDto) {
        User user = userManager.updateUser(userDto, id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Subscribe user to an event", description = "Subscribes a user to a specific event based on the user ID and event ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully subscribed user to event"),
            @ApiResponse(responseCode = "404", description = "User or event not found")
    })
    @PostMapping("/subscribe/{user_id}/{event_id}")
    public ResponseEntity<String> subscribe(
            @Parameter(description = "ID of the user") @PathVariable Long user_id,
            @Parameter(description = "ID of the event") @PathVariable Long event_id) {
        userManager.subscribeOnEvent(user_id, event_id);
        return ResponseEntity.ok("Subscribed");
    }

    @Operation(summary = "Unsubscribe user from an event", description = "Unsubscribes a user from a specific event based on the user ID and event ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unsubscribed user from event"),
            @ApiResponse(responseCode = "404", description = "User or event not found")
    })
    @PatchMapping("/unSubscribe/{user_id}/{event_id}")
    public ResponseEntity<String> unSubscribe(
            @Parameter(description = "ID of the user") @PathVariable Long user_id,
            @Parameter(description = "ID of the event") @PathVariable Long event_id) {
        userManager.unsubscribeOnEvent(user_id, event_id);
        return ResponseEntity.ok("Unsubscribed");
    }

    @Operation(summary = "Get user subscriptions", description = "Returns a list of events the user is subscribed to based on the user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of events",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/getUserSubscritions/{id}")
    public ResponseEntity<List<Event>> getUserSubscritions(
            @Parameter(description = "ID of the user") @PathVariable Long id) {
        List<Event> events = userManager.getUserSubscribtions(id);
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(
            @Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        userManager.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
