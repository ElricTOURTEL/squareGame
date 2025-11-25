package com.example.squaregame.controller;

import com.example.squaregame.dto.*;
import com.example.squaregame.model.Game;
import com.example.squaregame.model.GameInfo;
import com.example.squaregame.model.Move;
import com.example.squaregame.service.GameService;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/types")
    public ResponseEntity<List<GameInfo>> getAvailableGames() {
        return ResponseEntity.ok(gameService.getAvailableGames());
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame(@RequestBody CreateGameRequest request) {
        try {
            Game game = gameService.createGame(
                request.getGameType(),
                request.getBoardSize()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(GameResponse.fromGame(game));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getGame(@PathVariable String gameId) {
        try {
            Game game = gameService.getGame(gameId);
            return ResponseEntity.ok(GameResponse.fromGame(game));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        List<GameResponse> responses = games.stream()
            .map(GameResponse::fromGame)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<GameResponse> makeMove(
            @PathVariable String gameId,
            @RequestBody MakeMoveRequest request) {
        try {
            Game game = gameService.makeMove(gameId, request.getX(), request.getY(), request.getPlayerId());
            return ResponseEntity.ok(GameResponse.fromGame(game));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{gameId}/moves")
    public ResponseEntity<List<Move>> getGameMoves(@PathVariable String gameId) {
        try {
            List<Move> moves = gameService.getGameMoves(gameId);
            return ResponseEntity.ok(moves);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{gameId}/board")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable String gameId) {
        try {
            Token[][] board = gameService.getBoard(gameId);
            boolean[][] allowedMoves = gameService.getAllowedMoves(gameId);

            String[][] boardStrings = new String[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    boardStrings[i][j] = (board[i][j] != null) ? board[i][j].toString() : null;
                }
            }

            BoardResponse response = new BoardResponse(boardStrings, allowedMoves);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{gameId}/allowed-moves")
    public ResponseEntity<boolean[][]> getAllowedMoves(@PathVariable String gameId) {
        try {
            boolean[][] allowedMoves = gameService.getAllowedMoves(gameId);
            return ResponseEntity.ok(allowedMoves);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable String gameId) {
        try {
            gameService.deleteGame(gameId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
