package com.EventsApp.controller;

import com.EventsApp.manager.EventManager;
import com.EventsApp.model.Event;
import com.EventsApp.model.User;
import com.EventsApp.model.dto.EventDto;
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
@RequestMapping("/events")
public class EventController {
    private final EventManager eventManager;

    @Autowired
    public EventController(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Operation(summary = "Get all events", description = "Returns a list of all events.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of events",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Event.class)))
    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventManager.findAllEvents();
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Create a new event", description = "Creates a new event based on the provided event object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(
            @RequestBody(description = "Event object to be created", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)))
            @org.springframework.web.bind.annotation.RequestBody Event event) {
        Event newEvent = eventManager.createEvent(event);
        return ResponseEntity.ok(newEvent);
    }

    @Operation(summary = "Update an existing event", description = "Updates an existing event based on the provided event ID and event data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PatchMapping("/updateEvent/{id}")
    public ResponseEntity<Event> updateEvent(
            @Parameter(description = "ID of the event to be updated") @PathVariable Long id,
            @RequestBody(description = "Updated event data", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDto.class)))
            @org.springframework.web.bind.annotation.RequestBody EventDto eventDto) {
        Event event = eventManager.updateEvent(eventDto, id);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Get event by title", description = "Returns an event based on the provided title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<Event> getEventsByTitle(
            @Parameter(description = "Title of the event to be retrieved") @PathVariable String title) {
        Event events = eventManager.findEventByTitle(title);
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Get event by ID", description = "Returns an event based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<Event> getEventById(
            @Parameter(description = "ID of the event to be retrieved") @PathVariable Long id) {
        Event event = eventManager.findById(id);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Get subscribers of an event", description = "Returns a list of users subscribed to a specific event based on the event ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of subscribers",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/getEventSubscribers/{id}")
    public ResponseEntity<List<User>> getEventSubscribers(
            @Parameter(description = "ID of the event to get subscribers") @PathVariable Long id) {
        List<User> users = eventManager.getEventSubscribers(id);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Delete an event", description = "Deletes an event based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted event"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<Event> deleteEvent(
            @Parameter(description = "ID of the event to be deleted") @PathVariable("id") long id) {
        eventManager.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}

