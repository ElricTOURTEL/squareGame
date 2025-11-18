package com.example.demo;

import fr.le_campus_numerique.square_games.engine.Game;

public interface GameService {
    /**
     * Creates a new game with the specified parameters
     * @param gameType the type of game (e.g., "tictactoe", "connectfour", "taquin")
     * @param playerCount the number of players
     * @param boardSize the size of the game board
     * @return the ID of the created game as a String
     */
    String createGame(String gameType, int playerCount, int boardSize);

    /**
     * Retrieves an active game by its ID
     * @param gameId the ID of the game
     * @return the Game object, or null if not found
     */
    Game getGame(String gameId);

    /**
     * Retrieves a DTO representation of a game for API responses
     * @param gameId the ID of the game
     * @return a GameDTO object, or null if not found
     */
    GameDTO getGameDTO(String gameId);

    /**
     * Plays a move in an active game
     * @param gameId the ID of the game
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     * @return a GameDTO object with the updated game state
     * @throws IllegalArgumentException if the game is not found or the move is invalid
     */
    GameDTO playMove(String gameId, int x, int y);
}
