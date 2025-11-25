package com.example.squaregame.service;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.stereotype.Component;

@Component
public class Connect4Factory implements GameFactory {
    private final ConnectFourGameFactory engineFactory = new ConnectFourGameFactory();

    @Override
    public Game createGame(String boardSize) {
        return engineFactory.createGame(2, 7);
    }

    @Override
    public String getGameType() {
        return "connect4";
    }

    @Override
    public String[] getSupportedBoardSizes() {
        return new String[]{"7x6"};
    }
}
