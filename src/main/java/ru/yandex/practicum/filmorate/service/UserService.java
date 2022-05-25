package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Set;

@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int user, int friend) {
        userStorage.getAllUser().get(user).addFriends(userStorage.getAllUser().get(friend));
        userStorage.getAllUser().get(friend).addFriends(userStorage.getAllUser().get(user));
    }

    public void deleteFriend(int user, int friend) {
        userStorage.getAllUser().get(user).deleteFriend(userStorage.getAllUser().get(friend));
        userStorage.getAllUser().get(friend).deleteFriend(userStorage.getAllUser().get(user));
    }

    public Set<User> generalFriend(int id1, int id2) {
        Set<User> generalFriend = null;
        for (User user1: userStorage.getAllUser().get(id1).getFriends()) {
            for (User user2: userStorage.getAllUser().get(id2).getFriends()) {
                if (user1.getId() == user2.getId()) {
                    generalFriend.add(user1);
                }
            }
        }
        return generalFriend;
    }

    public Set<User> getFriend(int id) {
        return userStorage.get(id).getFriends();
    }
}
