package com.example.squaregame.dao;

import com.example.squaregame.model.Game;
import java.util.List;
import java.util.Optional;

public interface GameDAO {
    Game save(Game game);
    Optional<Game> findById(String id);
    List<Game> findAll();
    void update(Game game);
    void delete(String id);
    List<Game> findByStatus(String status);
}
