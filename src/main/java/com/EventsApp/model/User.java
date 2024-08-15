package com.EventsApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "events")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    @NotNull(message = "Must not be empty")
    @Size(min = 3, message = "Firstname must be at least 3 characters")
    @Size(max = 50, message = "Firstname must be no more than 50 characters")
    @Basic
    private String firstName;

    @Basic
    @Column(name = "lastname", nullable = false)
    @NotNull(message = "Must not be empty")
    @Size(min = 3, message = "Lastname must be at least 3 characters")
    @Size(max = 50, message = "Lastname must be no more than 50 characters")
    private String lastName;

    @Basic
    @Column(name = "email", nullable = false)
    @NotNull(message = "Fill the space")
    @Size(min = 5, message = "Use at least 3 characters")
    @Size(max = 50, message = "Use no more than 50 characters")
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    @NotNull(message = "Must not be empty ")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Size(max = 15, message = "Password must be no more than 15 characters")
    private String password;

    @Basic
    @Column(name = "username", nullable = false)
    @NotNull(message = "Must not be empty")
    @Size(min = 3, message = "Username must be at least 3 characters")
    @Size(max = 50, message = "Username must be no more than 50 characters")
    private String username;

    @Basic
    @Column(name = "birthday")
    @Past(message = "Birthday date must be in the past")
    private Date birthday;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    public void addEvent(Event event) {
        events.add(event);
        event.getUsers().add(this);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.getUsers().remove(this);
    }
}
