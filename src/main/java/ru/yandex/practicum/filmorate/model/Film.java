package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    private static int idCounter = 1;
    private static int id;
    @NotBlank
    private final String name;
    @NotEmpty
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private Set<Integer> likes;

    public Set<Integer> getLikes() {
        return likes;
    }

    public void addLikes(int userId) {
        likes.add(userId);
    }

    public void deleteLikes(int useId) {
        likes.remove(useId);
    }

    public void setId(){
        id = idCounter++;
    }

    public int getId(){
        return id;
    }
}