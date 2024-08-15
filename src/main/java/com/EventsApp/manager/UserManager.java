package com.EventsApp.manager;

import com.EventsApp.model.Event;
import com.EventsApp.model.User;
import com.EventsApp.model.dto.UserDto;
import com.EventsApp.repository.EventRepository;
import com.EventsApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManager {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws Exception {
        boolean ifUsernameExists = findAllUsers().stream().anyMatch(userFromList -> userFromList.getUsername().equals(user.getUsername()));
        if (ifUsernameExists) throw new Exception("Such user already exists ");

        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(UserDto userDto, Long id) {
        User oldUser = findUserById(id);
        User newUser = User.builder()
                .id(oldUser.getId())
                .firstName(userDto.getFirstName() != null ? userDto.getFirstName() : oldUser.getFirstName())
                .lastName(userDto.getLastName() != null ? userDto.getLastName() : oldUser.getLastName())
                .email(userDto.getEmail() != null ? userDto.getEmail() : oldUser.getEmail())
                .password(userDto.getPassword() != null ? userDto.getPassword() : oldUser.getPassword())
                .birthday(userDto.getBirthday() != null ? userDto.getBirthday() : oldUser.getBirthday())
                .username(userDto.getUsername() != null ? userDto.getUsername() : oldUser.getUsername()).build();

        return userRepository.save(newUser);
    }

    public void subscribeOnEvent(Long userId, Long eventId) {
        User user = findUserById(userId);
        Event event = eventRepository.findById(eventId).orElse(null);
        user.addEvent(event);
        userRepository.save(user);
    }

    public void unsubscribeOnEvent(Long userId, Long eventId) {
        User user = findUserById(userId);
        Event event = eventRepository.findById(eventId).orElse(null);
        user.removeEvent(event);
        userRepository.save(user);
    }

    public List<Event> getUserSubscribtions(Long id) {
        return new ArrayList<>(findUserById(id).getEvents());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
