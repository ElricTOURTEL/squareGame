package com.example.demo;

import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog {
    public TicTacToeGameFactory ticTacToeGameFactory;

    public GameCatalogImpl() {
        this.ticTacToeGameFactory = new TicTacToeGameFactory();
    }

    public Collection<String> getGameIdentifiers(){
        Collection<String> gameIdentifiers = new ArrayList<>();
        gameIdentifiers.add(ticTacToeGameFactory.getGameFactoryId());
        return gameIdentifiers;
    }
}
