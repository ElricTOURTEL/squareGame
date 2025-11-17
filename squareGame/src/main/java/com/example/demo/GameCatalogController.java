package com.example.demo;

import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class GameCatalogController {
    @Autowired
    private GameCatalog gameCatalog;

    @GetMapping("/games")
    public Collection<String> getGameIdentifiers() {
        return gameCatalog.getGameIdentifiers();
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameFactory> getGameById(@PathVariable String gameId) {
        GameFactory gameFactory = gameCatalog.getGameFactory(gameId);
        if (gameFactory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameFactory);
    }

    @GetMapping("/games/{gameId}/moves")
    public ResponseEntity<Collection<Move>> getAllowedMoves(@PathVariable String gameId) {
        GameFactory gameFactory = gameCatalog.getGameFactory(gameId);
        if (gameFactory == null) {
            return ResponseEntity.notFound().build();
        }
        Collection<Move> allowedMoves = gameCatalog.getAllowedMoves(gameId);
        return ResponseEntity.ok(allowedMoves);
    }
}
