package com.example.squaregame.model;

public class GameInfo {
    private String name;
    private String description;
    private int minPlayers;
    private int maxPlayers;
    private String[] availableBoardSizes;

    public GameInfo(String name, String description, int minPlayers, int maxPlayers, String[] availableBoardSizes) {
        this.name = name;
        this.description = description;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.availableBoardSizes = availableBoardSizes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String[] getAvailableBoardSizes() {
        return availableBoardSizes;
    }

    public void setAvailableBoardSizes(String[] availableBoardSizes) {
        this.availableBoardSizes = availableBoardSizes;
    }
}
