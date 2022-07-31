package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmStorage {

    public List<Film> getAllFilm();

    public void put(Film film);

    public Film get(int id);

    public void update(Film film);
}
