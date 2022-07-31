package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {

    public List<User> getAllUser();

    public void put(User user);

    public User get(Long id);

    public User update(User user);
}
