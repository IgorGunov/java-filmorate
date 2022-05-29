package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> getAllFilm() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void put(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public Film get(int id) {
        for (Film film: getAllFilm()) {
            if (film.getId() == id) {
                return film;
            }
        };
        return null;
    }

    @Override
    public Film update(Film film) {
        return films.put(film.getId(), film);
    }
}
