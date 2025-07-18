package com.example.SportingEventTracker.Game;

import com.example.SportingEventTracker.Game.NFLGame.NFLGameService;
import com.example.SportingEventTracker.dto.GameDTO;
import com.example.SportingEventTracker.dto.NFLGameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/games")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    private final NFLGameService nflGameService;

    @Autowired
    public GameController(NFLGameService nflGameService) {
        this.nflGameService = nflGameService;
    }

    @GetMapping
    public List<? extends GameDTO> getGames(@RequestParam String sport,
                                            @RequestParam(required = false) Long id,
                                            @RequestParam(required = false) Integer year,
                                            @RequestParam(required = false) String teamOne,
                                            @RequestParam(required = false) String teamTwo) {

        switch (sport.toUpperCase()) {
            case "NFL" -> {

                return nflGameService.getGames(year, teamOne, teamTwo, id);
            }
            // future: case "NBA": return nbaGameService.getAllGames() ...
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported sport: " + sport
            );
        }
}








}
