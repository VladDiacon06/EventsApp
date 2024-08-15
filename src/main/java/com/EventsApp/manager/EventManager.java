package com.EventsApp.manager;

import com.EventsApp.model.Event;
import com.EventsApp.model.User;
import com.EventsApp.model.dto.EventDto;
import com.EventsApp.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventManager {

    private final EventRepository eventRepository;

    @Autowired
    public EventManager(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Event findEventByTitle(String title) {
        Event event = eventRepository.findByTitle(title);
        if (event == null) {
            System.out.println("Event by title not found");

        }
        return event;
    }

    public List<Event> findEventByCategory(String category) {
        List<Event> events = eventRepository.findByCategory(category);
        if (events.isEmpty()) {
            System.out.println("Events with this category not found");
        }
        return events;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(EventDto eventDto, Long id) {
        Event oldEvent = findById(id);
        Event newEvent = Event.builder()
                .id(oldEvent.getId())
                .title(eventDto.getTitle() != null ? eventDto.getTitle() : oldEvent.getTitle())
                .description(eventDto.getDescription() != null ? eventDto.getDescription() : oldEvent.getDescription())
                .location(eventDto.getLocation() != null ? eventDto.getLocation() : oldEvent.getLocation())
                .time(eventDto.getTime() != null ? eventDto.getTime() : oldEvent.getTime())
                .date(eventDto.getDate() != null ? eventDto.getDate() : oldEvent.getDate())
                .category(eventDto.getCategory() != null ? eventDto.getCategory() : oldEvent.getCategory())
                .build();
        return eventRepository.save(newEvent);
    }

    public List<User> getEventSubscribers(Long id) {
        return new ArrayList<>(findById(id).getUsers());
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
