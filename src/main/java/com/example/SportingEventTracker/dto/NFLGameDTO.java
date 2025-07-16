package com.example.SportingEventTracker.dto;

import com.example.SportingEventTracker.Game.NFLGame.NFLGame;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NFLGameDTO extends GameDTO {

    private String homeTeam;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;

    private Boolean stadiumNeutral;

    private Integer weatherTemperature;
    private Integer weatherWindMph;
    private Integer weatherHumidity;
    private String weatherDetail;

    private String teamFavoriteId;
    private Double spreadFavorite;
    private Double overUnderLine;

    public NFLGameDTO(NFLGame game) {
        super(
                game.getId(),
                game.getSportType(),
                game.getGameDate(),
                game.getStadium()
        );
        this.homeTeam = game.getTeamHome();
        this.awayTeam = game.getTeamAway();
        this.homeScore = game.getScoreHome();
        this.awayScore = game.getScoreAway();
        this.stadiumNeutral = game.getStadiumNeutral();
        this.weatherTemperature = game.getWeatherTemperature();
        this.weatherWindMph = game.getWeatherWindMph();
        this.weatherHumidity = game.getWeatherHumidity();
        this.weatherDetail = game.getWeatherDetail();
        this.teamFavoriteId = game.getTeamFavoriteId();
        this.spreadFavorite = game.getSpreadFavorite();
        this.overUnderLine = game.getOverUnderLine();
    }

}
