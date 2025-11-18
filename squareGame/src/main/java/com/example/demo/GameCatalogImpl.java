package com.example.demo;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.Token;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class GameCatalogImpl implements GameCatalog {
    private TicTacToeGameFactory ticTacToeGameFactory;
    private TaquinGameFactory taquinGameFactory;
    private ConnectFourGameFactory connectFourGameFactory;
    private Map<String, GameFactory> gameFactoriesMap;

    public GameCatalogImpl() {
        this.ticTacToeGameFactory = new TicTacToeGameFactory();
        this.taquinGameFactory = new TaquinGameFactory();
        this.connectFourGameFactory = new ConnectFourGameFactory();

        // Initialize map with game factories
        this.gameFactoriesMap = new HashMap<>();
        gameFactoriesMap.put(ticTacToeGameFactory.getGameFactoryId(), ticTacToeGameFactory);
        gameFactoriesMap.put(taquinGameFactory.getGameFactoryId(), taquinGameFactory);
        gameFactoriesMap.put(connectFourGameFactory.getGameFactoryId(), connectFourGameFactory);
    }

    public Collection<String> getGameIdentifiers(){
        Collection<String> gameIdentifiers = new ArrayList<>();
        gameIdentifiers.add(ticTacToeGameFactory.getGameFactoryId());
        gameIdentifiers.add(taquinGameFactory.getGameFactoryId());
        gameIdentifiers.add(connectFourGameFactory.getGameFactoryId());
        return gameIdentifiers;
    }

    @Override
    public GameFactory getGameFactory(String gameId) {
        return gameFactoriesMap.get(gameId);
    }

    @Override
    public Set<CellPosition> getAllowedMoves(String gameId) {
        GameFactory gameFactory = gameFactoriesMap.get(gameId);
        if (gameFactory == null) {
            return new HashSet<>();
        }

        // Create a game instance with default parameters
        try {
            UUID player1 = UUID.randomUUID();
            UUID player2 = UUID.randomUUID();

            // Create game with 2 players and default board size
            Game game = gameFactory.createGame(3, Set.of(player1, player2));

            // Collect all allowed moves from remaining tokens
            Set<CellPosition> allMoves = new HashSet<>();
            for (Token token : game.getRemainingTokens()) {
                allMoves.addAll(token.getAllowedMoves());
            }

            return allMoves;
        } catch (Exception e) {
            // Handle any exceptions and return empty set
            return new HashSet<>();
        }
    }

}
