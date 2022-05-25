package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private static int idCounter = 1;
    private static int id;
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private String name;
    private final LocalDate birthday;
    private Set<User> friends;

    public void setId(){
        id = idCounter++;
    }

    public int getId(){
        return id;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void addFriends(User friend) {
        friends.add(friend);
    }

    public void deleteFriend(User friend) {
        friends.remove(friend);
    }
}