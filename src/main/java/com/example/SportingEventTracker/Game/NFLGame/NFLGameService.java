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
            String resolvedTeamOne = resolveHistoricalTeamName(teamOne, year);
            String resolvedTeamTwo = resolveHistoricalTeamName(teamTwo, year);
            return getGamesByYearAndTeams(year, resolvedTeamOne, resolvedTeamTwo);
        } else if (teamOne != null && teamTwo != null) {
            return getGamesByTeams(teamOne, teamTwo);
        } else if (teamOne != null && year != null) {
            String resolvedTeam = resolveHistoricalTeamName(teamOne, year);
            return getGamesByYearAndTeam(year, resolvedTeam);
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
        List<String> aliasesOne = getAllHistoricalNames(teamOne).stream()
                .map(String::toLowerCase)
                .toList();
        List<String> aliasesTwo = getAllHistoricalNames(teamTwo).stream()
                .map(String::toLowerCase)
                .toList();
        return nflGameRepository.findGamesByTeamsAliases(aliasesOne, aliasesTwo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<NFLGameDTO> getGamesByTeam(String team) {
        List<String> aliases = getAllHistoricalNames(team).stream()
                .map(String::toLowerCase)
                .toList();
        return nflGameRepository.findGamesByTeamAliases(aliases).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<String> getAllHistoricalNames(String canonicalName) {
        if (canonicalName.equalsIgnoreCase("Washington Commanders")) {
            return List.of("Washington Commanders", "Washington Football Team", "Washington Redskins");
        } else if (canonicalName.equalsIgnoreCase("Las Vegas Raiders")) {
            return List.of("Las Vegas Raiders", "Oakland Raiders");
        } else if (canonicalName.equalsIgnoreCase("Los Angeles Chargers")) {
            return List.of("Los Angeles Chargers", "San Diego Chargers");
        }
        return List.of(canonicalName);
    }


    private String resolveHistoricalTeamName(String canonicalName, int year) {
        if (canonicalName.equalsIgnoreCase("Washington Commanders")) {
            if (year <= 2019) return "Washington Redskins";
            if (year == 2020 || year == 2021) return "Washington Football Team";
            return "Washington Commanders";
        } else if (canonicalName.equalsIgnoreCase("Las Vegas Raiders")) {
            if (year <= 2019) return "Oakland Raiders";
            return "Las Vegas Raiders";
        } else if (canonicalName.equalsIgnoreCase("Los Angeles Chargers")) {
            if (year <= 2016) return "San Diego Chargers";
            return "Los Angeles Chargers";
        }
        return canonicalName;
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
