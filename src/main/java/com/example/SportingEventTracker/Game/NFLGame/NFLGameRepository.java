package com.example.SportingEventTracker.Game.NFLGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NFLGameRepository extends JpaRepository<NFLGame, Long> {

    List<NFLGame> findBySeasonYear(Integer seasonYear);

    @Query("SELECT g FROM NFLGame g WHERE g.seasonYear = :year AND (LOWER(g.teamHome) = LOWER(:team) OR LOWER(g.teamAway) = LOWER(:team))")
    List<NFLGame> findGamesByYearAndTeam(@Param("year") int year, @Param("team") String team);


    @Query("SELECT g FROM NFLGame g WHERE g.seasonYear = :year AND ((LOWER(g.teamHome) = LOWER(:teamOne) AND (LOWER(g.teamAway) = LOWER(:teamTwo))) OR (LOWER(g.teamHome) = LOWER(:teamTwo) AND (LOWER(g.teamAway) = LOWER(:teamOne))))")
    List<NFLGame> findGamesByYearAndTeams(@Param("year") int year, @Param("teamOne") String teamOne, @Param("teamTwo") String teamTwo);


    @Query("SELECT g FROM NFLGame g WHERE ((LOWER(g.teamHome) = LOWER(:teamOne) AND (LOWER(g.teamAway) = LOWER(:teamTwo))) OR (LOWER(g.teamHome) = LOWER(:teamTwo) AND (LOWER(g.teamAway) = LOWER(:teamOne))))")
    List<NFLGame> findGamesByTeams(@Param("teamOne") String teamOne, @Param("teamTwo") String teamTwo);

    @Query("SELECT g FROM NFLGame g WHERE (LOWER(g.teamHome) = LOWER(:team) OR LOWER(g.teamAway) = LOWER(:team))")
    List<NFLGame> findGamesByTeam(@Param("team") String team);



}

