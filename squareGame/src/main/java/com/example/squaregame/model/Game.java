package com.example.squaregame.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "game_type", length = 50, nullable = false)
    private String gameType;

    @Column(name = "board_size", length = 20)
    private String boardSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private GameStatus status;

    @Column(name = "current_player_id", length = 36)
    private UUID currentPlayerId;

    @Column(name = "winner_id", length = 36)
    private UUID winnerId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_move_at", nullable = false)
    private LocalDateTime lastMoveAt;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.status = GameStatus.IN_PROGRESS;
        this.createdAt = LocalDateTime.now();
        this.lastMoveAt = LocalDateTime.now();
    }

    public Game(String gameType, String boardSize) {
        this();
        this.gameType = gameType;
        this.boardSize = boardSize;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(String boardSize) {
        this.boardSize = boardSize;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public UUID getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(UUID currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public UUID getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(UUID winnerId) {
        this.winnerId = winnerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastMoveAt() {
        return lastMoveAt;
    }

    public void setLastMoveAt(LocalDateTime lastMoveAt) {
        this.lastMoveAt = lastMoveAt;
    }
}
