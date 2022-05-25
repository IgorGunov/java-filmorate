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
import java.util.Set;

@Slf4j
@RestController
@Component
public class UserController {

    @Autowired private UserStorage userStorage;
    @Autowired private UserService userService;

    @GetMapping("/users")
    public void getAll() {
        userStorage.getAllUser();
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public void getOnId(@PathVariable int id) {
        userStorage.get(id);
    }

    @GetMapping("/users/{id}/friends")
    public Set<User> getFriendOnId(@PathVariable int id) {
        return userService.getFriend(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> generalFriend(@PathVariable int id, @PathVariable int otherId) {
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
    public User update(@Valid @RequestBody User user) throws ValidationException {
        if (userStorage.getAllUser().containsKey(user.getId())) {
            checkInput(user);
            userStorage.put(user);
            log.info("Добавлен обновлен");
            return user;
        } else {
            log.info("нет пользователя с таким id");
            throw new UserNotFoundExaption("нет пользователя с таким id");
        }
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
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