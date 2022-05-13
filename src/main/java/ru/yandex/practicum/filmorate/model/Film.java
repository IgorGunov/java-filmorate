package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private static int idCounter = 0;
    private static int id;
    @NotBlank
    private final String name;
    @NotEmpty
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;

    public void setId(){
        id = idCounter++;
    }

    public int getId(){
        return id;
    }
}