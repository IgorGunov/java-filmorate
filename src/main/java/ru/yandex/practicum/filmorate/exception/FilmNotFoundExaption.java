package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundExaption extends RuntimeException {
    public FilmNotFoundExaption(String message) {
        super(message);
    }
}
