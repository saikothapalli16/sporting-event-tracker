package com.example.SportingEventTracker.Game.NFLGame;

import com.example.SportingEventTracker.Game.Game;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NFLGame extends Game {


    private String scheduleWeek;

    private Boolean schedulePlayoff;

    private String teamHome;
    private Integer scoreHome;
    private Integer scoreAway;
    private String teamAway;

    private String teamFavoriteId;
    private Double spreadFavorite;
    private Double overUnderLine;

    private Boolean stadiumNeutral;

    private Integer weatherTemperature;
    private Integer weatherWindMph;
    private Integer weatherHumidity;

    private String weatherDetail;
}
