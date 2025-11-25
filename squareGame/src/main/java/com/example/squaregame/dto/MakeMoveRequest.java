package com.example.squaregame.dto;

import java.util.UUID;

public class MakeMoveRequest {
    private int x;
    private int y;
    private UUID playerId;

    public MakeMoveRequest() {
    }

    public MakeMoveRequest(int x, int y, UUID playerId) {
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }

    // Getters and Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
