package com.example.squaregame.service;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Component;

@Component
public class TicTacToeFactory implements GameFactory {
    private final TicTacToeGameFactory engineFactory = new TicTacToeGameFactory();

    @Override
    public Game createGame(String boardSize) {
        return engineFactory.createGame(2, 3);
    }

    @Override
    public String getGameType() {
        return "tictactoe";
    }

    @Override
    public String[] getSupportedBoardSizes() {
        return new String[]{"3x3"};
    }
}
