package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;

public interface GameStateRepository extends JpaRepository<GameStateDb, Long>{
    Optional<GameStateDb> findById(Long id);
    Optional<GameStateDb> findByPartidaDbIdGame(Long idGame);
}
