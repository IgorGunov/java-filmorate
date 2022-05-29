package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> getAllUser() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void put(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        for (User user: getAllUser()) {
            if (user.getId() == id) {
                return user;
            }
        };
        return null;
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(), user);
    }
}
