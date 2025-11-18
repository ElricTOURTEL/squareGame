package com.example.demo;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.GameFactory;

import java.util.Collection;
import java.util.Set;

public interface GameCatalog {
    Collection<String> getGameIdentifiers();
    GameFactory getGameFactory(String gameId);
    Set<CellPosition> getAllowedMoves(String gameId);
}
