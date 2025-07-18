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

    @Query("SELECT g FROM NFLGame g WHERE LOWER(g.teamHome) IN :aliases OR LOWER(g.teamAway) IN :aliases")
    List<NFLGame> findGamesByTeamAliases(@Param("aliases") List<String> aliases);

    @Query("""
    SELECT g FROM NFLGame g
    WHERE\s
        (LOWER(g.teamHome) IN :teamOneAliases AND LOWER(g.teamAway) IN :teamTwoAliases)
        OR
        (LOWER(g.teamHome) IN :teamTwoAliases AND LOWER(g.teamAway) IN :teamOneAliases)
""")
    List<NFLGame> findGamesByTeamsAliases(@Param("teamOneAliases") List<String> teamOneAliases,
                                          @Param("teamTwoAliases") List<String> teamTwoAliases);





}

