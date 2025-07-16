package com.example.SportingEventTracker.Game;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_date")
    private LocalDate gameDate;

    private String stadium;

    @Column(name = "sport_type")
    private String sportType;

    @Column(name = "season_year")
    private Integer seasonYear;
}
