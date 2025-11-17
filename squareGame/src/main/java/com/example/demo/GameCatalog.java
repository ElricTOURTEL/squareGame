package com.example.demo;

import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.Move;

import java.util.Collection;
import java.util.List;

public interface GameCatalog {
    Collection<String> getGameIdentifiers();
    GameFactory getGameFactory(String gameId);
    Collection<Move> getAllowedMoves(String gameId);
}
