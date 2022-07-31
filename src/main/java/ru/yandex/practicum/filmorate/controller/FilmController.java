package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundExaption;
import ru.yandex.practicum.filmorate.exception.UserNotFoundExaption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDeserializer;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import com.fasterxml.jackson.databind.Module;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@Component
public class FilmController {

    private static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, 12, 28);

    private final FilmStorage filmStorage;
    private final FilmService filmService;
    private final UserStorage userStorage;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
        this.userStorage = userStorage;
    }

    @GetMapping("/films")
    public List<Film> getAll() {
        return filmStorage.getAllFilm();
    }

    @GetMapping("/films/{id}")
    public Film getOnId(@PathVariable int id) {
        chekId(id);
        return filmStorage.get(id);
    }

    @GetMapping("/films/popular")
    public Set<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Вывод топа фильмов");
        return filmService.topFilms(count);
    }

    @Bean
    public Module filmDeserializer() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Film.class, new FilmDeserializer());
        return module;
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
            checkInput(film);
            film.setId();
            filmStorage.put(film);
            log.info("Добавлен фильм");
            return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        chekId(film.getId());
        checkInput(film);
        filmStorage.update(film);
        log.info("Добавлен фильм");
        return film;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable Long userId) {
        chekId(id);
        userChekId(userId);
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteId(@PathVariable int id, @PathVariable Long userId) {
        chekId(id);
        userChekId(userId);
        return filmService.deleteLike(id, userId);
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

    private void chekId(int id) {
        if (filmStorage.get(id) == null) {
            throw new FilmNotFoundExaption("нет фильма с таким ид");
        }
    }

    private void userChekId(Long id) {
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
    public ErrorResponse notFound(final FilmNotFoundExaption e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(final UserNotFoundExaption e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerError(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}