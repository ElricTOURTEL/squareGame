package com.example.squaregame.service;

import com.example.squaregame.dao.GameRepository;
import com.example.squaregame.dao.MoveRepository;
import com.example.squaregame.model.GameInfo;
import com.example.squaregame.model.GameStatus;
import com.example.squaregame.model.Move;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.Token;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private final GameCatalog gameCatalog;
    private final Map<String, fr.le_campus_numerique.square_games.engine.Game> activeEngines = new ConcurrentHashMap<>();

    @Autowired
    public GameService(GameRepository gameRepository, MoveRepository moveRepository, GameCatalog gameCatalog) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
        this.gameCatalog = gameCatalog;
    }

    public List<GameInfo> getAvailableGames() {
        return gameCatalog.getAvailableGames();
    }

    public com.example.squaregame.model.Game createGame(String gameType, String boardSize) {
        GameFactory factory = gameCatalog.getFactory(gameType);

        // Parse boardSize (e.g., "3x3" -> boardSize=3, "7x6" -> boardSize=7)
        int parsedBoardSize = Integer.parseInt(boardSize.split("x")[0]);

        // Create game with 2 players by default
        // Note: factory.createGame signature is createGame(playerCount, boardSize)
        fr.le_campus_numerique.square_games.engine.Game engine = factory.createGame(2, parsedBoardSize);

        com.example.squaregame.model.Game game = new com.example.squaregame.model.Game(gameType, boardSize);
        game.setCurrentPlayerId(engine.getCurrentPlayerId());

        gameRepository.save(game);
        activeEngines.put(game.getId(), engine);

        return game;
    }

    public com.example.squaregame.model.Game getGame(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found: " + gameId));
    }

    public List<com.example.squaregame.model.Game> getAllGames() {
        return gameRepository.findAll();
    }

    public com.example.squaregame.model.Game makeMove(String gameId, int x, int y, UUID playerId) {
        com.example.squaregame.model.Game game = getGame(gameId);

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }

        fr.le_campus_numerique.square_games.engine.Game engine = activeEngines.get(gameId);
        if (engine == null) {
            throw new IllegalStateException("Game engine not found");
        }

        UUID currentPlayerId = engine.getCurrentPlayerId();
        if (!currentPlayerId.equals(playerId)) {
            throw new IllegalArgumentException("Not this player's turn");
        }

        CellPosition targetPosition = new CellPosition(y, x);

        Token tokenToMove = findTokenToMove(engine, targetPosition, playerId);
        if (tokenToMove == null) {
            throw new IllegalArgumentException("No available token can move to this position");
        }

        try {
            tokenToMove.moveTo(targetPosition);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid move: " + e.getMessage());
        }

        Move move = new Move(gameId, x, y, playerId);
        moveRepository.save(move);

        game.setLastMoveAt(LocalDateTime.now());

        fr.le_campus_numerique.square_games.engine.GameStatus engineStatus = engine.getStatus();
        if (engineStatus == fr.le_campus_numerique.square_games.engine.GameStatus.TERMINATED) {
            game.setStatus(GameStatus.FINISHED);
            UUID winner = findWinner(engine);
            if (winner != null) {
                game.setWinnerId(winner);
            }
        } else {
            UUID nextPlayerId = engine.getCurrentPlayerId();
            game.setCurrentPlayerId(nextPlayerId);
        }

        gameRepository.save(game);
        return game;
    }

    private Token findTokenToMove(fr.le_campus_numerique.square_games.engine.Game engine, CellPosition targetPosition, UUID playerId) {
        Collection<Token> remainingTokens = engine.getRemainingTokens();

        for (Token token : remainingTokens) {
            if (token.getOwnerId().isPresent() &&
                token.getOwnerId().get().equals(playerId) &&
                token.canMove() &&
                token.getAllowedMoves().contains(targetPosition)) {
                return token;
            }
        }
        return null;
    }

    private UUID findWinner(fr.le_campus_numerique.square_games.engine.Game engine) {
        Map<CellPosition, Token> board = engine.getBoard();
        if (board.isEmpty()) {
            return null;
        }

        Map<UUID, Integer> playerTokenCounts = new HashMap<>();
        for (Token token : board.values()) {
            if (token.getOwnerId().isPresent()) {
                UUID ownerId = token.getOwnerId().get();
                playerTokenCounts.put(ownerId, playerTokenCounts.getOrDefault(ownerId, 0) + 1);
            }
        }

        return playerTokenCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<Move> getGameMoves(String gameId) {
        return moveRepository.findByGameIdOrderByTimestampAsc(gameId);
    }

    @Transactional
    public void deleteGame(String gameId) {
        activeEngines.remove(gameId);
        moveRepository.deleteByGameId(gameId);
        gameRepository.deleteById(gameId);
    }

    public Token[][] getBoard(String gameId) {
        fr.le_campus_numerique.square_games.engine.Game engine = activeEngines.get(gameId);
        if (engine == null) {
            throw new IllegalStateException("Game engine not found");
        }

        int boardSize = engine.getBoardSize();
        Token[][] board = new Token[boardSize][boardSize];

        Map<CellPosition, Token> boardMap = engine.getBoard();
        for (Map.Entry<CellPosition, Token> entry : boardMap.entrySet()) {
            CellPosition pos = entry.getKey();
            board[pos.x()][pos.y()] = entry.getValue();
        }

        return board;
    }

    public boolean[][] getAllowedMoves(String gameId) {
        fr.le_campus_numerique.square_games.engine.Game engine = activeEngines.get(gameId);
        if (engine == null) {
            throw new IllegalStateException("Game engine not found");
        }

        int boardSize = engine.getBoardSize();
        boolean[][] allowed = new boolean[boardSize][boardSize];

        Collection<Token> remainingTokens = engine.getRemainingTokens();
        for (Token token : remainingTokens) {
            if (token.canMove()) {
                for (CellPosition pos : token.getAllowedMoves()) {
                    allowed[pos.x()][pos.y()] = true;
                }
            }
        }

        return allowed;
    }
}
