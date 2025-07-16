package com.example.SportingEventTracker.dto;


import com.example.SportingEventTracker.Game.Game;
import com.example.SportingEventTracker.Game.NFLGame.NFLGame;

public class GameMapper {

    public static GameDTO toDTO(Game game) {
        if (game instanceof NFLGame nflGame) {
            return new NFLGameDTO(nflGame);
        }

        // Add more sports later
        throw new IllegalArgumentException("Unsupported game type: " + game.getClass().getSimpleName());
    }
}
