package com.EventsApp.repository;

import com.EventsApp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByTitle(String title);

    List<Event> findByCategory(String category);
}