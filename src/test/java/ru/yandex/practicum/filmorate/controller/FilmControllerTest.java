package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.ValidationException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private final FilmController filmController = new FilmController();

    @Test
    public void notCorrectDuration() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2020, 02, 02))
                .duration(0)
                .build();

        ValidationException ex = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Продолжительность фильма не может быть отрицательной", ex.getMessage());
    }

    @Test
    public void notCorrectData() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(800, 02, 02))
                .duration(100)
                .build();

        ValidationException ex = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Тогда еще не было фильмов", ex.getMessage());
    }

    @Test
    public void notCorrectDescription() {
        Film film = Film.builder()
                .name("name")
                .description("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" +
                        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr")
                .releaseDate(LocalDate.of(2020, 02, 02))
                .duration(100)
                .build();

        ValidationException ex = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Описание слишком велико", ex.getMessage());
    }
}