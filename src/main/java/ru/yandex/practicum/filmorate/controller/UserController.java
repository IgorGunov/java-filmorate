package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public Map<Integer, User> getAll() {
        return users;
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        validation(user);
        user.setId();
        users.put(user.getId(), user);
        log.info("Добавлен пользователь");
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) throws ValidationException {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        validation(user);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь");
        return user;
    }

    public void validation(User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Как живется в будущем ?");
            throw new ValidationException("Как живется в будущем ?");
        } else if (user.getLogin().contains(" ")) {
            log.warn("Логин содержит пробел");
            throw new ValidationException("Логин содержит пробел");
        }
    }
}