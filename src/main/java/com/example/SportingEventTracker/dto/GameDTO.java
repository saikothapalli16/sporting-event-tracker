package com.example.SportingEventTracker.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Tell Jackson to serialize this with "sport" as the type discriminator
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "sport"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NFLGameDTO.class, name = "NFL")
        // Add other sports here, e.g.
        // @JsonSubTypes.Type(value = NBAGameDTO.class, name = "NBA")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class GameDTO {
    protected Long gameId;
    protected String sport;
    protected LocalDate date;
    protected String stadium;
}
