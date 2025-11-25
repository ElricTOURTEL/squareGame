package com.example.squaregame.service;

import fr.le_campus_numerique.square_games.engine.Game;

public interface GameFactory {
    Game createGame(String boardSize);
    String getGameType();
    String[] getSupportedBoardSizes();
}
