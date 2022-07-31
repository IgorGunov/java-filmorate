package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addLike(int filmId, Long user) {
        Film film = filmStorage.get(filmId);
        Set<Long> likes = new HashSet<>(filmStorage.get(filmId).getLikes());
        likes.add(user);
        film.setLikes(likes);
        filmStorage.update(film);
        return film;
    }

    public Film deleteLike(int film, Long user) {
        Film filmOne = filmStorage.get(film);
        Set<Long> idLikesFilm = new HashSet<>();
        if (filmOne.getLikes() != null && filmOne.getLikes().contains(user)) {
            idLikesFilm = filmOne.getLikes();
            idLikesFilm.remove(user);
            filmOne.setLikes(idLikesFilm);
            filmStorage.update(filmOne);
            return filmOne;
        }
        return null;
    }

    public Set<Film> topFilms(int count) {
        return filmStorage.getAllFilm().stream()
                .sorted((o1, o2) -> {
                    int result = Integer.valueOf(o1.getLikes().size()).compareTo(Integer.valueOf(o2.getLikes().size()));
                    return result * -1;
                }).limit(count)
                .collect(Collectors.toSet());
    }
}
