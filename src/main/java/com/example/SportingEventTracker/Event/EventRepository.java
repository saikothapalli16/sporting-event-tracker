package com.example.SportingEventTracker.Event;

import com.example.SportingEventTracker.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Get all events by user ID
    List<Event> findByUserId(Long userId);

    @Query("SELECT e FROM Event e WHERE e.userId = :user AND e.game = :game")
    Event findByUserIdAndGame(@Param("user")Long userId, @Param("game") Game game);

    //filter the specific games within a sport that the user went to
    @Query("SELECT e FROM Event e WHERE e.userId = :user AND e.game.sportType = :sport")
    List<Event> findByUserIdAndSportType(@Param("user")Long userId, @Param("sport")String sportType);

    @Query("SELECT e FROM Event e WHERE e.userId = :user AND e.game.seasonYear = :year")
    List<Event> findByUserIdAndYear(@Param("user")Long userId, @Param("year")Integer year);


    @Query("SELECT e FROM Event e WHERE e.userId = :user AND e.game.seasonYear = :year AND e.game.sportType = :sport")
    List<Event> findByUserIdAndYearAndSport(@Param("user")Long userId, @Param("year")Integer year, @Param("sport")String sport);

}
