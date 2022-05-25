package ru.yandex.practicum.filmorate.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}