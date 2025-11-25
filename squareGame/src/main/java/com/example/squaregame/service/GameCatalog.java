package com.example.squaregame.service;

import com.example.squaregame.model.GameInfo;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameCatalog {
    private final Map<String, GameFactory> factories = new HashMap<>();

    public GameCatalog(List<GameFactory> gameFactories) {
        for (GameFactory factory : gameFactories) {
            factories.put(factory.getGameType(), factory);
        }
    }

    public GameFactory getFactory(String gameType) {
        GameFactory factory = factories.get(gameType.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Unknown game type: " + gameType);
        }
        return factory;
    }

    public List<GameInfo> getAvailableGames() {
        List<GameInfo> games = new ArrayList<>();

        for (GameFactory factory : factories.values()) {
            String gameType = factory.getGameType();
            String description = getGameDescription(gameType);
            games.add(new GameInfo(
                gameType,
                description,
                2,
                2,
                factory.getSupportedBoardSizes()
            ));
        }

        return games;
    }

    private String getGameDescription(String gameType) {
        return switch (gameType.toLowerCase()) {
            case "tictactoe" -> "Classic Tic-Tac-Toe game on a 3x3 grid";
            case "connect4" -> "Connect Four - align 4 tokens in a row";
            default -> "Square game";
        };
    }

    public Set<String> getAvailableGameTypes() {
        return factories.keySet();
    }
}
