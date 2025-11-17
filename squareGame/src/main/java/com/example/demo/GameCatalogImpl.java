package com.example.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.Move;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Collection<Move> getAllowedMoves(String gameId) {
        GameFactory gameFactory = gameFactoriesMap.get(gameId);
        if (gameFactory == null) {
            return new ArrayList<>();
        }
        Game game = gameFactory.createGame();
        return game.getAllowedMoves();
    }
}
