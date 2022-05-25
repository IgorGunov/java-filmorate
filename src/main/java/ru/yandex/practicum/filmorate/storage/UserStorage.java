package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {

    public Map<Integer, User> getAllUser();

    public void put(User user);

    public User get(int id);
}
