package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, 12, 28);

    @GetMapping("/films")
    public Map<Integer, Film> getAll() {
        return films;
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        checkInput(film);
        film.setId();
        films.put(film.getId(), film);
        log.info("Добавлен фильм ");
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid  @RequestBody Film film) throws ValidationException {
        checkInput(film);
        films.put(film.getId(), film);
        log.info("Добавлен фильм");
        return film;
    }

    private void checkInput(Film film) throws ValidationException {
        if (film.getDuration() <= 0) {
            log.warn("Произошла ошибка валидации при создании фильма");
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        } else if (film.getReleaseDate().isBefore(DATE_FIRST_FILM)) {
            log.warn("Произошла ошибка валидации при создании фильма");
            throw new ValidationException("Тогда еще не было фильмов");
        } else if (film.getDescription().length() > 200) {
            log.warn("Произошла ошибка валидации при создании фильма");
            throw new ValidationException("Описание слишком велико");
        }
    }
}