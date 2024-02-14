package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;

import jakarta.transaction.Transactional;

public interface GameStateRepository extends JpaRepository<GameStateDb, Long>{
    public Optional<GameStateDb> findById(Long id);

    public Optional<GameStateDb> findByPartidaDbIdGame(Long idGame);

    @Transactional
    public void deleteByPartidaDbIdGame(Long idGame);
}
