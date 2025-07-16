package com.example.SportingEventTracker.Game.NFLGame;

import com.example.SportingEventTracker.dto.NFLGameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class NFLGameService {
    private final NFLGameRepository nflGameRepository;

    @Autowired
    public NFLGameService(NFLGameRepository nflGameRepository) {
        this.nflGameRepository = nflGameRepository;
    }


    public List<NFLGameDTO> getGames(Integer year, String teamOne, String teamTwo, Long id) {
        if (id != null) {
            return getGameById(id).map(List::of).orElse(List.of());
        } else if (teamOne != null && teamTwo != null && year != null) {
            return getGamesByYearAndTeams(year, teamOne, teamTwo);
        } else if (teamOne != null && teamTwo != null) {
            return getGamesByTeams(teamOne, teamTwo);
        } else if (teamOne != null && year != null) {
            return getGamesByYearAndTeam(year, teamOne);
        } else if (year != null) {
            return getGamesByYear(year);
        } else if (teamOne != null) {
            return getGamesByTeam(teamOne);
        } else {
            return getAllGames();
        }
    }


//helper methods
    private List<NFLGameDTO> getAllGames() {
        return nflGameRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Optional<NFLGameDTO> getGameById(Long gameId) {
        return nflGameRepository.findById(gameId)
                .map(this::convertToDto);
    }

    private List<NFLGameDTO> getGamesByYearAndTeam(int year, String team) {
        return nflGameRepository.findGamesByYearAndTeam(year, team).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<NFLGameDTO> getGamesByYearAndTeams(int year, String teamOne, String teamTwo) {
        return nflGameRepository.findGamesByYearAndTeams(year, teamOne, teamTwo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<NFLGameDTO> getGamesByYear(int year) {
        return nflGameRepository.findBySeasonYear(year).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<NFLGameDTO> getGamesByTeams(String teamOne, String teamTwo) {
        return nflGameRepository.findGamesByTeams(teamOne, teamTwo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<NFLGameDTO> getGamesByTeam(String team) {
        return nflGameRepository.findGamesByTeam(team).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NFLGameDTO convertToDto(NFLGame game) {
        NFLGameDTO dto = new NFLGameDTO();
        dto.setGameId(game.getId());
        dto.setSport(game.getSportType());
        dto.setDate(game.getGameDate());
        dto.setStadium(game.getStadium());
        dto.setHomeTeam(game.getTeamHome());
        dto.setAwayTeam(game.getTeamAway());
        dto.setHomeScore(game.getScoreHome());
        dto.setAwayScore(game.getScoreAway());
        dto.setStadiumNeutral(game.getStadiumNeutral());
        dto.setWeatherTemperature(game.getWeatherTemperature());
        dto.setWeatherWindMph(game.getWeatherWindMph());
        dto.setWeatherHumidity(game.getWeatherHumidity());
        dto.setWeatherDetail(game.getWeatherDetail());
        dto.setTeamFavoriteId(game.getTeamFavoriteId());
        dto.setSpreadFavorite(game.getSpreadFavorite());
        dto.setOverUnderLine(game.getOverUnderLine());
        return dto;
    }
}
