package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.ValidationException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@Component
class FilmControllerTest {

    @Autowired private FilmController filmController;

    @Test
    public void notCorrectDuration() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2020, 02, 02))
                .duration(0)
                .build();
        System.out.println(film);

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