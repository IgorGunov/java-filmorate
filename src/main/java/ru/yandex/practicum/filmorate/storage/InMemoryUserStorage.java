package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public Map<Integer, User> getAllUser() {
        return users;
    }

    @Override
    public void put(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        return getAllUser().getOrDefault(id, null);
    }
}
