package com.example.squaregame.dao;

import com.example.squaregame.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    List<Move> findByGameIdOrderByTimestampAsc(String gameId);
    void deleteByGameId(String gameId);
}
