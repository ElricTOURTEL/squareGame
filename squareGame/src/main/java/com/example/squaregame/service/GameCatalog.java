package com.example.squaregame.service;

import com.example.squaregame.model.GameInfo;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameCatalog {
    private final Map<String, GameFactory> factories = new HashMap<>();

    public GameCatalog() {
        // Register TicTacToe factory
        TicTacToeGameFactory ticTacToeFactory = new TicTacToeGameFactory();
        factories.put("tictactoe", ticTacToeFactory);

        // Register Connect4 factory
        ConnectFourGameFactory connect4Factory = new ConnectFourGameFactory();
        factories.put("connect4", connect4Factory);
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

        for (Map.Entry<String, GameFactory> entry : factories.entrySet()) {
            String gameType = entry.getKey();
            GameFactory factory = entry.getValue();
            String description = getGameDescription(gameType);

            List<String> boardSizes = getSupportedBoardSizes(factory);
            games.add(new GameInfo(
                gameType,
                description,
                2,
                2,
                boardSizes.toArray(new String[0])
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

    private List<String> getSupportedBoardSizes(GameFactory factory) {
        // For TicTacToe: board size is always 3x3
        // For Connect4: board size is always 7x6
        if (factory instanceof TicTacToeGameFactory) {
            return List.of("3x3");
        } else if (factory instanceof ConnectFourGameFactory) {
            return List.of("7x6");
        }
        return List.of();
    }

    public Set<String> getAvailableGameTypes() {
        return factories.keySet();
    }
}
