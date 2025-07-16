package com.example.SportingEventTracker.Event;

import com.example.SportingEventTracker.Game.Game;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;


    @Column(name = "user_note")
    private String userNote;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}