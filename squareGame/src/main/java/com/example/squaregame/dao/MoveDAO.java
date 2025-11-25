package com.example.squaregame.dao;

import com.example.squaregame.model.Move;
import java.util.List;

public interface MoveDAO {
    Move save(Move move);
    List<Move> findByGameId(String gameId);
    void deleteByGameId(String gameId);
}
