package com.example.squaregame.dto;

import com.example.squaregame.model.Game;
import com.example.squaregame.model.GameStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class GameResponse {
    private String id;
    private String gameType;
    private String boardSize;
    private GameStatus status;
    private UUID currentPlayerId;
    private UUID winnerId;
    private LocalDateTime createdAt;
    private LocalDateTime lastMoveAt;

    public GameResponse() {
    }

    public static GameResponse fromGame(Game game) {
        GameResponse response = new GameResponse();
        response.setId(game.getId());
        response.setGameType(game.getGameType());
        response.setBoardSize(game.getBoardSize());
        response.setStatus(game.getStatus());
        response.setCurrentPlayerId(game.getCurrentPlayerId());
        response.setWinnerId(game.getWinnerId());
        response.setCreatedAt(game.getCreatedAt());
        response.setLastMoveAt(game.getLastMoveAt());
        return response;
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
