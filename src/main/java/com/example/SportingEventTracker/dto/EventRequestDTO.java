package com.example.SportingEventTracker.dto;


//this information comes from the frontend after the user selects an event
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class EventRequestDTO {
    private Long gameId;

    @NotNull
    private Long userId; // if you're supporting multiple users soon
    private String userNote;
    private String sportType;
    private Integer year;
}