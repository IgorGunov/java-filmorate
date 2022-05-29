package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.ValidationException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@Component
class UserControllerTest {

    @Autowired private UserController userController;

    @Test
    public void notCorrectEmail() {
        User user = User.builder()
                .email("igor@yandex.ru")
                .birthday(LocalDate.of(2023,05,30))
                .name("UserName")
                .login("userLogin")
                .build();

        ValidationException ex = assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals("Как живется в будущем ?", ex.getMessage());
    }

    @Test
    public void notCorrectLogin() {
        User user = User.builder()
                .email("igor@yandex.ru")
                .birthday(LocalDate.of(2002,05,30))
                .name("UserName")
                .login("user Login")
                .build();

        ValidationException ex = assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals("Логин содержит пробел", ex.getMessage());
    }
}