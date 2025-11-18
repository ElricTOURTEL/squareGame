package com.example.demo;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameCatalog gameCatalog;

    private final Map<String, Game> activeGames = new HashMap<>();

    @Override
    public String createGame(String gameType, int playerCount, int boardSize) {
        // Get the game factory for the specified game type
        GameFactory gameFactory = gameCatalog.getGameFactory(gameType);
        if (gameFactory == null) {
            throw new IllegalArgumentException("Game type not found: " + gameType);
        }

        try {
            // Create a new game with the provided parameters
            UUID gameId = UUID.randomUUID();
            Set<UUID> players = generatePlayerIds(playerCount);

            // Create the game using the factory
            Game game = gameFactory.createGame(boardSize, players);

            // Store the game in the active games map
            String gameIdStr = gameId.toString();
            activeGames.put(gameIdStr, game);

            return gameIdStr;
        } catch (IllegalArgumentException e) {
            // Re-throw if it's our validation error
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create game: " + e.getMessage(), e);
        }
    }

    @Override
    public Game getGame(String gameId) {
        return activeGames.get(gameId);
    }

    @Override
    public GameDTO getGameDTO(String gameId) {
        Game game = getGame(gameId);
        if (game == null) {
            return null;
        }

        // Convert Game to GameDTO
        GameDTO dto = new GameDTO();
        dto.setGameId(gameId);
        dto.setGameType(game.getFactoryId());
        dto.setStatus(game.getStatus().toString());
        dto.setBoardSize(game.getBoardSize());
        dto.setPlayerIds(game.getPlayerIds());
        dto.setCurrentPlayerId(game.getCurrentPlayerId());

        // Create a simple board representation
        Map<String, Object> boardMap = new HashMap<>();
        boardMap.put("size", game.getBoardSize());
        boardMap.put("occupiedCells", game.getBoard().size());
        dto.setBoard(boardMap);

        return dto;
    }

    @Override
    public GameDTO playMove(String gameId, int x, int y) {
        // Get the game
        Game game = getGame(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found: " + gameId);
        }

        try {
            // Create the target position
            CellPosition targetPosition = new CellPosition(x, y);

            // Find a token that can move to this position
            Token tokenToMove = null;
            for (Token token : game.getRemainingTokens()) {
                Set<CellPosition> allowedMoves = token.getAllowedMoves();
                if (allowedMoves.contains(targetPosition)) {
                    tokenToMove = token;
                    break;
                }
            }

            // If no valid token found for this move, throw exception
            if (tokenToMove == null) {
                throw new IllegalArgumentException("Invalid move: no token can move to position (" + x + ", " + y + ")");
            }

            // Execute the move
            tokenToMove.moveTo(targetPosition);

            // Return the updated game state as DTO
            return getGameDTO(gameId);
        } catch (InvalidPositionException e) {
            throw new IllegalArgumentException("Invalid move: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to play move: " + e.getMessage(), e);
        }
    }

    /**
     * Generates a set of random player IDs
     * @param playerCount the number of players to generate IDs for
     * @return a set of UUID player IDs
     */
    private Set<UUID> generatePlayerIds(int playerCount) {
        Set<UUID> players = new java.util.HashSet<>();
        for (int i = 0; i < playerCount; i++) {
            players.add(UUID.randomUUID());
        }
        return players;
    }
}
