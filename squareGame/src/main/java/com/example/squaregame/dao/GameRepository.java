package com.example.squaregame.dao;

import com.example.squaregame.model.Game;
import com.example.squaregame.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    List<Game> findByStatus(GameStatus status);
}
