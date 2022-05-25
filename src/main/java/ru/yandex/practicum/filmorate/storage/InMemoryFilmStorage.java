package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Map<Integer, Film> getAllFilm() {
        return films;
    }

    @Override
    public void put(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public Film get(int id) {
        return getAllFilm().getOrDefault(id, null);
    }
}
