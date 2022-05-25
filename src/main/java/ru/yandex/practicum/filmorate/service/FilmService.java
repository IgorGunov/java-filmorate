package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final TreeSet<Film> sortFilms = new TreeSet<Film>(new Comparator<Film>() {

        public int compare(Film o1, Film o2) {
            return o2.getLikes().size() - o1.getLikes().size();
        }
    });

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(int film, int user) {
        filmStorage.getAllFilm().get(film).addLikes(user);
    }

    public void deleteLike(int film, int user) {
        filmStorage.getAllFilm().get(film).deleteLikes(user);
    }

    public TreeSet<Film> topTen(int count) {
        sortFilms.addAll(filmStorage.getAllFilm().values());

        if (count == 0) {
            sortFilms.stream().limit(10);
        } else {
            sortFilms.stream().limit(count);
        }
        return sortFilms;
    }
}
