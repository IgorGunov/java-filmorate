package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundExaption extends RuntimeException {
    public UserNotFoundExaption(String message) {
        super(message);
    }
}
