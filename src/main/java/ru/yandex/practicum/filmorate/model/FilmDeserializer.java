package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FilmDeserializer extends JsonDeserializer {
    private Set<Long> likes = new HashSet<Long>();
    ObjectMapper mapper = new ObjectMapper();


    @Override
    public Film deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException {
        mapper.registerModule(new JavaTimeModule());

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        LocalDate date = mapper.convertValue(node.get("releaseDate"), LocalDate.class);
        int id = 0;
        try {
            id = node.get("id").intValue();
        } catch (Exception e) {
        }
        return new Film(id,
                node.get("name").asText(),
                node.get("description").asText(),
                date,
                node.get("duration").intValue(),
                likes);
    }
}