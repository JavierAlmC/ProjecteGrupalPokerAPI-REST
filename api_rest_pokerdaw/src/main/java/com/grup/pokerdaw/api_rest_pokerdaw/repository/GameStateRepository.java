package com.grup.pokerdaw.api_rest_pokerdaw.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grup.pokerdaw.api_rest_pokerdaw.model.db.GameStateDb;

public interface GameStateRepository extends JpaRepository<GameStateDb, Long> {
    public Optional<GameStateDb> findById(Long id);

    public Page<GameStateDb> findByGameStateNameContaining(String gameStateName, Pageable paging);
}
