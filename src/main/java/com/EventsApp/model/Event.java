package com.EventsApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "users")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "title", nullable = false)
    @NotNull(message = "Fill it ")
    private String title;

    @Column(name = "description")
    @Size(max = 100, message = "Use no more than 100 characters")
    @Basic
    private String description;

    @Column(name = "location", nullable = false)
    @NotNull(message = "fill the space")
    @Size(min = 5, message = "Use at least 3 characters")
    @Size(max = 50, message = "Use no more than 50 characters")
    @Basic
    private String location;

    @Column(name = "date", nullable = false)
    @NotNull(message = "fill the space")
    @Size(min = 2, message = "Use at least 2 characters")
    @Size(max = 30, message = "Use no more than 30 characters")
    @Future(message = "date must be in the future")
    @Basic
    private Date date;

    @Column(name = "time", nullable = false)
    @NotNull(message = "fill the space")
    @Size(min = 2, message = "Use at least 2 characters")
    @Size(max = 30, message = "Use no more than 30 characters")
    @Basic
    private Time time;

    @Column(name = "сategory")
    @Basic
    private String category;

    @ManyToMany(mappedBy = "events", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private Set<User> users = new HashSet<>();

}
