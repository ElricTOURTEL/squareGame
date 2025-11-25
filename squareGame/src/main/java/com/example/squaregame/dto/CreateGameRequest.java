package com.example.squaregame.dto;

public class CreateGameRequest {
    private String gameType;
    private String boardSize;

    public CreateGameRequest() {
    }

    public CreateGameRequest(String gameType, String boardSize) {
        this.gameType = gameType;
        this.boardSize = boardSize;
    }

    // Getters and Setters
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
}
