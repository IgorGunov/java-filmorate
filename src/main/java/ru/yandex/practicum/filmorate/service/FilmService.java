package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;
    private final TreeSet<Film> sortFilms = new TreeSet<>(new Comparator<Film>() {

        public int compare(Film o1, Film o2) {
            if (o1.getLikes().size() == 0 && o2.getLikes().size() != 0) {
                return o2.getLikes().size();
            } else if (o2.getLikes().size() == 0 && o1.getLikes().size() != 0) {
                return o1.getLikes().size();
            } else {
                return o2.getLikes().size() - o1.getLikes().size();
            }
        }
    });

    public Film addLike(int film, int user) {
        Film filmOne = filmStorage.get(film);
        Set<Integer> idLikesFilm = new HashSet<>();
        if (filmOne.getLikes() != null) {
            idLikesFilm = filmOne.getLikes();
        }
        idLikesFilm.add(user);
        filmOne.setLikes(idLikesFilm);
        return filmOne;
    }

    public Film deleteLike(int film, int user) {
        Film filmOne = filmStorage.get(film);
        Set<Integer> idLikesFilm = new HashSet<>();
        if (filmOne.getLikes() != null && filmOne.getLikes().contains(user)) {
            idLikesFilm = filmOne.getLikes();
            idLikesFilm.remove(user);
            filmOne.setLikes(idLikesFilm);
            return filmOne;
        }
        return null;
    }

    public Set<Film> topTen(int count) {
        if (filmStorage.getAllFilm().size() != 0) {
            sortFilms.addAll(filmStorage.getAllFilm());
            if (count == 0) {
                sortFilms.stream().limit(10);
            } else {
                sortFilms.stream().limit(count);
            }
        }
        return sortFilms;

    }
}
