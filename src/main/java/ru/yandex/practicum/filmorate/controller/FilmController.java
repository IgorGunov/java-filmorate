package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundExaption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.TreeSet;

@Slf4j
@RestController
@Component
public class FilmController {

    private static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, 12, 28);

    @Autowired private FilmStorage filmStorage;
    @Autowired private FilmService filmService;


    @GetMapping("/films")
    public void getAll() {
        filmStorage.getAllFilm();
    }

    @GetMapping("/films/{id}")
    public void getOnId(@PathVariable int id) {
        filmStorage.get(id);
    }

    @GetMapping("/films/popular?count={count}")
    public TreeSet<Film> getTopTen(@PathVariable int count) {
        return filmService.topTen(count);
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        checkInput(film);
        film.setId();
        filmStorage.put(film);
        log.info("Добавлен фильм ");
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid  @RequestBody Film film) throws ValidationException {
        if (filmStorage.getAllFilm().containsKey(film.getId())) {
            checkInput(film);
            filmStorage.put(film);
            log.info("Добавлен фильм");
            return film;
        } else {
            throw new FilmNotFoundExaption("нет фильма с таким id");
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteId(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerError(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}