package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(Long user, Long friend) {
        User userOne = userStorage.get(user);
        User friendOne = userStorage.get(friend);

        Set<Long> setUser = new HashSet<>();
        if (userOne.getFriends() != null) {
            setUser = userOne.getFriends();
        }
        setUser.add(friend);
        userOne.setFriends(setUser);

        Set<Long> setFriend = new HashSet<>();
        if (friendOne.getFriends() != null) {
            setFriend = friendOne.getFriends();
        }
        setFriend.add(user);
        friendOne.setFriends(setFriend);

        userStorage.update(friendOne);
        userStorage.update(userOne);

        return userOne;
    }

    public User deleteFriend(Long user, Long friend) {
        User userOne = userStorage.get(user);
        User friendOne = userStorage.get(friend);

        Set<Long> setUser = new HashSet<>();
        setUser = userOne.getFriends();
        if (userOne.getFriends() != null) {
            setUser = userOne.getFriends();
        }
        setUser.remove(friend);
        userOne.setFriends(setUser);

        Set<Long> setFriend = new HashSet<>();
        setFriend = friendOne.getFriends();
        if (friendOne.getFriends() != null) {
            setFriend = friendOne.getFriends();
        }
        setFriend.remove(user);
        friendOne.setFriends(setFriend);

        userStorage.update(friendOne);
        userStorage.update(userOne);

        return userOne;
    }

    public ArrayList<User> generalFriend(Long id1, Long id2) {
        ArrayList<User> generalFriend = new ArrayList<>();
        if (userStorage.get(id1).getFriends() != null || userStorage.get(id2).getFriends() != null) {
            for (Long user1 : userStorage.get(id1).getFriends()) {
                for (Long user2 : userStorage.get(id2).getFriends()) {
                    if (user1 == user2) {
                        generalFriend.add(userStorage.get(user1));
                    }
                }
            }
        }
        return generalFriend;
    }

    public List<User> getFriend(Long id) {
        List<User> listUser = new ArrayList<>();
        if (userStorage.get(id).getFriends().size() != 0) {
            for (Long user: userStorage.get(id).getFriends()) {
                listUser.add(userStorage.get(user));
            }
        }
        return listUser;
    }
}
