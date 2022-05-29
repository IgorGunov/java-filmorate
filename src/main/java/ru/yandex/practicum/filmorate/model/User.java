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
    private int id;
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private String name;
    private final LocalDate birthday;
    private Set<Integer> friends;

    public void setId(){
        id = idCounter++;
    }

    public void addFriends(int friend) {
        friends.add(friend);
    }

    public void deleteFriend(int friend) {
        friends.remove(friend);
    }
}