package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundExaption;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@Component
public class UserController {

    private UserStorage userStorage;
    private UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userStorage.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getOnId(@PathVariable Long id) {
        chekId(id);
        return userStorage.get(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriendOnId(@PathVariable Long id) {
        chekId(id);
        return userService.getFriend(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ArrayList<User> generalFriend(@PathVariable Long id, @PathVariable Long otherId) {
        chekId(id);
        chekId(otherId);
        return userService.generalFriend(id, otherId);
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        checkInput(user);
        user.setId();
        userStorage.put(user);
        log.info("Добавлен пользователь");
        return user;
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) throws ValidationException, UserNotFoundExaption {
        chekId(user.getId());
        userStorage.put(user);
        log.info("Добавлен обновлен");
        return user;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        chekId(id);
        chekId(friendId);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        chekId(id);
        chekId(friendId);
        return userService.deleteFriend(id, friendId);
    }

    private void checkInput(User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Как живется в будущем ?");
            throw new ValidationException("Как живется в будущем ?");
        } else if (user.getLogin().contains(" ")) {
            log.warn("Логин содержит пробел");
            throw new ValidationException("Логин содержит пробел");
        }
    }

    private void chekId(Long id) {
        if (userStorage.get(id) == null) {
            throw new UserNotFoundExaption("нет юзера с таким ид");
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(final ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(final UserNotFoundExaption e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerError(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}