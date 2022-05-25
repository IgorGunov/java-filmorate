package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.Map;

public interface FilmStorage {

    public Map<Integer, Film> getAllFilm();

    public void put(Film film);

    public Film get(int id);
}
