package com.example.demo;

import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog {
    private TicTacToeGameFactory ticTacToeGameFactory;
    private TaquinGameFactory taquinGameFactory;
    private ConnectFourGameFactory connectFourGameFactory;

    public GameCatalogImpl() {
        this.ticTacToeGameFactory = new TicTacToeGameFactory();
        this.taquinGameFactory = new TaquinGameFactory();
        this.connectFourGameFactory = new ConnectFourGameFactory();
    }

    public Collection<String> getGameIdentifiers(){
        Collection<String> gameIdentifiers = new ArrayList<>();
        gameIdentifiers.add(ticTacToeGameFactory.getGameFactoryId());
        gameIdentifiers.add(taquinGameFactory.getGameFactoryId());
        gameIdentifiers.add(connectFourGameFactory.getGameFactoryId());
        return gameIdentifiers;
    }
}
